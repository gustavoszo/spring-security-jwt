package br.com.springsecurity.demo.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserCreateDto {
    
    @Email
    @NotNull
    private String username;

    @Size(min = 5)
    @NotNull
    private String password;

}
