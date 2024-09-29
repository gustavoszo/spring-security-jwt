package br.com.springsecurity.demo.exception;

public class ErrorPasswordException extends RuntimeException {
    
    public ErrorPasswordException(String msg) {
        super(msg);
    }

}
