package br.com.springsecurity.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.springsecurity.demo.entity.User;
import br.com.springsecurity.demo.exception.EntityNotFoundException;
import br.com.springsecurity.demo.exception.ErrorPasswordException;
import br.com.springsecurity.demo.exception.UsernameUniqueValidationException;
import br.com.springsecurity.demo.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return repository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new UsernameUniqueValidationException(String.format("O username %s já existe", user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> 
            new EntityNotFoundException(String.format("User id '%d' não encontrado", id))
        );
    }

    @Transactional
    public User updatePassword(Long id, String senhaAtual, String novaSenha, String confirmacaoSenha) {
        User user = findById(id);

        if (! passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new ErrorPasswordException("Sua senha não confere");
        }

        if (! novaSenha.equals(confirmacaoSenha)) {
            throw new ErrorPasswordException("Nova senha e confirmação de senha diferentes");
        }
        // O hibernate já vai enviar a atualização para o banco, pois ele tem o controle do objeto enquanto a requisição é feita
        user.setPassword(passwordEncoder.encode(confirmacaoSenha));
        return user;
    }

}
