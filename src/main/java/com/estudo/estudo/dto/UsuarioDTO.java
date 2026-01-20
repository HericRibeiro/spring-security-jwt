package com.estudo.estudo.dto;

import com.estudo.estudo.enums.Role;

public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private int idade;
    private Role role;

    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nome, String email, int idade, Role role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.idade = idade;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
