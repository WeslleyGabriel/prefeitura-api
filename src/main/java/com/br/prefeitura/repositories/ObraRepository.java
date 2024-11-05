package com.br.prefeitura.repositories;

import com.br.prefeitura.enums.StatusObra;
import com.br.prefeitura.entities.Obra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObraRepository extends JpaRepository<Obra, Long> {
    List<Obra> findByStatusObra(StatusObra status);
}
