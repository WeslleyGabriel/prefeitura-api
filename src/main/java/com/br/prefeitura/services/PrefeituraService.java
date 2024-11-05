package com.br.prefeitura.services;

import com.br.prefeitura.enums.StatusLicitacao;
import com.br.prefeitura.enums.StatusProposta;
import com.br.prefeitura.exceptions.*;
import com.br.prefeitura.entities.Licitacao;
import com.br.prefeitura.entities.Prefeitura;
import com.br.prefeitura.entities.Proposta;
import com.br.prefeitura.entities.Usuario;
import com.br.prefeitura.repositories.LicitacaoRepository;
import com.br.prefeitura.repositories.PrefeituraRepository;
import com.br.prefeitura.repositories.PropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrefeituraService {

    @Autowired
    private PrefeituraRepository prefeituraRepository;

    @Autowired
    private LicitacaoService licitacaoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PropostaService propostaService;

    @Autowired
    private LicitacaoRepository licitacaoRepository;

    @Autowired
    private PropostaRepository propostaRepository;


    public Prefeitura save(Prefeitura prefeitura) {
        Prefeitura prefeitura1 = new Prefeitura();
        prefeitura1.setNome(prefeitura.getNome());
        prefeitura1.setEndereco(prefeitura.getEndereco());
        prefeituraRepository.save(prefeitura1);
        return prefeitura1;
    }


    public Optional<Prefeitura> findById(Long prefeituraId) {
        Optional<Prefeitura> prefeituraOpt = prefeituraRepository.findById(prefeituraId);
        if (prefeituraOpt.isPresent()) {
            return prefeituraOpt;
        } else {
            throw new PrefeituraNaoEncontradaException(prefeituraId);
        }
    }


    public List<Licitacao> getLicitacoesByPrefeituraId(Long prefeituraId) {
        Prefeitura prefeitura = findById(prefeituraId).orElseThrow(() -> new PrefeituraNaoEncontradaException(prefeituraId));
        return prefeitura.getLicitacoes();
    }


    public Proposta solicitarLicitacao(Long prefeitura_id, Long proposta_id){

        Prefeitura prefeitura = findById(prefeitura_id).orElseThrow(() -> new PrefeituraNaoEncontradaException(prefeitura_id));
        Proposta proposta = propostaService.findById(proposta_id).orElseThrow(() -> new PropostaNaoEncontradaException(proposta_id));
        Usuario usuario = proposta.getUsuario();

        if(!usuario.isAdmin()){
            prefeitura.addProposta(proposta);
            prefeituraRepository.save(prefeitura);
            return proposta;
        } else {
            throw new ViolacaoRegraNegocioException("Somente usuarios CNPJ's podem solicitar uma licitação");
        }
    }



    public Licitacao cancelarLicitacao(Long prefeitura_id, Long usuario_id, Long licitacao_id){

        Prefeitura prefeitura = findById(prefeitura_id).orElseThrow(() -> new PrefeituraNaoEncontradaException(prefeitura_id));
        Usuario usuario = usuarioService.findById(usuario_id);
        Licitacao licitacao = licitacaoService.findById(licitacao_id).orElseThrow(() -> new LicitacaoNaoEncontradaException(licitacao_id));

        if(usuario.isAdmin()){
            licitacao.setStatusLicitacao(StatusLicitacao.ENCERRADO);
            licitacaoRepository.save(licitacao);

            prefeitura.deleteLicitacao(licitacao);
            prefeituraRepository.save(prefeitura);

            return licitacao;
        } else {
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem cancelar uma licitação");
        }
    }



    public List<Proposta> listarPropostas(Long usuario_id, Long prefeitura_id){

        Prefeitura prefeitura = findById(prefeitura_id).orElseThrow(() -> new PrefeituraNaoEncontradaException(prefeitura_id));
        Usuario usuario = usuarioService.findById(usuario_id);

        if(usuario.isAdmin()){
            return prefeitura.getPropostas();
        }else{
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem listar as propostas");
        }
    }


    public Proposta aceitarProposta(Long usuario_id, Long prefeitura_id, Long proposta_id){
        Usuario usuario = usuarioService.findById(usuario_id);
        Prefeitura prefeitura = findById(prefeitura_id).orElseThrow(() -> new PrefeituraNaoEncontradaException(prefeitura_id));
        Proposta proposta = propostaService.findById(proposta_id).orElseThrow(() -> new PropostaNaoEncontradaException(proposta_id));

        if(usuario.isAdmin()){
            proposta.setStatusProposta(StatusProposta.ACEITE);
            proposta.setPrefeitura(prefeitura);
            return propostaRepository.save(proposta);
        }else{
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem aceitar propostas");
        }
    }


    public Proposta recusarProposta(Long usuario_id, Long prefeitura_id, Long proposta_id){
        Usuario usuario = usuarioService.findById(usuario_id);
        Prefeitura prefeitura = findById(prefeitura_id).orElseThrow(() -> new PrefeituraNaoEncontradaException(prefeitura_id));
        Proposta proposta = propostaService.findById(proposta_id).orElseThrow(() -> new PropostaNaoEncontradaException(proposta_id));

        if(usuario.isAdmin()){
            proposta.setStatusProposta(StatusProposta.CANCELADO);
            propostaRepository.save(proposta);
            prefeitura.deleteProposta(proposta);
            prefeituraRepository.save(prefeitura);
            return proposta;
        }else{
            throw new ViolacaoRegraNegocioException("Somente usuarios ADMIN's podem aceitar propostas");
        }
    }

}
