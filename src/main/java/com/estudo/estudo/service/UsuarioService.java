package com.estudo.estudo.service;

import com.estudo.estudo.dto.CriarUsuarioDTO;
import com.estudo.estudo.dto.UsuarioDTO;
import com.estudo.estudo.model.Usuario;
import com.estudo.estudo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UsuarioDTO> listarTodos() {
        return userRepository.findAll().stream()
            .map(this::converterParaDTO)
            .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscarPorId(Long id) {
        return userRepository.findById(id)
            .map(this::converterParaDTO);
    }

    public UsuarioDTO criar(CriarUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setIdade(dto.getIdade());
        usuario.setRole(dto.getRole());

        Usuario savedUser = userRepository.save(usuario);

        return converterParaDTO(savedUser);
    }

    public Optional<UsuarioDTO> atualizar(Long id, CriarUsuarioDTO dto) {
        Optional<Usuario> usuarioExistente = userRepository.findById(id);

        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNome(dto.getNome());
            usuario.setEmail(dto.getEmail());
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
            usuario.setIdade(dto.getIdade());
            usuario.setRole(dto.getRole());

            Usuario atualizarUsuario = userRepository.save(usuario);

            return Optional.of(converterParaDTO(atualizarUsuario));
        }

        return Optional.empty();
    }

    public boolean deletar(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean emailJaExiste(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return userRepository.findByEmail(email);
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
