package com.estudo.estudo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.estudo.estudo.dto.LoginDTO;
import com.estudo.estudo.service.JwtService;
import com.estudo.estudo.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto) {

        return usuarioService.autenticar(dto.getEmail(), dto.getSenha())
            .map(usuario -> {
                String token = jwtService.gerarToken(usuario.getEmail());
                return ResponseEntity.ok(token);
            })
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "E-mail ou Senha Inválidos"
            ));
    }
}
