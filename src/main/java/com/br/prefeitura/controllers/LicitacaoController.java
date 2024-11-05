package com.br.prefeitura.controllers;

import com.br.prefeitura.dtos.LicitacaoDTO;
import com.br.prefeitura.services.LicitacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/licitacoes")
public class LicitacaoController {

    @Autowired
    private LicitacaoService licitacaoService;


}