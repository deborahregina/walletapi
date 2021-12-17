package com.dbc.walletapi.service;

import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.dbc.walletapi.repository.UsuarioRepository;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Optional<UsuarioEntity> findByLogin(String login) throws RegraDeNegocioException {
        Optional<UsuarioEntity> userEntity = usuarioRepository.findByUsuario(login);
        try {
            return userEntity;
        } catch (NoSuchElementException ex) {
            throw new RegraDeNegocioException("Usuário ou senha inválidos");
        }
    }
}