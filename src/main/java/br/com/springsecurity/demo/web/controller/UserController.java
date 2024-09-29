package br.com.springsecurity.demo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.springsecurity.demo.entity.User;
import br.com.springsecurity.demo.service.UserService;
import br.com.springsecurity.demo.web.dto.UserCreateDto;
import br.com.springsecurity.demo.web.dto.UserPasswordDto;
import br.com.springsecurity.demo.web.dto.UserResponseDto;
import br.com.springsecurity.demo.web.dto.mapper.UserMapper;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateDto dto) {
        User user = service.save(UserMapper.toUser(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> findAll() {
        List<User> users = service.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    } 

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENTE') AND #id == authentication.principal.id)")
    public ResponseEntity<User> findById(@PathVariable("id") Long id) {
        User user = service.findById(id);
        return ResponseEntity.ok(user);
    } 

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') AND (#id == authentication.principal.id)")
    public ResponseEntity<UserResponseDto> updatePassword(@PathVariable("id") Long id, @Valid @RequestBody UserPasswordDto dto) {
        User user = service.updatePassword(id, dto.getCurrentPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.ok(UserMapper.toDto(user));
    }

}
