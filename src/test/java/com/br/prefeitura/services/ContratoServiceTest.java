package com.br.prefeitura.services;

import com.br.prefeitura.entities.Contrato;
import com.br.prefeitura.entities.Proposta;
import com.br.prefeitura.exceptions.ContratoNaoEncontradoException;
import com.br.prefeitura.repositories.ContratoRepository;
import com.br.prefeitura.services.ContratoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContratoServiceTest {

    @Mock
    private ContratoRepository contratoRepository;
    private ContratoService service;

    @InjectMocks
    private ContratoService contratoService;

    private Contrato contrato;

    @BeforeEach
    void setUp() {
        Proposta proposta = new Proposta();
        proposta.setDescricao("Contrato de teste");

        contrato = new Contrato();
        contrato.setId(1L);
        contrato.setProposta(proposta);
    }

    @Test
    void testSaveContrato() {
        when(contratoRepository.save(any(Contrato.class))).thenReturn(contrato);

        Contrato contratoSalvo = contratoService.save(contrato);

        assertNotNull(contratoSalvo);
        assertEquals("Contrato de teste", contratoSalvo.getProposta().getDescricao());

        verify(contratoRepository).save(any(Contrato.class));
    }


    @Test
    void testFindByIdContratoExistente() {
        when(contratoRepository.findById(1L)).thenReturn(Optional.of(contrato));

        Optional<Contrato> contratoEncontrado = contratoService.findById(1L);

        assertTrue(contratoEncontrado.isPresent());

        verify(contratoRepository).findById(1L);
    }

    @Test
    void testFindByIdContratoInexistente() {
        when(contratoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ContratoNaoEncontradoException.class, () -> contratoService.findById(1L));

        verify(contratoRepository).findById(1L);
    }
}
