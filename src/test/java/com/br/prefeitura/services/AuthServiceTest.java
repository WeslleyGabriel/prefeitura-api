package com.br.prefeitura.services;

import com.br.prefeitura.dto.auth.LoginRequestDTO;
import com.br.prefeitura.dto.auth.RegisterRequestDTO;
import com.br.prefeitura.dto.auth.ResponseDTO;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.enums.Privilegio;
import com.br.prefeitura.exceptions.SenhaInvalidaException;
import com.br.prefeitura.exceptions.UsuarioJaExisteException;
import com.br.prefeitura.exceptions.UsuarioNaoEncontradoException;
import com.br.prefeitura.infra.security.TokenService;
import com.br.prefeitura.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequestDTO registerRequest;
    private LoginRequestDTO loginRequest;
    private ResponseDTO response;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequestDTO();
        registerRequest.setEmail("weslley@teste.io");
        registerRequest.setSenha("senha123");
        registerRequest.setNome("Weslley");
        registerRequest.setPrivilegio(Privilegio.ADMIN);

        loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("weslley@teste.io");
        loginRequest.setSenha("senha123");

        usuario = new Usuario();
        usuario.setEmail("weslley@teste.io");
        usuario.setNome("Weslley");
        usuario.setSenha("senhaCodificada");
    }

    @Test
    void testRegisterSuccess() {
        when(repository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerRequest.getSenha())).thenReturn("senha123");
        when(tokenService.generateToken(any(Usuario.class))).thenReturn("token-testes");

        ResponseDTO response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("Weslley", response.getNome());
        assertEquals("token-testes", response.getToken());

        verify(repository).save(any(Usuario.class));
    }


    @Test
    void testRegisterEmailAlreadyExists() {

        when(repository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.of(new Usuario()));

        assertThrows(UsuarioJaExisteException.class, () -> authService.register(registerRequest));

        verify(repository, never()).save(any(Usuario.class));
    }

    @Test
    void testLoginSuccess() {
        when(repository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())).thenReturn(true);
        when(tokenService.generateToken(usuario)).thenReturn("token-testes");

        String nomeUsuario = authService.login(loginRequest);

        System.out.println("Login Request: " + loginRequest);
        System.out.println("Login Response: Nome do Usuário = " + nomeUsuario);

        assertNotNull(nomeUsuario);
        assertEquals("Weslley", nomeUsuario);
    }

    @Test
    void testLoginUsuarioNaoEncontrado() {
        when(repository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> authService.login(loginRequest));

        System.out.println("Login Request: " + loginRequest);
        System.out.println("Login Response: UsuarioNaoEncontradoException lançada");
    }

    @Test
    void testLoginSenhaInvalida() {
        when(repository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())).thenReturn(false);

        assertThrows(SenhaInvalidaException.class, () -> authService.login(loginRequest));

        System.out.println("Login Request: " + loginRequest);
        System.out.println("Login Response: SenhaInvalidaException lançada");
    }
}
