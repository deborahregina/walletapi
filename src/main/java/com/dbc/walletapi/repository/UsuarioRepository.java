package com.dbc.walletapi.repository;

import com.dbc.walletapi.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {
    Optional<UsuarioEntity> findByUsuarioAndSenha(String usuario, String senha);
    Optional<UsuarioEntity> findByUsuario(String usuario);
}