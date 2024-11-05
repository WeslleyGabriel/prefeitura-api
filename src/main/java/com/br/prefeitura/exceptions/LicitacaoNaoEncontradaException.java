package com.br.prefeitura.exceptions;

public class LicitacaoNaoEncontradaException extends RuntimeException {
    public LicitacaoNaoEncontradaException(Long id) {
        super("Licitação com o id: " +id+" não encontrada!");
    }
}