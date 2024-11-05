package com.br.prefeitura.services;

import com.br.prefeitura.exceptions.ContratoNaoEncontradoException;
import com.br.prefeitura.entities.Contrato;
import com.br.prefeitura.repositories.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;


    public Contrato save(Contrato contrato) {
        return contratoRepository.save(contrato);
    }


    public Optional<Contrato> findById(Long id) {
        Optional<Contrato> contratoOptional = contratoRepository.findById(id);
        if(contratoOptional.isPresent()){
            return contratoOptional;
        }else{
            throw new ContratoNaoEncontradoException(id);
        }
    }
}
