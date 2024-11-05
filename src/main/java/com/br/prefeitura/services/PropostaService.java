package com.br.prefeitura.services;

import com.br.prefeitura.dtos.PropostaDTO;
import com.br.prefeitura.exceptions.LicitacaoNaoEncontradaException;
import com.br.prefeitura.exceptions.PropostaNaoEncontradaException;
import com.br.prefeitura.exceptions.UsuarioIDNaoEncontradoException;
import com.br.prefeitura.exceptions.ViolacaoRegraNegocioException;
import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.entities.Proposta;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropostaService {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LicitacaoService licitacaoService;

    public Proposta save(Proposta proposta) {
        return propostaRepository.save(proposta);
    }


    public Optional<Proposta> findById(Long propostaId) {
        Optional<Proposta> propostaOpt = propostaRepository.findById(propostaId);
        if (propostaOpt.isPresent()) {
            return propostaOpt;
        } else {
            throw new PropostaNaoEncontradaException(propostaId);
        }
    }

    public Proposta criarProposta(Long usuario_id, Long licitacao_id, PropostaDTO propostaDTO){

        Usuario usuario = usuarioService.findById(usuario_id);
        Licitacao licitacao = licitacaoService.findById(licitacao_id).orElseThrow(() -> new LicitacaoNaoEncontradaException(licitacao_id));

        if(!usuario.isAdmin()){
            Proposta proposta = new Proposta();
            proposta.setUsuario(usuario);
            proposta.setLicitacao(licitacao);
            proposta.setDescricao(propostaDTO.getDescricao());
            proposta.setValor(propostaDTO.getValor());
            proposta.setStatusProposta(propostaDTO.getStatusProposta());
            return save(proposta);
        }else{
            throw new ViolacaoRegraNegocioException("Somente usuarios CNPJ's podem solicitar uma licitação!");
        }

    }
}
