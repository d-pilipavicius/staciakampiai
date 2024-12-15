package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) throws UnsupportedEncodingException {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(generateKey())
                .compact();
    }

    public String getClaimsFromToken(String){
        Claims claims = Jwts.parser()
                .sf
    }

    private SecretKey generateKey(){
        String encodedSecretString = Base64.getEncoder().encodeToString(secret.getBytes());
        byte[] decodedBytes = Base64.getDecoder().decode(encodedSecretString);
        return Keys.hmacShaKeyFor(decodedBytes);
    }
}
