package com.br.prefeitura.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.br.prefeitura.entities.Obra;
import com.br.prefeitura.repositories.ObraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ObraServiceTest {

    @Mock
    private ObraRepository obraRepository;

    @InjectMocks
    private ObraService obraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByIdObraExistente() {
        Long obraId = 1L;
        Obra obra = new Obra();
        obra.setId(obraId);
        obra.setDescricao("Obra Teste");
        when(obraRepository.findById(obraId)).thenReturn(Optional.of(obra));

        Optional<Obra> result = obraService.findById(obraId);

        assertNotNull(result);
        assertEquals(obraId, result.get().getId());
        assertEquals("Obra Teste", result.get().getDescricao());
        verify(obraRepository, times(1)).findById(obraId);
    }

    @Test
    void testSaveObra() {
        Obra obra = new Obra();
        obra.setDescricao("Nova Obra");
        when(obraRepository.save(obra)).thenReturn(obra);

        Obra result = obraService.save(obra);

        assertNotNull(result);
        assertEquals("Nova Obra", result.getDescricao());
        verify(obraRepository, times(1)).save(obra);
    }
}
