package com.br.prefeitura.controllers;

import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{usuario_id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long usuario_id) {
        Usuario usuario = usuarioService.findById(usuario_id);
        return ResponseEntity.ok(usuario);
    }
}
