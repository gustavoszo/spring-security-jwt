package br.com.springsecurity.demo.security;

import org.springframework.security.core.authority.AuthorityUtils;

import br.com.springsecurity.demo.entity.User;

public class JwtUserDetails extends org.springframework.security.core.userdetails.User {

    private User user;

    public JwtUserDetails(User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Long getId() {
        return this.user.getId();
    }

    public String getRole() {
        String role = this.user.getRole().getRole();
        return role;
    }
    
}
