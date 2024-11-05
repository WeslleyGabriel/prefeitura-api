package com.br.prefeitura.exceptions;

import org.springframework.http.HttpStatus;

import java.io.Serializable;


public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private HttpStatus status;
    private String error;
    private String message;

    public StandardError(HttpStatus status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;

    }
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "StandardError{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
