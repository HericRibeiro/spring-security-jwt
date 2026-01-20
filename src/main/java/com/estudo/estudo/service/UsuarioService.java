package com.estudo.estudo.service;

import com.estudo.estudo.dto.CriarUsuarioDTO;
import com.estudo.estudo.dto.UsuarioDTO;
import com.estudo.estudo.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private List<Usuario> usuarios = new ArrayList<>();
    private Long proximoId = 1L;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> listarTodos() {
        return usuarios.stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return usuarios.stream()
            .filter(u -> u.getId().equals(id))
            .findFirst()
            .map(this::converterParaDTO);
    }

    public UsuarioDTO criar(CriarUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(proximoId++);
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setIdade(dto.getIdade());
        usuario.setRole(dto.getRole());

        usuarios.add(usuario);

        return converterParaDTO(usuario);
    }

    public Optional<UsuarioDTO> atualizar(Long id, CriarUsuarioDTO dto) {
        Optional<Usuario> usuarioExistente = usuarios.stream()
            .filter(u -> u.getId().equals(id))
            .findFirst();

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
            usuario.setIdade(dto.getIdade());
            usuario.setRole(dto.getRole());

            return Optional.of(converterParaDTO(usuario));
        }

        return Optional.empty();
    }

    public boolean deletar(Long id) {
        return usuarios.removeIf(u -> u.getId().equals(id));
    }

    public boolean emailJaExiste(String email) {
        return usuarios.stream()
            .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarios.stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    private UsuarioDTO converterParaDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setIdade(usuario.getIdade());
        dto.setRole(usuario.getRole());
        return dto;
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        Optional<Usuario> usuarioOpt = buscarPorEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (passwordEncoder.matches(senha, usuario.getSenha())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
}
