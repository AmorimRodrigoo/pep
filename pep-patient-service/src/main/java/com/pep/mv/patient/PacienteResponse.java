package com.pep.mv.patient;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PacienteResponse(
        Long id,
        String cpf,
        String nome,
        LocalDate dataNascimento,
        String telefone,
        String email,
        LocalDateTime criadoEm,
        Integer versao
) {
    public static PacienteResponse from(Paciente paciente) {
        return new PacienteResponse(
                paciente.getId(),
                paciente.getCpf(),
                paciente.getNome(),
                paciente.getDataNascimento(),
                paciente.getTelefone(),
                paciente.getEmail(),
                paciente.getCriadoEm(),
                paciente.getVersao()
        );
    }
}
