package com.br.prefeitura.entities;

import com.br.prefeitura.enums.StatusLicitacao;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Prefeitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 150, message = "O campo não pode ter mais de 150 caracteres")
    @Column(name = "nome", length = 150, nullable = false)
    private String nome;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 50, message = "O campo não pode ter mais de 50 caracteres")
    @Column(name = "endereco", length = 50, nullable = false)
    private String endereco;

    @OneToMany(mappedBy = "prefeitura", cascade = CascadeType.ALL)
    private List<Secretaria> secretarias = new ArrayList<>();

    @OneToMany(mappedBy = "prefeitura", cascade = CascadeType.ALL)
    private List<Licitacao> licitacoes = new ArrayList<>();

    @OneToMany(mappedBy = "prefeitura", cascade = CascadeType.ALL)
    private List<Proposta> propostas = new ArrayList<>();

    public Prefeitura(){}
    public Prefeitura(String nome, String endereco){
        this.nome = nome;
        this.endereco = endereco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Secretaria> getSecretarias() {
        return secretarias;
    }

    public void setSecretarias(List<Secretaria> secretarias) {
        this.secretarias = secretarias;
    }


    public List<Licitacao> getLicitacoes() {

        List<Licitacao> licitacoesRetorno = new ArrayList<>();

        for(Licitacao licitacao : licitacoes){
            if(licitacao.getStatusLicitacao().equals(StatusLicitacao.EM_ANDAMENTO)){
                licitacoesRetorno.add(licitacao);
            }
        }
        return licitacoesRetorno;
    }

    public void setLicitacoes(List<Licitacao> licitacoes) {
        this.licitacoes = licitacoes;
    }

    public List<Proposta> getPropostas() {
        return propostas;
    }

    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }

    public void addLicitacao(Licitacao licitacao){
        this.licitacoes.add(licitacao);
    }

    public void deleteLicitacao(Licitacao licitacao){
        this.licitacoes.remove(licitacao);
    }

    public void addProposta(Proposta proposta){
        this.propostas.add(proposta);
    }

    public void deleteProposta(Proposta proposta){
        this.propostas.remove(proposta);
    }



}
