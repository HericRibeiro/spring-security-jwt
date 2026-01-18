package com.estudo.estudo.controller;
import com.estudo.estudo.dto.CriarUsuarioDTO;
import com.estudo.estudo.dto.UsuarioDTO;
import com.estudo.estudo.service.UsuarioService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public UsuarioDTO obterUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuário não encontrado"
            ));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criarUsuario(@Valid @RequestBody CriarUsuarioDTO dto) {
        if (usuarioService.emailJaExiste(dto.getEmail())) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Email já cadastrado no sistema"
            );
        }

        UsuarioDTO usuarioCriado = usuarioService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizarUsuario(@PathVariable Long id, @Valid @RequestBody CriarUsuarioDTO dto) {
        return usuarioService.atualizar(id, dto)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuário não encontrado"
            ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        boolean deletado = usuarioService.deletar(id);

        if (deletado) {
            return ResponseEntity.ok("Usuário deletado com sucesso");
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Usuário não encontrado"
            );
        }
    }
}