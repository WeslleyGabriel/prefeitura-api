package com.br.prefeitura.exceptions;

import com.br.prefeitura.exceptions.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PrefeituraNaoEncontradaException.class)
    public ResponseEntity<StandardError> handlePrefeituraNaoEncontradaException(PrefeituraNaoEncontradaException ex) {
        String error = "Prefeitura com ID não encontrada.\"!";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DataEncerramentoInvalidaException.class)
    public ResponseEntity<StandardError> handleDataEncerramentoInvalidaException(DataEncerramentoInvalidaException ex) {
        String error = "Data de encerramento invalida!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(DataFimInvalidaException.class)
    public ResponseEntity<StandardError> handleDataFimInvalidaException(DataFimInvalidaException ex) {
        String error = "Data de fim invalida!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<StandardError> handleEmailJaCadastradoException(EmailJaCadastradoException ex) {
        String error = "Email ja cadastrado!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(LicitacaoNaoEncontradaException.class)
    public ResponseEntity<StandardError> handleLicitacaoNaoEncontradaException(LicitacaoNaoEncontradaException ex) {
        String error = "Licitacao não encontrada!";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(SecretariaNaoEncontradaException.class)
    public ResponseEntity<StandardError> handleSecretariaNaoEncontradaException(SecretariaNaoEncontradaException ex) {
        String error = "Secretaria não encontrada!";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }
    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<StandardError> handleSenhaInvalidaException(SenhaInvalidaException ex) {
        String error = "Senha invalida!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<StandardError> handleUsuarioNaoEncontradoException(UsuarioNaoEncontradoException ex) {
        String error = "Usuario não encontrado!";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UsuarioIDNaoEncontradoException.class)
    public ResponseEntity<StandardError> handleUsuarioNaoEncontradoException(UsuarioIDNaoEncontradoException ex) {
        String error = "ID do usuário não encontrado!";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UsuarioJaExisteException.class)
    public ResponseEntity<StandardError> handleUsuarioJaExisteException(UsuarioJaExisteException ex) {
        String error = "Usuário já existe!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ObraNaoEncontradaException.class)
    public ResponseEntity<StandardError> handleObraNaoEncontradaException(ObraNaoEncontradaException ex) {
        String error = "Obra não encontrada!";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PropostaNaoEncontradaException.class)
    public ResponseEntity<StandardError> handlePropostaNaoEncontradaException(PropostaNaoEncontradaException ex) {
        String error = "Proposta não encontrada!";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }



    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<StandardError> handleValidacaoException(ValidacaoException ex) {
        String error = "Validação!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ViolacaoRegraNegocioException.class)
    public ResponseEntity<StandardError> handleViolacaoRegraNegocioException(ViolacaoRegraNegocioException ex) {
        String error = "Violação da regra de negócio!";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(status ,error, ex.getMessage());
        return ResponseEntity.status(status).body(err);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGenericException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado.");
//    }
}
