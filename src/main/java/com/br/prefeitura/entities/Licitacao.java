package com.br.prefeitura.entities;

import com.br.prefeitura.enums.StatusLicitacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Licitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "prefeitura_id", nullable = false)
    private Prefeitura prefeitura;

    @ManyToOne
    @JoinColumn(name = "secretaria_id", nullable = false)
    private Secretaria secretaria;
    @OneToOne(mappedBy = "licitacao")
    private Obra obra;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 20, message = "O campo não pode ter mais de 20 caracteres")
    @Column(name = "numero", length = 20, nullable = false)
    private String numero;

    @Column(name = "data_abertura",nullable = false, updatable = false)
    private final LocalDate dataAbertura = LocalDate.now();

    @Column(name = "data_encerramento")
    private LocalDate dataEncerramento;

    @NotNull(message = "O campo não pode ser nulo")
    @Column(name = "valor_estimado", precision = 10, scale = 2, nullable = false)
    private BigDecimal valorEstimado;

    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo não pode ser vazio ou em branco")
    @Size(max = 50, message = "O campo não pode ter mais de 50 caracteres")
    @Column(name = "tipo", length = 50, nullable = false)
    private String tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_licitacao", nullable = false)
    private StatusLicitacao statusLicitacao;

    public Licitacao(){}
    public Licitacao(String numero,
                     BigDecimal valorEstimado, String tipo, StatusLicitacao statusLicitacao){
        this.prefeitura = prefeitura;
        this.secretaria = secretaria;
        this.numero = numero;
        this.valorEstimado = valorEstimado;
        this.tipo = tipo;
        this.statusLicitacao = statusLicitacao;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDataAbertura() {
        return dataAbertura;
    }

    public LocalDate getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(LocalDate dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
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

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }


    public void setData_abertura(LocalDate of) {
    }

    public void setData_encerramento(LocalDate of) {

    }

    public void setValor_estimado(BigDecimal bigDecimal) {
    }

}
