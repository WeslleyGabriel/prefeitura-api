package com.br.prefeitura.dtos;


import com.br.prefeitura.enums.StatusLicitacao;
import jakarta.validation.constraints.NotNull;


public class LicitacaoStatusDTO {

    @NotNull(message = "O campo status da licitação não pode ser nulo")
    private StatusLicitacao statusLicitacao;

    public LicitacaoStatusDTO(){}
    public LicitacaoStatusDTO(StatusLicitacao statusLicitacao) {
        this.statusLicitacao = statusLicitacao;
    }

    public StatusLicitacao getStatusLicitacao() {
        return statusLicitacao;
    }

    public void setStatusLicitacao(StatusLicitacao statusLicitacao) {
        this.statusLicitacao = statusLicitacao;
    }
}
