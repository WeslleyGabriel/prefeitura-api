package com.br.prefeitura.dtos;


import com.br.prefeitura.enums.StatusLicitacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class LicitacaoDTO {
    private String numero;

    private BigDecimal valorEstimado;

    private String tipo;

    private StatusLicitacao statusLicitacao;

    public LicitacaoDTO(){}
    public LicitacaoDTO(String numero, BigDecimal valorEstimado, String tipo, StatusLicitacao statusLicitacao) {
        this.numero = numero;
        this.valorEstimado = valorEstimado;
        this.tipo = tipo;
        this.statusLicitacao = statusLicitacao;
    }

    // Getters and Setters
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getValorEstimado() {
        return valorEstimado;
    }

    public void setValorEstimado(BigDecimal valorEstimado) {
        this.valorEstimado = valorEstimado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public StatusLicitacao getStatusLicitacao() {
        return statusLicitacao;
    }

    public void setStatusLicitacao(StatusLicitacao statusLicitacao) {
        this.statusLicitacao = statusLicitacao;
    }
}
