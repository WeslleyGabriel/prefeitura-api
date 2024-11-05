package com.br.prefeitura.repositories;

import com.br.prefeitura.entities.Prefeitura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrefeituraRepository extends JpaRepository<Prefeitura, Long> {
}
