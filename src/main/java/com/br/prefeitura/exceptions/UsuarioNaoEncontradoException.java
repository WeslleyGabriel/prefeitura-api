package com.br.prefeitura.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(String email) {
        super("Usuario com email: " + email + " não encontrado.");
    }
}