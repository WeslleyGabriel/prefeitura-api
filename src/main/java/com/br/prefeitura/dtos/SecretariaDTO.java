package com.br.prefeitura.dtos;

import com.br.prefeitura.entities.Prefeitura;

public class SecretariaDTO {

    private Prefeitura prefeitura;
    private String nome;
    private String email;
    private String senha;

    public SecretariaDTO(){}
    public SecretariaDTO(Prefeitura prefeitura, String nome, String email, String senha){
        this.prefeitura = prefeitura;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

}
