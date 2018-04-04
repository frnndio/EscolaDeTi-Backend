package com.time02escoladeti.back.Recursos.Excecoes;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private HttpStatus codigoStatus;

    public ServiceException (String mensagem) {
        super(mensagem);
        this.codigoStatus = codigoStatus;
    }

    public ServiceException (String mensagem, HttpStatus codigoStatus) {
        super(mensagem);
        this.codigoStatus = codigoStatus;
    }

    public HttpStatus getCodigoStatus() {
        return codigoStatus;
    }
}
