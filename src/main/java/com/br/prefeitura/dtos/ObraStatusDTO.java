package com.br.prefeitura.dtos;

import com.br.prefeitura.enums.StatusObra;
import jakarta.validation.constraints.NotNull;


public class ObraStatusDTO {

    @NotNull(message = "O campo status da obra não pode ser nulo")
    private StatusObra statusObra;

    public ObraStatusDTO(){}
    public ObraStatusDTO(StatusObra statusObra) {
        this.statusObra = statusObra;
    }

    public StatusObra getStatusObra() {
        return statusObra;
    }

    public void setStatusObra(StatusObra statusObra) {
        this.statusObra = statusObra;
    }
}
