package com.br.prefeitura.exceptions;

public class PropostaNaoEncontradaException extends RuntimeException {
    public PropostaNaoEncontradaException(Long id) {
        super("Proposta com o id: " +id+" não encontrada!");
    }
}
