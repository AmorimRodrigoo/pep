package com.pep.mv.shared;

public class CpfJaCadastradoException extends RuntimeException {

    public CpfJaCadastradoException() {
        super("CPF já cadastrado no sistema.");
    }

    public CpfJaCadastradoException(String message) {
        super(message);
    }
}
