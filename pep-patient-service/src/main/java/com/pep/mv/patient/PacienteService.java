package com.pep.mv.patient;

import com.pep.mv.shared.CpfJaCadastradoException;
import com.pep.mv.shared.PacienteNaoEncontradoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteService {

    private static final Logger log = LoggerFactory.getLogger(PacienteService.class);

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Transactional
    public PacienteResponse criar(PacienteRequest request) {
        String cpfLimpo = limparMascaraCpf(request.cpf());

        if (pacienteRepository.existsByCpf(cpfLimpo)) {
            log.warn("Tentativa de cadastro de paciente com CPF já existente");
            throw new CpfJaCadastradoException();
        }

        Paciente paciente = new Paciente(
                cpfLimpo,
                request.nome(),
                request.dataNascimento(),
                request.telefone(),
                request.email()
        );

        Paciente salvo = pacienteRepository.save(paciente);
        log.info("Paciente cadastrado com sucesso. id={}", salvo.getId());
        return PacienteResponse.from(salvo);
    }

    @Transactional(readOnly = true)
    public PacienteResponse buscarPorId(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Paciente não encontrado. id={}", id);
                    return new PacienteNaoEncontradoException(id);
                });
        return PacienteResponse.from(paciente);
    }

    @Transactional(readOnly = true)
    public PacienteResponse buscarPorCpf(String cpf) {
        String cpfLimpo = limparMascaraCpf(cpf);
        Paciente paciente = pacienteRepository.findByCpf(cpfLimpo)
                .orElseThrow(() -> {
                    log.warn("Paciente não encontrado na busca por CPF");
                    return new PacienteNaoEncontradoException("Paciente não encontrado para o CPF informado.");
                });
        return PacienteResponse.from(paciente);
    }

    @Transactional(readOnly = true)
    public Page<PacienteResponse> listar(Pageable pageable) {
        return pacienteRepository.findAll(pageable)
                .map(PacienteResponse::from);
    }

    @Transactional
    public PacienteResponse atualizar(Long id, PacienteRequest request) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Tentativa de atualização de paciente inexistente. id={}", id);
                    return new PacienteNaoEncontradoException(id);
                });

        String cpfLimpo = limparMascaraCpf(request.cpf());

        if (!paciente.getCpf().equals(cpfLimpo) && pacienteRepository.existsByCpfAndIdNot(cpfLimpo, id)) {
            log.warn("Conflito de CPF ao atualizar paciente. id={}", id);
            throw new CpfJaCadastradoException();
        }

        paciente.setCpf(cpfLimpo);
        paciente.setNome(request.nome());
        paciente.setDataNascimento(request.dataNascimento());
        paciente.setTelefone(request.telefone());
        paciente.setEmail(request.email());

        Paciente atualizado = pacienteRepository.save(paciente);
        log.info("Paciente atualizado com sucesso. id={}", atualizado.getId());
        return PacienteResponse.from(atualizado);
    }

    private String limparMascaraCpf(String cpf) {
        if (cpf == null) {
            return null;
        }
        return cpf.replaceAll("\\D", "");
    }
}
