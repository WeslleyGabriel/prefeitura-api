package com.br.prefeitura.resources;

import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.enums.Privilegio;
import com.br.prefeitura.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
public class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;

    @BeforeEach
    public void setup() {
        usuarioRepository.deleteAll();

        usuario = new Usuario("Testes Wessley", "gabriel@email.com", "password123", Privilegio.ADMIN);
        usuario = usuarioRepository.save(usuario);
    }

    @Test
    public void testGetById_Success() throws Exception {
        mockMvc.perform(get("/usuarios/" + usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Testes Wessley"))
                .andExpect(jsonPath("$.email").value("gabriel@email.com"))
                .andExpect(jsonPath("$.privilegio").value("ADMIN"));
    }

    @Test
    public void testGetById_NotFound() throws Exception {
        mockMvc.perform(get("/usuarios/98736")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
