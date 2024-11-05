package com.br.prefeitura.exceptions;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException() {
        super("Este e-mail já está cadastrado.");
    }
}