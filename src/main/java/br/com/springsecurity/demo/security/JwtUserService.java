package br.com.springsecurity.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.springsecurity.demo.entity.User;
import br.com.springsecurity.demo.repository.UserRepository;

@Service
public class JwtUserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        return new JwtUserDetails(user);
    }
    
    public JwtToken getToken(String username) {
        return new JwtToken(JwtUtils.generateToken(username));
    }

}
