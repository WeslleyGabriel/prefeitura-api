package com.br.prefeitura.repositories;

import com.br.prefeitura.entities.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
}
