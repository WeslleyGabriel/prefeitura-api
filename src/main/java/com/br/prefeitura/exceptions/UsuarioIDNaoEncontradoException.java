package com.br.prefeitura.exceptions;

public class UsuarioIDNaoEncontradoException extends RuntimeException {
    public UsuarioIDNaoEncontradoException(Long id) {
        super("Usuario com ID: " + id+ " não encontrado.");
    }
}