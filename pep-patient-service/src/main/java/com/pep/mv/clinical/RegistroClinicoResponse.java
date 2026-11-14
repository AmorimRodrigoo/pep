package com.pep.mv.clinical;

import java.time.Instant;
import java.util.Map;

public record RegistroClinicoResponse(
        String id,
        Long pacienteId,
        TipoRegistroClinico tipo,
        Instant dataRegistro,
        Long profissionalId,
        Map<String, Object> conteudo,
        Instant criadoEm
) {
    public static RegistroClinicoResponse from(RegistroClinico registro) {
        return new RegistroClinicoResponse(
                registro.getId(),
                registro.getPacienteId(),
                registro.getTipo(),
                registro.getDataRegistro(),
                registro.getProfissionalId(),
                registro.getConteudo(),
                registro.getCriadoEm()
        );
    }
}
