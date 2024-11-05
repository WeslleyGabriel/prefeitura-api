package com.br.prefeitura.dtos;


import com.br.prefeitura.entities.Contrato;
import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.entities.Nota;
import com.br.prefeitura.entities.Secretaria;
import com.br.prefeitura.enums.StatusObra;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ObraDTO {

    private Licitacao licitacao;

    private Secretaria secretaria;

    private List<Nota> notas = new ArrayList<>();

    private Contrato contrato;

    private String descricao;

    private final LocalDate dataInicio = LocalDate.now();

    private LocalDate dataFim;

    private StatusObra statusObra;

    public ObraDTO(){}

    public ObraDTO(Licitacao licitacao, Secretaria secretaria, List<Nota> notas, Contrato contrato, String descricao, LocalDate dataFim, StatusObra statusObra) {
        this.licitacao = licitacao;
        this.secretaria = secretaria;
        this.notas = notas;
        this.contrato = contrato;
        this.descricao = descricao;
        this.dataFim = dataFim;
        this.statusObra = statusObra;
    }

    public Licitacao getLicitacao() {
        return licitacao;
    }

    public void setLicitacao(Licitacao licitacao) {
        this.licitacao = licitacao;
    }

    public Secretaria getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(Secretaria secretaria) {
        this.secretaria = secretaria;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public StatusObra getStatusObra() {
        return statusObra;
    }

    public void setStatusObra(StatusObra statusObra) {
        this.statusObra = statusObra;
    }
}
