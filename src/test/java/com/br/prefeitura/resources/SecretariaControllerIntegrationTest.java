package com.br.prefeitura.resources;

import com.br.prefeitura.dtos.LicitacaoStatusDTO;
import com.br.prefeitura.dtos.SecretariaDTO;
import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.entities.Prefeitura;
import com.br.prefeitura.entities.Secretaria;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.enums.Privilegio;
import com.br.prefeitura.enums.StatusLicitacao;
import com.br.prefeitura.repositories.LicitacaoRepository;
import com.br.prefeitura.repositories.PrefeituraRepository;
import com.br.prefeitura.repositories.SecretariaRepository;
import com.br.prefeitura.repositories.UsuarioRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.h2.value.ValueDouble.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class SecretariaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PrefeituraRepository prefeituraRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LicitacaoRepository licitacaoRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCriarSecretaria() throws Exception {
        Prefeitura prefeitura = new Prefeitura();
        prefeitura.setNome("Prefeitura Pianco");
        prefeitura.setEndereco("Rua Faustino, 12344");
        prefeitura = prefeituraRepository.save(prefeitura);

        Secretaria secretaria = new Secretaria(prefeitura, "Secretaria de obras", "obras@prefeitura.com", "obra123");

        String secretariaJson = objectMapper.writeValueAsString(secretaria);

        mockMvc.perform(post("/secretarias/criar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(secretariaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Secretaria de obras"))
                .andExpect(jsonPath("$.email").value("obras@prefeitura.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCriarLicitacao() throws Exception {
        Prefeitura prefeitura = new Prefeitura();
        prefeitura.setNome("Prefeitura Teste");
        prefeitura.setEndereco("Rua Nunes, 123");
        prefeitura = prefeituraRepository.save(prefeitura);

        Secretaria secretaria = new Secretaria(prefeitura, "Secretaria de Obras", "obras@prefeitura.com", "senha12345");
        secretaria = secretariaRepository.save(secretaria);

        Usuario usuario = new Usuario("Admin", "adm@prefeitura.com", "Admin123", Privilegio.ADMIN);
        usuario = usuarioRepository.save(usuario);

        Licitacao licitacao = new Licitacao();
        licitacao.setNumero("LIC123");
        licitacao.setValorEstimado(BigDecimal.valueOf(100000.00));
        licitacao.setTipo("Concorrência Pública");

        String licitacaoJson = objectMapper.writeValueAsString(licitacao);

        mockMvc.perform(post("/secretarias/usuario/" + usuario.getId() + "/secretaria/" + secretaria.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(licitacaoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("LIC123"))
                .andExpect(jsonPath("$.valorEstimado").value(100000.00))
                .andExpect(jsonPath("$.tipo").value("Concorrência Pública"));
    }

    @Test
    @WithMockUser(roles = "ADMIN") //tratar erro de json
    public void testAlterarStatusLicitacao() throws Exception {
        Prefeitura prefeitura = new Prefeitura();
        prefeitura.setNome("Prefeitura Teste");
        prefeitura.setEndereco("Rua nova, 123");
        prefeitura = prefeituraRepository.save(prefeitura);

        Secretaria secretaria = new Secretaria(prefeitura, "Secretaria de Educação", "educacao@prefeitura.com", "senha123");
        secretaria = secretariaRepository.save(secretaria);

        Usuario usuario = new Usuario("Admin", "adm@prefeitura.com", "senhaAdmin", Privilegio.ADMIN);
        usuario = usuarioRepository.save(usuario);

        Licitacao licitacao = new Licitacao("LIC456", new BigDecimal("100000.00"), "Tomada de Preços", StatusLicitacao.EM_ANDAMENTO);
        licitacao.setPrefeitura(prefeitura);
        licitacao.setSecretaria(secretaria);
        licitacao = licitacaoRepository.save(licitacao);

        LicitacaoStatusDTO statusDTO = new LicitacaoStatusDTO(StatusLicitacao.ENCERRADO);
        String statusJson = objectMapper.writeValueAsString(statusDTO);

        mockMvc.perform(put("/secretarias/licitacao/" + licitacao.getId() + "/usuario/" + usuario.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statusJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusLicitaçao").value("ENCERRADO"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllLicitacoes() throws Exception {
        Prefeitura prefeitura = new Prefeitura();
        prefeitura.setNome("Prefeitura Teste");
        prefeitura.setEndereco("Ruas, 123");
        prefeitura = prefeituraRepository.save(prefeitura);

        Secretaria secretaria = new Secretaria(prefeitura, "Secretaria de Obras", "obras@prefeitura.com", "senha123");
        secretaria = secretariaRepository.save(secretaria);

        Licitacao licitacao1 = new Licitacao("LIC101", new BigDecimal ("300000.00"), "Convite", StatusLicitacao.EM_ANDAMENTO);
        licitacao1.setPrefeitura(prefeitura);
        licitacao1.setSecretaria(secretaria);
        licitacaoRepository.save(licitacao1);

        Licitacao licitacao2 = new Licitacao("LIC102", new BigDecimal ("450000.00"), "Concorrência", StatusLicitacao.EM_ANDAMENTO);
        licitacao2.setPrefeitura(prefeitura);
        licitacao2.setSecretaria(secretaria);
        licitacaoRepository.save(licitacao2);

        mockMvc.perform(get("/secretarias/licitacoes/" + secretaria.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].numero").value("LIC101"))
                .andExpect(jsonPath("$[1].numero").value("LIC102"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetLicitacaoById() throws Exception {
        Prefeitura prefeitura = new Prefeitura();
        prefeitura.setNome("Prefeitura Teste");
        prefeitura.setEndereco("Rua da agua, 123");
        prefeitura = prefeituraRepository.save(prefeitura);

        Secretaria secretaria = new Secretaria(prefeitura, "Secretaria de Educação", "educacao@prefeitura.com", "senha123");
        secretaria = secretariaRepository.save(secretaria);

        Licitacao licitacao = new Licitacao("LIC789", new BigDecimal("200000.00"), "Esporte", StatusLicitacao.EM_ANDAMENTO);
        licitacao.setPrefeitura(prefeitura);
        licitacao.setSecretaria(secretaria);
        licitacao = licitacaoRepository.save(licitacao);

        mockMvc.perform(get("/secretarias/licitacao/" + licitacao.getId() + "/secretaria/" + secretaria.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero").value("LIC789"))
                .andExpect(jsonPath("$.valorEstimado").value(200000.00))
                .andExpect(jsonPath("$.tipo").value("Esporte"));
    }




}
