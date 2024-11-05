package com.br.prefeitura.exceptions;

public class PrefeituraNaoEncontradaException extends RuntimeException {
    public PrefeituraNaoEncontradaException(Long id) {
        super("Prefeitura com ID " + id + " não encontrada.");
    }
}
