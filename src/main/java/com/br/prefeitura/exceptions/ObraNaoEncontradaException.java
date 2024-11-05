package com.br.prefeitura.exceptions;

public class ObraNaoEncontradaException extends RuntimeException {
    public ObraNaoEncontradaException(Long id) {
        super("Obra com o id: " +id+" não encontrada!");
    }
}
