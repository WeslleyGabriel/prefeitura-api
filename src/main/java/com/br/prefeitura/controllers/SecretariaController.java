package com.br.prefeitura.controllers;

import com.br.prefeitura.dtos.*;
import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.entities.Obra;
import com.br.prefeitura.entities.Secretaria;
import com.br.prefeitura.services.LicitacaoService;
import com.br.prefeitura.services.SecretariaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Validated
@RequestMapping("/secretarias")
public class SecretariaController {

    @Autowired
    private SecretariaService secretariaService;

    @Autowired
    private LicitacaoService licitacaoService;

    @PostMapping("/criar")
    public SecretariaDTO save(@RequestBody Secretaria secretaria){
        return secretariaService.save(secretaria);
    }

    @Operation(summary = "Cria Nova Licitação da Secretaria")
    @PostMapping("/usuario/{usuarioId}/secretaria/{secretariaId}")
    public ResponseEntity<LicitacaoDTO> criarLicitacao(@PathVariable Long usuarioId, @PathVariable Long secretariaId,  @Valid @RequestBody Licitacao licitacao){
        LicitacaoDTO novaLicitacao = secretariaService.abrirLicitacao(usuarioId, secretariaId, licitacao);
        return ResponseEntity.status(HttpStatus.OK).body(novaLicitacao);
    }


    @Operation(summary = "Atualiza a Licitação")
    @PutMapping("/licitacao/{licitacao_id}/usuario/{usuario_id}")
    public ResponseEntity<Licitacao> alterarStatusLicitacao(
            @PathVariable Long licitacao_id, @PathVariable Long usuario_id,  @Valid @RequestBody LicitacaoStatusDTO licitacaoStatusDTO) {

        Licitacao licitacaoAtualizada = secretariaService.alterarStatusLicitacao(usuario_id, licitacao_id, licitacaoStatusDTO);
        return ResponseEntity.status(HttpStatus.OK).body(licitacaoAtualizada);
    }


    @Operation(summary = "Busca Licitação de Acordo Com a Secretaria")
    @GetMapping("/licitacao/{licitacao_id}/secretaria/{secretaria_id}")
    public ResponseEntity<Licitacao> getLicitacaoById(@PathVariable Long secretaria_id, @PathVariable Long licitacao_id){
        Licitacao licitacao = secretariaService.findLicitacaoById(secretaria_id ,licitacao_id);
        return ResponseEntity.status(HttpStatus.OK).body(licitacao);
    }

    @Operation(summary = "Busca uma Lista de Licitações da Secretaria")
    @GetMapping("/licitacoes/{secretaria_id}")
    public ResponseEntity<List<Licitacao>> getAllLicitacoes(@PathVariable Long secretaria_id){
        List<Licitacao> licitacaos = secretariaService.findAllLicitacoes(secretaria_id);
        return ResponseEntity.status(HttpStatus.OK).body(licitacaos);
    }

    @Operation(summary = "Cria uma Nova Obra Para Secretaria")
    @PostMapping("/usuario/{usuarioId}/licitacao/{licitacaoId}/secretaria/{secretariaId}")
    public ResponseEntity<Obra> criarObra(@PathVariable Long usuarioId, @PathVariable Long licitacaoId,
                                          @PathVariable Long secretariaId, @Valid @RequestBody ObraDTO obraDTO){
        Obra novaObra = secretariaService.iniciarObra(usuarioId, licitacaoId, secretariaId, obraDTO);
        return ResponseEntity.status(HttpStatus.OK).body(novaObra);
    }

    @Operation(summary = "Busca uma Lista de Obras Pela Secretaria")
    @GetMapping("/obras/{secretaria_id}")
    public ResponseEntity<List<Obra>> getAllObras(@PathVariable Long secretaria_id){
        List<Obra> obras = secretariaService.findAllObras(secretaria_id);
        return ResponseEntity.status(HttpStatus.OK).body(obras);
    }



    @GetMapping("/obra/{obra_id}/secretaria/{secretaria_id}")
    public ResponseEntity<Obra> getObraById(@PathVariable Long secretaria_id, @PathVariable Long obra_id){
        Obra obra = secretariaService.findObraById(secretaria_id ,obra_id);
        return ResponseEntity.status(HttpStatus.OK).body(obra);
    }



    @PutMapping("/obra/{obra_id}/usuario/{usuario_id}")
    public ResponseEntity<Obra> alterarStatusObra(
            @PathVariable Long obra_id, @PathVariable Long usuario_id, @Valid @RequestBody ObraStatusDTO obraStatusDTO) {
        Obra obraAtualizada = secretariaService.alterarStatusObra(usuario_id, obra_id, obraStatusDTO);
        return ResponseEntity.status(HttpStatus.OK).body(obraAtualizada);
    }
}

