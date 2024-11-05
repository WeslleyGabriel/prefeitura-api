package com.br.prefeitura.repositories;


import com.br.prefeitura.enums.StatusLicitacao;
import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.entities.Prefeitura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LicitacaoRepository extends JpaRepository<Licitacao, Long> {

    List<Licitacao> findByStatusLicitacao(StatusLicitacao status);

    List<Licitacao> findByPrefeituraAndStatusLicitacao(Prefeitura prefeitura, StatusLicitacao status);

    List<Licitacao> findByDataAberturaBetween(LocalDate dataInicio, LocalDate dataFim);

    Optional<Licitacao> findByNumero(String numero);
}
