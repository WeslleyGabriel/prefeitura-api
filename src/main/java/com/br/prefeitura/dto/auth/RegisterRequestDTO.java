package com.br.prefeitura.dto.auth;

import com.br.prefeitura.enums.Privilegio;

public class RegisterRequestDTO {
    private String name;
    private String email;
    private String senha;
    private Privilegio privilegio; // Adicionar este campo se necessário

    public RegisterRequestDTO() {}

    public RegisterRequestDTO(String name, String email, String senha) {
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.privilegio = privilegio;
    }

    // Getters e setters para todos os campos
    public String getNome() {
        return name;
    }

    public void setNome(String name) {
        this.name = name;
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

    public Privilegio getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Privilegio privilegio) {
        this.privilegio = privilegio;
    }
}