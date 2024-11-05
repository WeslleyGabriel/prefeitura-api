package com.br.prefeitura.services;

import com.br.prefeitura.exceptions.UsuarioIDNaoEncontradoException;
import com.br.prefeitura.exceptions.UsuarioNaoEncontradoException;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    public Usuario save(Usuario usuario) {
        findByEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }


    public Usuario findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioIDNaoEncontradoException(id));
        return usuario;
    }

    public Usuario findByEmail(String email) {
        Usuario usuarioOptional = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioNaoEncontradoException(email));
        return usuarioOptional;
    }

}
