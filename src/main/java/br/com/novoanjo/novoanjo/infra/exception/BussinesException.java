package br.com.novoanjo.novoanjo.infra.exception;

public class BussinesException extends RuntimeException{
    public BussinesException(final String msg) {
        super(msg);
    }
}
