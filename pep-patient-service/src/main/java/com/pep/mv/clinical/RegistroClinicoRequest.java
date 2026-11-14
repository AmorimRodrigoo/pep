package com.pep.mv.clinical;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Map;

public record RegistroClinicoRequest(
        @NotNull(message = "O ID do paciente é obrigatório")
        Long pacienteId,

        @NotNull(message = "O tipo de registro clínico é obrigatório")
        TipoRegistroClinico tipo,

        Instant dataRegistro,

        @NotNull(message = "O ID do profissional é obrigatório")
        Long profissionalId,

        @NotEmpty(message = "O conteúdo do registro clínico não pode ser vazio")
        Map<String, Object> conteudo
) {
}
