package com.br.prefeitura.services;

import com.br.prefeitura.dtos.PropostaDTO;
import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.entities.Proposta;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.enums.Privilegio;
import com.br.prefeitura.enums.StatusProposta;
import com.br.prefeitura.exceptions.LicitacaoNaoEncontradaException;
import com.br.prefeitura.exceptions.ViolacaoRegraNegocioException;
import com.br.prefeitura.repositories.PropostaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PropostaServiceTest {

    @Mock
    private PropostaRepository propostaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private LicitacaoService licitacaoService;

    @InjectMocks
    private PropostaService propostaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarProposta_Success() {
        Long usuarioId = 1L;
        Long licitacaoId = 2L;

        Usuario usuario = new Usuario("CODATA", "cnpj@codata.com", "senhaSeguraEu", Privilegio.CNPJ);
        usuario.setId(usuarioId);

        Licitacao licitacao = new Licitacao();
        licitacao.setId(licitacaoId);

        PropostaDTO propostaDTO = new PropostaDTO();
        propostaDTO.setDescricao("Proposta para licitação");
        propostaDTO.setValor(BigDecimal.valueOf(10000.00));

        when(usuarioService.findById(usuarioId)).thenReturn(usuario);
        when(licitacaoService.findById(licitacaoId)).thenReturn(Optional.of(licitacao));
        when(propostaRepository.save(any(Proposta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Proposta proposta = propostaService.criarProposta(usuarioId, licitacaoId, propostaDTO);

        assertNotNull(proposta);
        assertEquals(usuario, proposta.getUsuario());
        assertEquals(licitacao, proposta.getLicitacao());
        assertEquals(propostaDTO.getDescricao(), proposta.getDescricao());
        assertEquals(propostaDTO.getValor(), proposta.getValor());
        verify(propostaRepository, times(1)).save(any(Proposta.class));
    }

    @Test
    void testCriarProposta_WithAdminUser_ThrowsViolacaoRegraNegocioException() {
        Long usuarioId = 1L;
        Long licitacaoId = 2L;

        Usuario usuarioAdmin = new Usuario("Admin", "adm@codata.com", "senhaSeguraNois", Privilegio.ADMIN);
        when(usuarioService.findById(usuarioId)).thenReturn(usuarioAdmin);

        Licitacao licitacao = new Licitacao();
        licitacao.setId(licitacaoId);
        when(licitacaoService.findById(licitacaoId)).thenReturn(Optional.of(licitacao));

        PropostaDTO propostaDTO = new PropostaDTO(new BigDecimal(10000.00), "Descrição proposta", StatusProposta.EM_ANDAMENTO);

        assertThrows(ViolacaoRegraNegocioException.class, () -> {
            propostaService.criarProposta(usuarioId, licitacaoId, propostaDTO);
        });

        verify(propostaRepository, never()).save(any(Proposta.class));
    }

    @Test
    void testCriarProposta_LicitacaoNaoEncontrada_ThrowsLicitacaoNaoEncontradaException() {
        Long usuarioId = 1L;
        Long licitacaoId = 2L;

        Usuario usuario = new Usuario("Codata", "cnpj@codata.com", "senha123", Privilegio.CNPJ);
        usuario.setId(usuarioId);

        when(usuarioService.findById(usuarioId)).thenReturn(usuario);
        when(licitacaoService.findById(licitacaoId)).thenReturn(Optional.empty());

        PropostaDTO propostaDTO = new PropostaDTO();
        propostaDTO.setDescricao("Proposta ausente");
        propostaDTO.setValor(BigDecimal.valueOf(2000.00));

        assertThrows(LicitacaoNaoEncontradaException.class, () ->
                propostaService.criarProposta(usuarioId, licitacaoId, propostaDTO));

        verify(propostaRepository, never()).save(any(Proposta.class));
    }
}
