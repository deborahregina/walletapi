package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.GerenteCreateDTO;
import com.dbc.walletapi.dto.GerenteDTO;
import com.dbc.walletapi.dto.UsuarioDTO;
import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GerenteService {

    private final ObjectMapper objectMapper;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final GerenteRepository gerenteRepository;

    public GerenteDTO create(GerenteCreateDTO gerenteCreateDTO) {

        GerenteEntity gerenteEntity = objectMapper.convertValue(gerenteCreateDTO, GerenteEntity.class);
        GerenteEntity novoGerente = gerenteRepository.save(gerenteEntity);
        GerenteDTO gerenteDTO = objectMapper.convertValue(novoGerente, GerenteDTO.class);
        return gerenteDTO;

    }


}
