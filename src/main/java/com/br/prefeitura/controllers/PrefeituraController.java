package com.br.prefeitura.controllers;


import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.entities.Prefeitura;
import com.br.prefeitura.entities.Proposta;
import com.br.prefeitura.services.PrefeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/prefeituras")
public class PrefeituraController {

    @Autowired
    private PrefeituraService prefeituraService;

    @PostMapping("/criar")
    public ResponseEntity<Prefeitura> save(@RequestBody Prefeitura prefeitura) {
        Prefeitura prefeitura1 = prefeituraService.save(prefeitura);
        return new ResponseEntity<>(prefeitura1, HttpStatus.CREATED);
    }

    @GetMapping("/licitacoes/{prefeitura_id}")
    public ResponseEntity<List<Licitacao>> getAllLicitacoes(@PathVariable Long prefeitura_id){
        List<Licitacao> licitacoes = prefeituraService.getLicitacoesByPrefeituraId(prefeitura_id);
        return ResponseEntity.status(HttpStatus.OK).body(licitacoes);
    }


    @PutMapping("propostas/{proposta_id}/prefeitura/{prefeitura_id}")
    public ResponseEntity<Proposta> solicitarLicitacao(@PathVariable Long proposta_id, @PathVariable Long prefeitura_id){
        Proposta novaProposta = prefeituraService.solicitarLicitacao(proposta_id, prefeitura_id);
        return ResponseEntity.status(HttpStatus.OK).body(novaProposta);
    }


    @PutMapping("/usuario/{usuario_id}/prefeitura/{prefeitura_id}/licitacao/{licitacao_id}")
    public ResponseEntity<Licitacao> cancelarLicitacao(@PathVariable Long usuario_id, @PathVariable Long prefeitura_id,
                                                       @PathVariable Long licitacao_id){
        Licitacao licitacao = prefeituraService.cancelarLicitacao(prefeitura_id, usuario_id, licitacao_id);
        return ResponseEntity.status(HttpStatus.OK).body(licitacao);
    }


    @GetMapping("/usuario/{usuario_id}/prefeitura/{prefeitura_id}")
    public ResponseEntity<List<Proposta>> listarPropostas(@PathVariable Long usuario_id, @PathVariable Long prefeitura_id){
        List<Proposta> propostas = prefeituraService.listarPropostas(usuario_id, prefeitura_id);
        return ResponseEntity.status(HttpStatus.OK).body(propostas);
    }


    @PutMapping("/aceitar/usuario/{usuario_id}/prefeitura/{prefeitura_id}/proposta/{proposta_id}")
    public ResponseEntity<Proposta> aceitarProposta(@PathVariable Long usuario_id, @PathVariable Long prefeitura_id,
                                                    @PathVariable Long proposta_id){
        Proposta proposta = prefeituraService.aceitarProposta(usuario_id, prefeitura_id, proposta_id);
        return ResponseEntity.status(HttpStatus.OK).body(proposta);
    }

    @PutMapping("/recusar/usuario/{usuario_id}/prefeitura/{prefeitura_id}/proposta/{proposta_id}")
    public ResponseEntity<Proposta> recusarProposta(@PathVariable Long usuario_id, @PathVariable Long prefeitura_id,
                                                    @PathVariable Long proposta_id){
        Proposta proposta = prefeituraService.recusarProposta(usuario_id, prefeitura_id, proposta_id);
        return ResponseEntity.status(HttpStatus.OK).body(proposta);
    }
}
