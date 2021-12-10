package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.GerenteCreateDTO;
import com.dbc.walletapi.dto.GerenteDTO;
import com.dbc.walletapi.dto.UsuarioCreateDTO;
import com.dbc.walletapi.dto.UsuarioDTO;
import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.RegraEntity;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.RegraRepository;
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
    private final RegraRepository regraRepository;

    public GerenteDTO create(GerenteCreateDTO gerenteCreateDTO) throws RegraDeNegocioException {


        UsuarioCreateDTO usuarioCreateDTO = gerenteCreateDTO.getUsuario();
        UsuarioEntity usuarioNovo = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);


        usuarioNovo.setRegraEntity(regraRepository.findById(usuarioCreateDTO.getRegra()).orElseThrow(() -> new RegraDeNegocioException("Regra não encontrada!")));
        UsuarioEntity user = usuarioRepository.save(usuarioNovo);


        GerenteEntity gerenteEntity = objectMapper.convertValue(gerenteCreateDTO, GerenteEntity.class);
        gerenteEntity.setUsuario(user);
        GerenteEntity novoGerente = gerenteRepository.save(gerenteEntity);

        GerenteDTO gerenteDTO = objectMapper.convertValue(novoGerente, GerenteDTO.class);
        return gerenteDTO;

    }


}
