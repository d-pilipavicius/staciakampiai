package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JWTUtils {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .claim("roles", roles)
                .subject(username)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(generateKey())
                .compact();
    }

    public Claims getPayloadOfToken(String token){
        Claims claims;
        try{
            claims = Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch(Exception e){
            System.out.println("something went wrong with parsing!!!!");
            return null;
        }
        return claims;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("lalaalla");;
            return false;
        } catch (SignatureException e) {
            System.out.println("gagaga");;
            return false;
        }
    }

    private SecretKey generateKey(){
        String encodedSecretString = Base64.getEncoder().encodeToString(secret.getBytes());
        byte[] decodedBytes = Base64.getDecoder().decode(encodedSecretString);
        return Keys.hmacShaKeyFor(decodedBytes);
    }
}