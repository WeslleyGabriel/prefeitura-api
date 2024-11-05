package com.br.prefeitura.resources;

import com.br.prefeitura.controllers.PropostaController;
import com.br.prefeitura.dtos.PropostaDTO;
import com.br.prefeitura.entities.Proposta;
import com.br.prefeitura.enums.StatusProposta;
import com.br.prefeitura.services.LicitacaoService;
import com.br.prefeitura.services.PropostaService;
import com.br.prefeitura.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropostaController.class)
public class PropostaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropostaService propostaService;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private LicitacaoService licitacaoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void criarProposta() throws Exception {
        PropostaDTO propostaDTO = new PropostaDTO(new BigDecimal("25000.00"), "Construção de ponte", StatusProposta.EM_ANDAMENTO);

        Proposta propostaSalva = new Proposta();
        propostaSalva.setDescricao(propostaDTO.getDescricao());
        propostaSalva.setValor(propostaDTO.getValor());
        propostaSalva.setStatusProposta(propostaDTO.getStatusProposta());

        when(propostaService.criarProposta(anyLong(), anyLong(), any(PropostaDTO.class))).thenReturn(propostaSalva);

        mockMvc.perform(post("/propostas/usuario/1/licitacao/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"valor\": 25000.00, \"descricao\": \"Construção de ponte\", \"statusProposta\": \"EM_ANDAMENTO\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao").value("Construção de ponte"))
                .andExpect(jsonPath("$.valor").value(25000.00))
                .andExpect(jsonPath("$.statusProposta").value("EM_ANDAMENTO"));

        verify(propostaService, times(1)).criarProposta(anyLong(), anyLong(), any(PropostaDTO.class));
    }
}
