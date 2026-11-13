package com.pep.mv.shared;

public class PacienteNaoEncontradoException extends RuntimeException {

    public PacienteNaoEncontradoException(String message) {
        super(message);
    }

    public PacienteNaoEncontradoException(Long id) {
        super("Paciente não encontrado com id: " + id);
    }
}
