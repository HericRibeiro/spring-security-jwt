package com.estudo.estudo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.estudo.estudo.model.Usuario;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Usuario, Long>{
    boolean existsByEmail(String email);
    
    Optional<Usuario> findByEmail(String email);
}
