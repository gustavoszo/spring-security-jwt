package br.com.springsecurity.demo.security;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtils {
    
    private static String SECRET_KEY = "mySecretKey";

    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 0;
    public static final long EXPIRE_MINUTES = 30;

    private static Algorithm generateKey() {
        return Algorithm.HMAC256(SECRET_KEY);
    }

    private static Date toExpire(Date start) { // Expiração do token
        LocalDateTime date = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(); 
        LocalDateTime end = date.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String generateToken(String username) {
        Date limit = toExpire(new Date());
        String token = JWT.create()
            .withIssuer("auth-api") // Emissor
            .withSubject(username)
            .withExpiresAt(limit)
            .sign(generateKey());
        return token;
    }

    public static boolean isTokenValid(String token) {
        try {
            JWT.require(generateKey())
                .withIssuer("auth-api")
                .build()
                .verify(token);
            return true;
        } catch(JWTVerificationException e) {
            log.error("Token inválido " + e.getMessage());
        }
        return false;
    }

    public static String getUsernameFromToken(String token) {
        try {
            return JWT.require(generateKey())
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();
        } catch(JWTVerificationException e) {
            log.error("Token inválido " +  e.getMessage()); 
        }
        return null;
    }

}
