package com.br.prefeitura.controllers;

import com.br.prefeitura.dto.auth.LoginRequestDTO;
import com.br.prefeitura.dto.auth.RegisterRequestDTO;
import com.br.prefeitura.dto.auth.ResponseDTO;
import com.br.prefeitura.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO body){
        authService.login(body);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body) {
        ResponseDTO responseDTO = authService.register(body);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}