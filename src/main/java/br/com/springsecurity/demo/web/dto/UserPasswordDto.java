package br.com.springsecurity.demo.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserPasswordDto {

    @NotNull
    private String currentPassword;

    @Size(min = 5)
    @NotNull
    private String newPassword;

    @NotNull
    private String confirmPassword;
    
}
