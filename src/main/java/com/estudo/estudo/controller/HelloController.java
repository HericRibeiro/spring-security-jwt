package com.estudo.estudo.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {
    
    @GetMapping("hello")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("ola")
    public String ola() {
        return "Olá! Esse é meu primeiro projeto com spring boot";
    }

    // Receber nome como parâmetro na URL
    // Ex: /saudar?nome=Heric
    @GetMapping("/saudar")
    public String saudar(@RequestParam String nome) {
        return "Olá, " + nome + " É um prazer te conhecer!";
    }

    // Receber nome no caminho: /ola/Heric
    @GetMapping("/ola/{nome}")
    public String olaNome(@PathVariable String nome) {
        return "Olá, " + nome + "! Seja bem vindo ao meu projeto Spring Boot!";
    }

    // Receber multiplos paramentros na URl
    // Ex: /apresentar?nome=Heric&idade=21
    @GetMapping("/apresentar")
    public String apresentar(@RequestParam String nome, @RequestParam int idade) {
        return "Olá, meu nome é " + nome + " e tenho " + idade + " anos!";
    }
}
