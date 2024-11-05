package com.br.prefeitura.services;

import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.exceptions.UsuarioIDNaoEncontradoException;
import com.br.prefeitura.exceptions.UsuarioNaoEncontradoException;
import com.br.prefeitura.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testSaveAndRetrieveUser_Success() {
        Usuario usuario = new Usuario();
        usuario.setEmail("weslleyPaz@gmail.com");
        usuario.setNome("Wes Paz");
        usuario.setSenha("senhaBoa");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        when(usuarioRepository.findByEmail(anyString())).thenReturn(Optional.of(usuario));

        Usuario savedUser = usuarioService.save(usuario);

        Usuario foundUser = usuarioService.findByEmail("weslleyPaz@gmail.com");

        assertEquals("weslleyPaz@gmail.com", foundUser.getEmail(), "O e-mail deve ser o esperado");
        assertEquals("Wes Paz", foundUser.getNome(), "O nome deve ser o esperado");

        verify(usuarioRepository, times(2)).findByEmail("weslleyPaz@gmail.com");
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testFindById_Success() {
        Long userId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(userId);

        when(usuarioRepository.findById(userId)).thenReturn(Optional.of(usuario));

        Usuario foundUser = usuarioService.findById(userId);

        assertEquals(userId, foundUser.getId());
        verify(usuarioRepository, times(1)).findById(userId);
    }

    @Test
    void testFindById_NotFound() {
        Long userId = 1L;

        when(usuarioRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsuarioIDNaoEncontradoException.class, () -> {
            usuarioService.findById(userId);
        });

        verify(usuarioRepository, times(1)).findById(userId);
    }

    @Test
    void testFindByEmail_Success() {
        String email = "testeees@gmail.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        Usuario foundUser = usuarioService.findByEmail(email);

        assertEquals(email, foundUser.getEmail());
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    void testFindByEmail_NotFound() {
        String email = "nemExiste@gmail.com";

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            usuarioService.findByEmail(email);
        });

        verify(usuarioRepository, times(1)).findByEmail(email);
    }
}
