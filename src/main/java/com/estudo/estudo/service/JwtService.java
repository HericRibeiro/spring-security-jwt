package com.estudo.estudo.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estudo.estudo.model.Usuario;
import com.estudo.estudo.config.JwtProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Autowired
    private JwtProperties jwtProperties;

    private Key getChaveSecreta() {
        return Keys.hmacShaKeyFor(jwtProperties.getJwt().getSecret().getBytes());
    }

    public String gerarToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getJwt().getExpiration()))
            .signWith(getChaveSecreta(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String gerarTokenComUsuario(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", usuario.getId());
        claims.put("email", usuario.getEmail());
        claims.put("role", usuario.getRole().toString());

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(usuario.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getJwt().getExpiration()))
            .signWith(getChaveSecreta(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    public String extrairRole(String token) {
        return extrairClaims(token).get("role", String.class);
    }

    public Long extrairId(String token) {
        return extrairClaims(token).get("id", Long.class);
    }

    public boolean validarToken(String token, String email) {
        String emailDoToken = extrairEmail(token);
        return (emailDoToken.equals(email) && !tokenExpirado(token));
    }

    public boolean tokenExpirado(String token) {
        return extrairClaims(token).getExpiration().before(new Date());
    }

    public Claims extrairClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getChaveSecreta())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}