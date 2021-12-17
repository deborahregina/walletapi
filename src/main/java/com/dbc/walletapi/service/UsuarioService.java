package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.UsuarioCreateDTO;
import com.dbc.walletapi.dto.UsuarioDTO;
import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.RegraRepository;
import com.dbc.walletapi.security.IAuthenticationFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.dbc.walletapi.repository.UsuarioRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final RegraRepository regraRepository;


    public Optional<UsuarioEntity> findByLogin(String login) throws RegraDeNegocioException {
        Optional<UsuarioEntity> userEntity = usuarioRepository.findByUsuario(login);

        try {
            return userEntity;
        } catch (NoSuchElementException ex) {
            throw new RegraDeNegocioException("Usuário ou senha inválidos");
        }

    }


}