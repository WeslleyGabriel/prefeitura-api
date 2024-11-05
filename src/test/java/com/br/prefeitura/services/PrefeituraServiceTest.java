package com.br.prefeitura.services;

import com.br.prefeitura.entities.Prefeitura;
import com.br.prefeitura.entities.Proposta;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.enums.Privilegio;
import com.br.prefeitura.enums.StatusProposta;
import com.br.prefeitura.exceptions.PrefeituraNaoEncontradaException;
import com.br.prefeitura.exceptions.ViolacaoRegraNegocioException;
import com.br.prefeitura.repositories.PrefeituraRepository;
import com.br.prefeitura.repositories.PropostaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PrefeituraServiceTest {

    @Mock
    private PrefeituraRepository prefeituraRepository;

    @Mock
    private PropostaRepository propostaRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private PropostaService propostaService;

    @InjectMocks
    private PrefeituraService prefeituraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() {
        Prefeitura prefeitura = new Prefeitura();
        prefeitura.setId(1L);
        when(prefeituraRepository.findById(1L)).thenReturn(Optional.of(prefeitura));

        Optional<Prefeitura> result = prefeituraService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(prefeituraRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(prefeituraRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PrefeituraNaoEncontradaException.class, () -> prefeituraService.findById(1L));
        verify(prefeituraRepository, times(1)).findById(1L);
    }

    @Test
    void testAceitarProposta_Success() {
        Long usuarioId = 1L;
        Long prefeituraId = 2L;
        Long propostaId = 3L;

        Usuario usuarioAdmin = new Usuario("Nome do Admin", "admin@email.com", "senhaSegura", Privilegio.ADMIN);

        Prefeitura prefeitura = new Prefeitura();
        prefeitura.setId(prefeituraId);

        Proposta proposta = new Proposta();
        proposta.setId(propostaId);

        when(usuarioService.findById(usuarioId)).thenReturn(usuarioAdmin);
        when(prefeituraRepository.findById(prefeituraId)).thenReturn(Optional.of(prefeitura));
        when(propostaService.findById(propostaId)).thenReturn(Optional.of(proposta));
        when(propostaRepository.save(proposta)).thenReturn(proposta);

        Proposta result = prefeituraService.aceitarProposta(usuarioId, prefeituraId, propostaId);

        assertEquals(StatusProposta.ACEITE, result.getStatusProposta());
        assertEquals(prefeitura, result.getPrefeitura());
        verify(propostaRepository, times(1)).save(proposta);
    }


    @Test
    void testAceitarProposta_Unauthorized() {
        Long usuarioId = 1L;
        Long prefeituraId = 2L;
        Long propostaId = 3L;

        Usuario usuarioNaoAdmin = new Usuario("Nome do Usuario", "user@email.com", "senhaSegura", Privilegio.CNPJ);

        Prefeitura prefeitura = new Prefeitura();
        prefeitura.setId(prefeituraId);

        Proposta proposta = new Proposta();
        proposta.setId(propostaId);

        when(usuarioService.findById(usuarioId)).thenReturn(usuarioNaoAdmin);
        when(prefeituraRepository.findById(prefeituraId)).thenReturn(Optional.of(prefeitura));
        when(propostaService.findById(propostaId)).thenReturn(Optional.of(proposta));

        assertThrows(ViolacaoRegraNegocioException.class, () ->
                prefeituraService.aceitarProposta(usuarioId, prefeituraId, propostaId)
        );

        verify(propostaRepository, never()).save(any(Proposta.class));
    }

}
