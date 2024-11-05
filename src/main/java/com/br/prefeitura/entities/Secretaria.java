package com.br.prefeitura.entities;

import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.entities.Obra;
import com.br.prefeitura.entities.Prefeitura;
import com.br.prefeitura.exceptions.LicitacaoNaoEncontradaException;
import com.br.prefeitura.exceptions.ObraNaoEncontradaException;
import com.br.prefeitura.exceptions.PrefeituraNaoEncontradaException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Entity
public class Secretaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O campo não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "prefeitura_id", nullable = false)
    private Prefeitura prefeitura;


    @OneToMany(mappedBy = "secretaria")
    private List<Obra> obras = new ArrayList<>();

    @OneToMany(mappedBy = "secretaria")
    private List<Licitacao> licitacoes = new ArrayList<>();

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 100, message = "O campo não pode ter mais de 100 caracteres")
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Email
    @Size(max = 100, message = "O campo não pode ter mais de 100 caracteres")
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 540, message = "O campo não pode ter mais de 540 caracteres")
    @Column(name = "senha", length = 540, nullable = false)
    private String senha;

    public Secretaria(){}
    public Secretaria(Prefeitura prefeitura, String nome, String email, String senha){
        this.prefeitura = prefeitura;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Prefeitura getPrefeitura() {
        return prefeitura;
    }

    public void setPrefeitura(Prefeitura prefeitura) {
        this.prefeitura = prefeitura;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Obra> getObras() {
        return obras;
    }

    public void setObras(List<Obra> obras) {
        this.obras = obras;
    }

    public List<Licitacao> getLicitacoes() {
        return licitacoes;
    }

    public void setLicitacoes(List<Licitacao> licitacoes) {
        this.licitacoes = licitacoes;
    }

    // Método para retornar uma licitação com base no ID
    public Licitacao findLicitacaoById(Long licitacaoId) {

        // Busca a licitação na lista de licitações
        for (Licitacao licitacao : licitacoes) {
            if (licitacao.getId().equals(licitacaoId)) {
                return licitacao; // Retorna a licitação encontrada
            }
        }
        // lança uma exceção caso não encontre
        throw new LicitacaoNaoEncontradaException(licitacaoId);
    }

    // Método para retornar uma obra com base no ID
    public Obra findObraById(Long obraId) {

        // Busca a obra na lista de obras
        for (Obra obra : obras) {
            if (obra.getId().equals(obraId)) {
                return obra; // Retorna a obra encontrada
            }
        }
        // lança uma exceção caso não encontre
        throw new ObraNaoEncontradaException(obraId);
    }

}
