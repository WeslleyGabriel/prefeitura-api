package com.br.prefeitura.services;

import com.br.prefeitura.dtos.LicitacaoDTO;
import com.br.prefeitura.entities.*;
import com.br.prefeitura.enums.Privilegio;
import com.br.prefeitura.enums.StatusLicitacao;
import com.br.prefeitura.exceptions.*;
import com.br.prefeitura.repositories.LicitacaoRepository;
import com.br.prefeitura.repositories.ObraRepository;
import com.br.prefeitura.repositories.SecretariaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecretariaServiceTest {

    @Mock
    private SecretariaRepository secretariaRepository;

    @InjectMocks
    private SecretariaService secretariaService;

    @Mock
    private LicitacaoRepository licitacaoRepository;

    @Mock
    private ObraRepository obraRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private LicitacaoService licitacaoService;

    @Mock
    private ObraService obraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        Long secretariaId = 1L;
        Secretaria secretaria = new Secretaria();
        secretaria.setId(secretariaId);

        when(secretariaRepository.findById(anyLong())).thenReturn(Optional.of(secretaria));

        Optional<Secretaria> result = secretariaService.findById(secretariaId);

        assertTrue(result.isPresent(), "A secretaria deveria ta presente no resultado");
        assertEquals(secretariaId, result.get().getId(), "O ID da secretaria retornada deve ser o esperado");
    }



    @Test
    void testFindById_NotFound() {
        Long secretariaId = 2L;
        when(secretariaRepository.findById(secretariaId)).thenReturn(Optional.empty());

        assertThrows(SecretariaNaoEncontradaException.class, () -> {
            secretariaService.findById(secretariaId);
        });
    }

    @Test
    void testAbrirLicitacao_Success() {
        Long usuarioId = 1L;
        Long secretariaId = 2L;
        Usuario usuario = new Usuario("Weslley Bonito", "Bonito@gmail.com", "senha000", Privilegio.ADMIN);
        Secretaria secretaria = new Secretaria();
        secretaria.setId(secretariaId);
        Prefeitura prefeitura = new Prefeitura();
        secretaria.setPrefeitura(prefeitura);

        Licitacao licitacao = new Licitacao();
        licitacao.setNumero("12345");
        licitacao.setValorEstimado(BigDecimal.valueOf(100000));
        licitacao.setTipo("Obra governo");

        when(usuarioService.findById(usuarioId)).thenReturn(usuario);
        when(secretariaRepository.findById(secretariaId)).thenReturn(Optional.of(secretaria));
        when(licitacaoRepository.save(any(Licitacao.class))).thenReturn(licitacao);

        LicitacaoDTO result = secretariaService.abrirLicitacao(usuarioId, secretariaId, licitacao);
        assertEquals("12345", result.getNumero());
        assertEquals(BigDecimal.valueOf(100000), result.getValorEstimado());
        assertEquals(StatusLicitacao.EM_ANDAMENTO, result.getStatusLicitacao());
    }

    @Test
    void testAbrirLicitacao_Unauthorized() {
        Long usuarioId = 3L;
        Long secretariaId = 4L;
        Usuario usuario = new Usuario("homem sem autorização", "genteBoa@io.com", "pass12345", Privilegio.CNPJ);
        Secretaria secretaria = new Secretaria();
        secretaria.setId(secretariaId);

        Licitacao licitacao = new Licitacao();
        licitacao.setNumero("54321");
        licitacao.setValorEstimado(BigDecimal.valueOf(50000));
        licitacao.setTipo("Serviço Público");

        when(usuarioService.findById(usuarioId)).thenReturn(usuario);
        when(secretariaRepository.findById(secretariaId)).thenReturn(Optional.of(secretaria));

        assertThrows(ViolacaoRegraNegocioException.class, () -> {
            secretariaService.abrirLicitacao(usuarioId, secretariaId, licitacao);
        });
    }
}
