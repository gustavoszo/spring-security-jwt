package br.com.springsecurity.demo.exception;

public class UsernameUniqueValidationException extends RuntimeException {
    
    public UsernameUniqueValidationException(String msg) {
        super(msg);
    }

}
