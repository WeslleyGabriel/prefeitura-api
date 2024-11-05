package com.br.prefeitura.services;

import com.br.prefeitura.dtos.*;
import com.br.prefeitura.enums.StatusLicitacao;
import com.br.prefeitura.enums.StatusObra;
import com.br.prefeitura.exceptions.*;
import com.br.prefeitura.entities.*;
import com.br.prefeitura.repositories.LicitacaoRepository;
import com.br.prefeitura.repositories.ObraRepository;
import com.br.prefeitura.repositories.SecretariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecretariaService {

    @Autowired
    private ObraService obraService;

    @Autowired
    private SecretariaRepository secretariaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LicitacaoService licitacaoService;

    @Autowired
    private LicitacaoRepository licitacaoRepository;

    @Autowired
    private ObraRepository obraRepository;




    public SecretariaDTO save(Secretaria secretaria){
        Secretaria secretaria1 = secretariaRepository.save(secretaria);
        return new SecretariaDTO(
                secretaria1.getPrefeitura(),
                secretaria1.getNome(),
                secretaria1.getEmail(),
                secretaria1.getSenha());
    }


    public List<Secretaria> findAll() {
        return secretariaRepository.findAll();
    }


    public Optional<Secretaria> findById(Long secretariaId) {
        Optional<Secretaria> secretariaOpt = secretariaRepository.findById(secretariaId);
        if (secretariaOpt.isPresent()) {
            return secretariaOpt;
        } else {
            throw new SecretariaNaoEncontradaException(secretariaId);
        }
    }



    public LicitacaoDTO abrirLicitacao(Long usuario_id, Long secretaria_id, Licitacao licitacao){

        Usuario usuario = usuarioService.findById(usuario_id);
        Secretaria secretaria = findById(secretaria_id).orElseThrow(() -> new SecretariaNaoEncontradaException(secretaria_id));
        Prefeitura prefeitura = secretaria.getPrefeitura();

        if(usuario.isAdmin()){
            licitacao.setPrefeitura(prefeitura);
            licitacao.setSecretaria(secretaria);
            licitacao.setStatusLicitacao(StatusLicitacao.EM_ANDAMENTO);

            Licitacao licitacao1 = licitacaoRepository.save(licitacao);

            return new LicitacaoDTO(
                    licitacao1.getNumero(),
                    licitacao1.getValorEstimado(),
                    licitacao1.getTipo(),
                    licitacao1.getStatusLicitacao());

        }else{
            throw new ViolacaoRegraNegocioException("Esse usuário não pode criar uma licitação!");
        }
    }


    public Licitacao alterarStatusLicitacao(Long usuario_id, Long licitacao_id, LicitacaoStatusDTO licitacaoStatusDTO){

        Usuario usuario = usuarioService.findById(usuario_id);
        Licitacao licitacao = licitacaoService.findById(licitacao_id).orElseThrow(() -> new LicitacaoNaoEncontradaException(licitacao_id));

        if(usuario.isAdmin()){
            licitacao.setStatusLicitacao(licitacaoStatusDTO.getStatusLicitacao());
            return licitacaoRepository.save(licitacao);
        }else{
            throw new ViolacaoRegraNegocioException("Esse usuário não pode alterar uma licitação!");
        }
    }


    public Licitacao findLicitacaoById(Long secretaria_id, Long licitacao_id){
        Secretaria secretaria = findById(secretaria_id).orElseThrow(() -> new SecretariaNaoEncontradaException(secretaria_id));
        return secretaria.findLicitacaoById(licitacao_id);
    }


    public List<Licitacao> findAllLicitacoes(Long secretaria_id){
        Secretaria secretaria = findById(secretaria_id).orElseThrow(() -> new UsuarioIDNaoEncontradoException(secretaria_id));
        return secretaria.getLicitacoes();
    }


    public Obra iniciarObra(Long usuario_id, Long licitacao_id, Long secretaria_id, ObraDTO obraDTO){

        Usuario usuario = usuarioService.findById(usuario_id);
        Licitacao licitacao = licitacaoService.findById(licitacao_id).orElseThrow(() -> new LicitacaoNaoEncontradaException(licitacao_id));
        Secretaria secretaria = findById(secretaria_id).orElseThrow(() -> new SecretariaNaoEncontradaException(secretaria_id));
        Prefeitura prefeitura = secretaria.getPrefeitura();

        if(usuario.isAdmin()){
            Obra obra = new Obra();
            obra.setLicitacao(licitacao);
            obra.setSecretaria(secretaria);
            obra.setDescricao(obraDTO.getDescricao());
            obra.setStatusObra(StatusObra.EM_ANDAMENTO);
            return obraRepository.save(obra);
        }else{
            throw new ViolacaoRegraNegocioException("Esse usuário não pode criar uma obra!");
        }
    }


    public List<Obra> findAllObras(Long secretaria_id){
        Secretaria secretaria = findById(secretaria_id).orElseThrow(() -> new SecretariaNaoEncontradaException(secretaria_id));
        return secretaria.getObras();
    }


    public Obra findObraById(Long secretaria_id, Long obra_id){
        Secretaria secretaria = findById(secretaria_id).orElseThrow(() -> new SecretariaNaoEncontradaException(secretaria_id));
        return secretaria.findObraById(obra_id);
    }



    public Obra alterarStatusObra(Long usuario_id, Long obra_id, ObraStatusDTO obraStatusDTO){

        Usuario usuario = usuarioService.findById(usuario_id);;
        Obra obra = obraService.findById(obra_id).orElseThrow(() -> new ObraNaoEncontradaException(obra_id));;

        if(usuario.isAdmin()){
            obra.setStatusObra(obraStatusDTO.getStatusObra());
            return obraRepository.save(obra);
        }else{
            throw new ViolacaoRegraNegocioException("Esse usuário não pode criar uma licitação!");
        }
    }

}
