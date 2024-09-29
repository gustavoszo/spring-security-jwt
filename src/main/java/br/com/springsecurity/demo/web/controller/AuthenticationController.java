package br.com.springsecurity.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springsecurity.demo.security.JwtToken;
import br.com.springsecurity.demo.security.JwtUserService;
import br.com.springsecurity.demo.web.dto.LoginDto;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUserService service;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        authenticationManager.authenticate(authenticationToken);
        JwtToken token = service.getToken(dto.getUsername());
        return ResponseEntity.ok(token);
    }

}
