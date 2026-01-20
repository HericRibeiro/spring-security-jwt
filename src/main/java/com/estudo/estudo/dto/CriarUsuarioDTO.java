package com.estudo.estudo.dto;

import com.estudo.estudo.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CriarUsuarioDTO {
    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8)
    private String senha;

    @Min(value = 8, message = "Idade mínima de 8 anos")
    private int idade;

    @NotNull
    private Role role;

    public CriarUsuarioDTO() {}

    public CriarUsuarioDTO(String nome, String email, String senha, int idade, Role role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.idade = idade;
        this.role = role;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
