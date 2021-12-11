package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.UsuarioCreateDTO;
import com.dbc.walletapi.dto.UsuarioDTO;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.repository.RegraRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.dbc.walletapi.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final RegraRepository regraRepository;

    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByUsuarioAndSenha(login, senha);
    }

    public Optional<UsuarioEntity> findByLogin(String login){
        return usuarioRepository.findByUsuario(login);
    }

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) {

        UsuarioEntity entity = new UsuarioEntity();
        entity.setUsuario(usuarioCreateDTO.getUsuario());
        entity.setSenha(new BCryptPasswordEncoder().encode(usuarioCreateDTO.getSenha()));
        entity.setRegraEntity(regraRepository.findById(usuarioCreateDTO.getRegra()).orElse(null));

        UsuarioEntity save = usuarioRepository.save(entity);
        return new UsuarioDTO(save.getIdUsuario(), save.getUsername() , save.getRegraEntity().getIdRegra());

    }

    public List<UsuarioDTO> list() {

        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();

        return usuarioEntities.stream().map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class)).collect(Collectors.toList());

    }
}