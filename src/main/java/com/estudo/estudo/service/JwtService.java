package com.estudo.estudo.service;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final Key chaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long EXPIRACAO = 1000 * 60 * 60;

    public String gerarToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
            .signWith(chaveSecreta)
            .compact();
    }
}
