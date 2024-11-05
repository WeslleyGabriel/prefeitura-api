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
import com.br.prefeitura.repositories.PrefeituraRepository;
import com.br.prefeitura.repositories.SecretariaRepository;
import com.br.prefeitura.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private  UsuarioRepository repository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  TokenService tokenService;


    public String login(LoginRequestDTO body){
        Optional<Usuario> user = repository.findByEmail(body.getEmail());
        if(user.isPresent()){
            if(passwordEncoder.matches(body.getSenha(), user.get().getSenha())) {
                tokenService.generateToken(user.get());

                return user.get().getNome();
            }
            else{
                throw new SenhaInvalidaException();
            }
        }
        throw new UsuarioNaoEncontradoException(body.getEmail());
    }

    public ResponseDTO register(RegisterRequestDTO body) {
        Optional<Usuario> user = repository.findByEmail(body.getEmail());
        if(user.isPresent()){
            throw new UsuarioJaExisteException(body.getEmail());
        }
        Usuario newUser = new Usuario();
        // Codificar e definir senha
        newUser.setSenha(passwordEncoder.encode(body.getSenha()));
        newUser.setEmail(body.getEmail());
        newUser.setNome(body.getNome());

        // Definir privilégio padrão (exemplo: USER)
        if (body.getPrivilegio().equals(Privilegio.ADMIN)) {
            newUser.setPrivilegio(Privilegio.ADMIN);
        } else if (body.getPrivilegio().equals(Privilegio.CNPJ)) {
            newUser.setPrivilegio(Privilegio.CNPJ);
        }

        this.repository.save(newUser);

        String token = this.tokenService.generateToken(newUser);

        return new ResponseDTO(newUser.getNome(), token);

    }

}

