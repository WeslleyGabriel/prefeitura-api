package com.br.prefeitura.exceptions;

public class ContratoNaoEncontradoException extends RuntimeException {
    public ContratoNaoEncontradoException(Long id) {
        super("Contrato com o id: " +id+" não encontrada!");
    }
}
