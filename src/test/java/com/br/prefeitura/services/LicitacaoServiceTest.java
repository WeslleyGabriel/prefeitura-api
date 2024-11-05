package com.br.prefeitura.services;

import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.enums.StatusLicitacao;
import com.br.prefeitura.exceptions.LicitacaoNaoEncontradaException;
import com.br.prefeitura.repositories.LicitacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LicitacaoServiceTest {

    @Mock
    private LicitacaoRepository licitacaoRepository;

    @InjectMocks
    private LicitacaoService licitacaoService;

    private Licitacao licitacao;

    @BeforeEach
    public void setUp() {
        licitacao = new Licitacao();
        licitacao.setId(1L);
        licitacao.setData_abertura(LocalDate.of(2023, 11, 1));
        licitacao.setData_encerramento(LocalDate.of(2023, 12, 1));
        licitacao.setNumero("12345");
        licitacao.setStatusLicitacao(StatusLicitacao.EM_ANDAMENTO);
        licitacao.setTipo("Tipo de Teste");
        licitacao.setValorEstimado(new BigDecimal("10000.00"));
    }

    @Test
    void testFindByIdLicitacaoExistente() {
        when(licitacaoRepository.findById(1L)).thenReturn(Optional.of(licitacao));

        Optional<Licitacao> licitacaoEncontrada = licitacaoService.findById(1L);

        assertTrue(licitacaoEncontrada.isPresent());
        assertEquals("12345", licitacaoEncontrada.get().getNumero());
        assertEquals(StatusLicitacao.EM_ANDAMENTO, licitacaoEncontrada.get().getStatusLicitacao());
        assertEquals("Tipo de Teste", licitacaoEncontrada.get().getTipo());
        assertEquals(new BigDecimal("10000.00"), licitacaoEncontrada.get().getValorEstimado());

        verify(licitacaoRepository).findById(1L);
    }


    @Test
    void testFindByIdLicitacaoInexistente() {
        when(licitacaoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(LicitacaoNaoEncontradaException.class, () -> licitacaoService.findById(1L));

        verify(licitacaoRepository).findById(1L);
    }
}
