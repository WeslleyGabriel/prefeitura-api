package com.br.prefeitura.exceptions;

public class DataEncerramentoInvalidaException extends RuntimeException {
    public DataEncerramentoInvalidaException(String message) {
        super(message);
    }
}