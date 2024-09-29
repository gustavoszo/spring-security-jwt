package br.com.springsecurity.demo.web.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.springsecurity.demo.exception.EntityNotFoundException;
import br.com.springsecurity.demo.exception.ErrorPasswordException;
import br.com.springsecurity.demo.exception.UsernameUniqueValidationException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandler {

    // A exceção AccessDeniedException é lançada em aplicações Spring Security quando um usuário autenticado tenta acessar um recurso para o qual não tem permissão.
    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class) 
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        return ResponseEntity
               .status(HttpStatus.FORBIDDEN)
               .contentType(MediaType.APPLICATION_JSON)
               .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));                                                                                                                 
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class) 
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request, BindingResult result) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage(), result));
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(UsernameUniqueValidationException.class) 
    public ResponseEntity<ErrorMessage> usernameUniqueValidationException(UsernameUniqueValidationException exception, HttpServletRequest request) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.CONFLICT, exception.getMessage()));
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(ErrorPasswordException.class) 
    public ResponseEntity<ErrorMessage> errorPasswordException(RuntimeException exception, HttpServletRequest request) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, exception.getMessage()));
    }
    
    @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class) 
    public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }

}
