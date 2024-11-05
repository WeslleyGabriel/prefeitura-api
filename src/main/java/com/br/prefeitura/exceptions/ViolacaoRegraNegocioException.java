package com.br.prefeitura.exceptions;

public class ViolacaoRegraNegocioException extends RuntimeException {
    public ViolacaoRegraNegocioException(String mensagem) {
        super(mensagem);
    }
}