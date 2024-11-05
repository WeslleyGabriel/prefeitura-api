package com.br.prefeitura.exceptions;

public class SecretariaNaoEncontradaException extends RuntimeException {
    public SecretariaNaoEncontradaException(Long id) {
        super("Secretaria com ID " + id + " não encontrada.");
    }
}
