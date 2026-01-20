package com.estudo.estudo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estudo.estudo.dto.LoginDTO;
import com.estudo.estudo.model.Usuario;

@Service
public class AuthService {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private JwtService jwtService;
    
    public String login(LoginDTO loginDTO) {
        Usuario usuario = usuarioService.autenticar(loginDTO.getEmail(), loginDTO.getSenha())
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));
        
        return jwtService.gerarTokenComUsuario(usuario);
    }
}