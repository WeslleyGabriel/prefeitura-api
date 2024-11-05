package com.br.prefeitura.services;

import com.br.prefeitura.dtos.LicitacaoDTO;
import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.enums.StatusLicitacao;
import com.br.prefeitura.exceptions.LicitacaoNaoEncontradaException;
import com.br.prefeitura.repositories.LicitacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class LicitacaoService {


    @Autowired
    private LicitacaoRepository licitacaoRepository;


    public Optional<Licitacao> findById(Long id) {
        Optional<Licitacao> licitacaoOptional = licitacaoRepository.findById(id);
        if(licitacaoOptional.isPresent()){
            return licitacaoOptional;
        }else{
            throw new LicitacaoNaoEncontradaException(id);
        }
    }
}