package com.br.prefeitura.services;

import com.br.prefeitura.dtos.ObraDTO;
import com.br.prefeitura.entities.*;
import com.br.prefeitura.exceptions.*;
import com.br.prefeitura.repositories.ContratoRepository;
import com.br.prefeitura.repositories.NotaRepository;
import com.br.prefeitura.repositories.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObraService {

    private final SecretariaService secretariaService;
    private final ObraRepository obraRepository;
    private final NotaRepository notaRepository;
    private final LicitacaoService licitacaoService;
    private final UsuarioService usuarioService;
    private final ContratoRepository contratoRepository;
    private final ContratoService contratoService;

    @Autowired
    public ObraService(
            @Lazy SecretariaService secretariaService,
            ObraRepository obraRepository,
            NotaRepository notaRepository,
            LicitacaoService licitacaoService,
            UsuarioService usuarioService,
            ContratoRepository contratoRepository,
            ContratoService contratoService
    ) {
        this.secretariaService = secretariaService;
        this.obraRepository = obraRepository;
        this.notaRepository = notaRepository;
        this.licitacaoService = licitacaoService;
        this.usuarioService = usuarioService;
        this.contratoRepository = contratoRepository;
        this.contratoService = contratoService;
    }

    public Optional<Obra> findById(Long id) {
        return obraRepository.findById(id)
                .or(() -> { throw new ObraNaoEncontradaException(id); });
    }

    public ObraDTO salvar(Long licitacao_id, Long usuario_id, Long secretaria_id, Obra obra) {
        Licitacao licitacao = licitacaoService.findById(licitacao_id)
                .orElseThrow(() -> new LicitacaoNaoEncontradaException(licitacao_id));
        Usuario usuario = usuarioService.findById(usuario_id);
        Secretaria secretaria = secretariaService.findById(secretaria_id)
                .orElseThrow(() -> new SecretariaNaoEncontradaException(secretaria_id));

        if (usuario.isAdmin()) {
            obra.setSecretaria(secretaria);
            obra.setLicitacao(licitacao);
            Obra novaObra = obraRepository.save(obra);

            return new ObraDTO(
                    novaObra.getLicitacao(),
                    novaObra.getSecretaria(),
                    novaObra.getNotas(),
                    novaObra.getContrato(),
                    novaObra.getDescricao(),
                    novaObra.getDataInicio(),
                    novaObra.getStatusObra()
            );
        } else {
            throw new ViolacaoRegraNegocioException("Apenas usuarios ADMIN's podem criar uma obra");
        }
    }

    public Contrato gerarContrato(Long usuario_id, Long obra_id, Contrato contrato) {
        Usuario usuario = usuarioService.findById(usuario_id);
        Obra obra = findById(obra_id)
                .orElseThrow(() -> new ObraNaoEncontradaException(obra_id));

        if (usuario.isAdmin()) {
            contrato.setObra(obra);
            return contratoRepository.save(contrato);
        } else {
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem gerar um contrato");
        }
    }

    public Nota gerarNota(Long usuario_id, Long obra_id, Nota nota) {
        Usuario usuario = usuarioService.findById(usuario_id);
        Obra obra = findById(obra_id)
                .orElseThrow(() -> new ObraNaoEncontradaException(obra_id));

        if (usuario.isAdmin()) {
            nota.setObra(obra);
            return notaRepository.save(nota);
        } else {
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem gerar uma nota");
        }
    }

    public Contrato getContrato(Long usuario_id, Long obra_id) {
        Usuario usuario = usuarioService.findById(usuario_id);
        Obra obra = findById(obra_id)
                .orElseThrow(() -> new ObraNaoEncontradaException(obra_id));

        if (usuario.isAdmin()) {
            return obra.getContrato();
        } else {
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem ver os contratos");
        }
    }

    public List<Nota> getNotas(Long usuario_id, Long obra_id) {
        Usuario usuario = usuarioService.findById(usuario_id);
        Obra obra = findById(obra_id)
                .orElseThrow(() -> new ObraNaoEncontradaException(obra_id));

        if (usuario.isAdmin()) {
            return obra.getNotas();
        } else {
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem ver os contratos");
        }
    }

    public Obra save(Obra obra) {
        return obraRepository.save(obra);
    }
}
