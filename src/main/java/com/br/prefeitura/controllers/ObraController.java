package com.br.prefeitura.controllers;

import com.br.prefeitura.dtos.ObraDTO;
import com.br.prefeitura.entities.Contrato;
import com.br.prefeitura.entities.Nota;
import com.br.prefeitura.entities.Obra;
import com.br.prefeitura.services.ObraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

@RestController
@Validated
@RequestMapping("/obras")
public class ObraController {

    @Autowired
    private ObraService obraService;

    @Operation(summary = "Cria uma nova Obra")
    @PostMapping("/licitacao/{licitacao_id}/usuario/{usuario_id}/secretaria/{secretaria_id}")
    public ResponseEntity<ObraDTO> salvar(@PathVariable Long licitacao_id, @PathVariable Long usuario_id,
                                       @PathVariable Long secretaria_id, @Valid @RequestBody Obra obra){
        ObraDTO obra1 = obraService.salvar(licitacao_id, usuario_id, secretaria_id,obra);
        return ResponseEntity.status(HttpStatus.OK).body(obra1);
    }

    @Operation(summary = "Cria um Novo Contrato Para Obra")
    @PostMapping("/contrato/usuario/{usuario_id}/obra/{obra_id}")
    public ResponseEntity<Contrato> gerarContrato(@PathVariable Long usuario_id, @PathVariable Long obra_id,
                                                  @Valid @RequestBody Contrato contrato){
        Contrato novoContrato = obraService.gerarContrato(usuario_id, obra_id, contrato);
        return ResponseEntity.status(HttpStatus.OK).body(novoContrato);
    }


    @Operation(summary = "Cria uma nova Nota para Obra")
    @PostMapping("/nota/usuario/{usuario_id}/obra/{obra_id}")
    public ResponseEntity<Nota> gerarNota(@PathVariable Long usuario_id, @PathVariable Long obra_id,
                                          @Valid @RequestBody Nota nota){
        Nota novaNota = obraService.gerarNota(usuario_id, obra_id, nota);
        return ResponseEntity.status(HttpStatus.OK).body(novaNota);
    }

    @Operation(summary = "Busca Contratos Pelo ID da Obra")
    @GetMapping("contrato/usuario/{usuario_id}/obra/{obra_id}")
    public ResponseEntity<Contrato> contrato (@PathVariable Long usuario_id, @PathVariable Long obra_id){
        Contrato contrato = obraService.getContrato(usuario_id, obra_id);
        return ResponseEntity.status(HttpStatus.OK).body(contrato);
    }

    @Operation(summary = "Busca uma Lista de Notas Pelo ID da Obra")
    @GetMapping("notas/usuario/{usuario_id}/obra/{obra_id}")
    public ResponseEntity<List<Nota>> notas (@PathVariable Long usuario_id, @PathVariable Long obra_id){
        List<Nota> notas = obraService.getNotas(usuario_id, obra_id);
        return ResponseEntity.status(HttpStatus.OK).body(notas);
    }


}