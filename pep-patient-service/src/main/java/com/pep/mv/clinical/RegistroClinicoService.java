package com.pep.mv.clinical;

import com.pep.mv.patient.PacienteRepository;
import com.pep.mv.shared.PacienteNaoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RegistroClinicoService {

    private static final Logger log = LoggerFactory.getLogger(RegistroClinicoService.class);

    private final RegistroClinicoRepository registroClinicoRepository;
    private final PacienteRepository pacienteRepository;

    public RegistroClinicoService(
            RegistroClinicoRepository registroClinicoRepository,
            PacienteRepository pacienteRepository
    ) {
        this.registroClinicoRepository = registroClinicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public RegistroClinicoResponse criar(RegistroClinicoRequest request, String idempotencyKey) {
        String chaveNormalizada = (idempotencyKey != null && !idempotencyKey.isBlank())
                ? idempotencyKey.trim()
                : null;

        if (chaveNormalizada != null) {
            Optional<RegistroClinico> existente = registroClinicoRepository.findByIdempotencyKey(chaveNormalizada);
            if (existente.isPresent()) {
                log.info("Idempotency-Key já processada ({}). Retornando registro clínico existente id={}",
                        chaveNormalizada, existente.get().getId());
                return RegistroClinicoResponse.from(existente.get());
            }
        }

        if (!pacienteRepository.existsById(request.pacienteId())) {
            log.warn("Tentativa de criar registro clínico para paciente inexistente. pacienteId={}", request.pacienteId());
            throw new PacienteNaoEncontradoException(request.pacienteId());
        }

        Instant agora = Instant.now();
        Instant dataRegistro = request.dataRegistro() != null ? request.dataRegistro() : agora;

        RegistroClinico novoRegistro = new RegistroClinico(
                request.pacienteId(),
                request.tipo(),
                dataRegistro,
                request.profissionalId(),
                request.conteudo(),
                agora,
                chaveNormalizada
        );

        RegistroClinico salvo = registroClinicoRepository.save(novoRegistro);
        log.info("Registro clínico criado com sucesso. id={}, pacienteId={}, tipo={}",
                salvo.getId(), salvo.getPacienteId(), salvo.getTipo());
        return RegistroClinicoResponse.from(salvo);
    }

    public Page<RegistroClinicoResponse> listarPorPaciente(Long pacienteId, Pageable pageable) {
        if (!pacienteRepository.existsById(pacienteId)) {
            log.warn("Tentativa de consultar histórico clínico de paciente inexistente. pacienteId={}", pacienteId);
            throw new PacienteNaoEncontradoException(pacienteId);
        }

        return registroClinicoRepository.findByPacienteIdOrderByDataRegistroDesc(pacienteId, pageable)
                .map(RegistroClinicoResponse::from);
    }
}
