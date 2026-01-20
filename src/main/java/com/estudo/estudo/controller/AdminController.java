package com.estudo.estudo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public String dashboard() {
        return "🎯 Bem-vindo ao painel de ADMINISTRADOR!";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @GetMapping("/moderacao")
    public String moderacao() {
        return "🛡️ Área de MODERAÇÃO - Admin ou Moderator";
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'USER')")
    @GetMapping("/perfil")
    public String perfil() {
        return "👤 Seu perfil de usuário";
    }
}