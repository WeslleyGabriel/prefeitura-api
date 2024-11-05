package com.br.prefeitura.entities;

import com.br.prefeitura.enums.Privilegio;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prefeitura_id")
    private Prefeitura prefeitura;

    @ManyToOne
    @JoinColumn(name = "secretaria_id")
    private Secretaria secretaria;

    @OneToMany(mappedBy = "usuario")
    private List<Proposta> propostas = new ArrayList<>();

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 100, message = "O campo não pode ter mais de 100 caracteres")
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotNull(message = "O campo não pode ser nulo")
    @Email(message = "O campo deve ser um e-mail valido")
    @Size(max = 100, message = "O campo não pode ter mais de 100 caracteres")
    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 540, message = "O campo não pode ter mais de 540 caracteres")
    @Column(name = "senha", length = 540, nullable = false)
    private String senha;

    @NotNull(message = "O campo não pode ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "privilegio", nullable = false)
    private Privilegio privilegio;

    public Usuario(){}
    public Usuario(String nome,
                   String email, String senha, Privilegio privilegio){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.privilegio = privilegio;
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

    public Secretaria getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(Secretaria secretaria) {
        this.secretaria = secretaria;
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

    public Privilegio getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Privilegio privilegio) {
        this.privilegio = privilegio;
    }

    public List<Proposta> getPropostas() {
        return propostas;
    }

    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }

    public boolean isAdmin(){
        return privilegio == Privilegio.ADMIN;
    }
}
