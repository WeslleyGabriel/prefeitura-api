package com.br.prefeitura.resources;

import com.br.prefeitura.dtos.ObraDTO;
import com.br.prefeitura.dtos.ObraStatusDTO;
import com.br.prefeitura.entities.*;
import com.br.prefeitura.enums.Privilegio;
import com.br.prefeitura.enums.StatusLicitacao;
import com.br.prefeitura.enums.StatusObra;
import com.br.prefeitura.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ObraControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ObraRepository obraRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Autowired
    private LicitacaoRepository licitacaoRepository;

    @Autowired
    private PrefeituraRepository prefeituraRepository;

    @BeforeEach
    public void setup() {
        // Limpar o banco de dados
        obraRepository.deleteAll();
        licitacaoRepository.deleteAll();
        secretariaRepository.deleteAll();
        usuarioRepository.deleteAll();
        prefeituraRepository.deleteAll(); // Limpar dados anteriores de prefeitura

        // Criar uma instância de Prefeitura e salvar
        Prefeitura prefeitura = new Prefeitura();
        prefeitura.setNome("Prefeitura Teste");
        prefeitura.setEndereco("Rua Nova, 123");
        prefeituraRepository.save(prefeitura);

        // Criar um usuário ADMIN para o teste
        Usuario usuario = new Usuario("Admin Teste", "admin@prefeitura.com", "senhaAdmin", Privilegio.ADMIN);
        usuarioRepository.save(usuario);

        // Criar a secretaria e associá-la à prefeitura
        Secretaria secretaria = new Secretaria();
        secretaria.setNome("Secretaria Teste");
        secretaria.setEmail("secretaria@prefeitura.com");
        secretaria.setSenha("senha123");
        secretaria.setPrefeitura(prefeitura);  // Associar a prefeitura à secretaria
        secretariaRepository.save(secretaria);

        // Criar a licitação
        Licitacao licitacao = new Licitacao();
        licitacao.setNumero("LIC123");
        licitacao.setTipo("Tomada de Preços");
        licitacao.setStatusLicitacao(StatusLicitacao.EM_ANDAMENTO);
        licitacaoRepository.save(licitacao);
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCriarObra() throws Exception {
        Obra obra = new Obra();
        obra.setDescricao("Construção de escola");
        obra.setStatusObra(StatusObra.EM_ANDAMENTO);

        String obraJson = objectMapper.writeValueAsString(obra);

        mockMvc.perform(post("/obras/licitacao/1/usuario/1/secretaria/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(obraJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Construção de escola"))
                .andExpect(jsonPath("$.statusObra").value("EM_ANDAMENTO"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGerarContrato() throws Exception {
        Contrato contrato = new Contrato();
        contrato.setValor(BigDecimal.valueOf(500000.00));

        String contratoJson = objectMapper.writeValueAsString(contrato);

        mockMvc.perform(post("/obras/contrato/usuario/1/obra/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contratoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor").value(500000.00));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGerarNota() throws Exception {
        Nota nota = new Nota();
        nota.setValor(BigDecimal.valueOf(150000.00));

        String notaJson = objectMapper.writeValueAsString(nota);

        mockMvc.perform(post("/obras/nota/usuario/1/obra/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(notaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor").value(150000.00));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testBuscarContratoDaObra() throws Exception {
        mockMvc.perform(get("/obras/contrato/usuario/1/obra/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.valor").exists());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testListarNotasDaObra() throws Exception {
        mockMvc.perform(get("/obras/notas/usuario/1/obra/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descricao").exists());
    }
}
