package com.br.prefeitura.dtos;

import com.br.prefeitura.enums.StatusProposta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class PropostaDTO {

    @NotNull(message = "O campo valor não pode ser nulo")
    private BigDecimal valor;

    @NotNull(message = "O campo descrição não pode ser nulo")
    @NotBlank(message = "O campo descrição não pode ser vazio ou em branco")
    @Size(max = 250, message = "O campo descrição não pode ter mais de 250 caracteres")
    private String descricao;

    @NotNull(message = "O campo status da proposta não pode ser nulo")
    private StatusProposta statusProposta;

    // Construtores
    public PropostaDTO() {}
    public PropostaDTO(BigDecimal valor, String descricao, StatusProposta statusProposta) {
        this.valor = valor;
        this.descricao = descricao;
        this.statusProposta = statusProposta;
    }

    // Getters e Setters
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public void setStatusProposta(StatusProposta statusProposta) {
        this.statusProposta = statusProposta;
    }
}
