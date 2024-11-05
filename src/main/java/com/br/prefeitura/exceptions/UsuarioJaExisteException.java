package com.br.prefeitura.exceptions;

public class UsuarioJaExisteException extends RuntimeException {
    public UsuarioJaExisteException(String email) {
        super("Usuario com email: " + email + " ja existe.");
    }
}