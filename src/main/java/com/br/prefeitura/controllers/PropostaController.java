package com.br.prefeitura.controllers;

import com.br.prefeitura.dtos.PropostaDTO;
import com.br.prefeitura.entities.Proposta;
import com.br.prefeitura.services.PropostaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaService propostaService;

    @PostMapping("/usuario/{usuario_id}/licitacao/{licitacao_id}")
    public ResponseEntity<Proposta> criarProposta(@PathVariable Long usuario_id, @PathVariable Long licitacao_id,
                                                  @Valid @RequestBody PropostaDTO propostaDTO){
        Proposta novaProposta = propostaService.criarProposta(usuario_id, licitacao_id, propostaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(novaProposta);
    }
}
