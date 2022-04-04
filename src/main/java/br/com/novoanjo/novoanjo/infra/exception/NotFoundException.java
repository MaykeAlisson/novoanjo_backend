package br.com.novoanjo.novoanjo.infra.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
       super(msg);
    }

}
