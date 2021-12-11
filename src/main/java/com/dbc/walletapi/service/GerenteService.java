package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.GerenteCreateDTO;
import com.dbc.walletapi.dto.GerenteDTO;
import com.dbc.walletapi.dto.UsuarioCreateDTO;
import com.dbc.walletapi.dto.UsuarioDTO;
import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.RegraEntity;
import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.RegraRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GerenteService {

    private final ObjectMapper objectMapper;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final GerenteRepository gerenteRepository;
    private final RegraRepository regraRepository;

    public GerenteDTO create(GerenteCreateDTO gerenteCreateDTO) throws RegraDeNegocioException {
        gerenteCreateDTO.getUsuario().setRegra(2);
        UsuarioCreateDTO usuarioCreateDTO = gerenteCreateDTO.getUsuario();
        UsuarioEntity usuarioNovo = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        usuarioNovo.setRegraEntity(regraRepository.findById(usuarioCreateDTO.getRegra()).orElseThrow(() -> new RegraDeNegocioException("Regra não encontrada!")));
        String senha = new BCryptPasswordEncoder().encode(usuarioNovo.getPassword());
        usuarioNovo.setSenha(senha);
        UsuarioEntity user = usuarioRepository.save(usuarioNovo);
        GerenteEntity gerenteEntity = objectMapper.convertValue(gerenteCreateDTO, GerenteEntity.class);
        gerenteEntity.setUsuario(user);
        gerenteEntity.setStatus(TipoStatus.ATIVO);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(user,UsuarioDTO.class);
        GerenteEntity novoGerente = gerenteRepository.save(gerenteEntity);
        GerenteDTO gerenteDTO = objectMapper.convertValue(novoGerente, GerenteDTO.class);
        gerenteDTO.setUsuario(usuarioDTO);
        return gerenteDTO;
    }

    public List<GerenteDTO> list() {

        List<GerenteEntity> listaDeGerentesEntity = gerenteRepository.listaGerentesAtivos();

        List<GerenteDTO> listaDTO = new ArrayList<>();

        for(GerenteEntity gerenteEntity : listaDeGerentesEntity) {
            GerenteDTO gerenteDTO = objectMapper.convertValue(gerenteEntity, GerenteDTO.class);

            UsuarioDTO usuarioDTO = objectMapper.convertValue(gerenteEntity.getUsuario(),UsuarioDTO.class);
            usuarioDTO.setRegra(gerenteEntity.getUsuario().getRegraEntity().getIdRegra());
            gerenteDTO.setUsuario(usuarioDTO);
            listaDTO.add(gerenteDTO);
        }

        return listaDTO;

    }

    public GerenteDTO listById(Integer idGerente) throws RegraDeNegocioException {
        gerenteRepository.findById(idGerente).orElseThrow(() -> new RegraDeNegocioException("Gerente não encontrado!"));
        GerenteEntity gerenteEntity = gerenteRepository.getGerenteById(idGerente);

        return fromEntity(gerenteEntity);

    }

    public GerenteDTO update(Integer idGerente, GerenteCreateDTO gerenteCreateDTO) throws RegraDeNegocioException {

        GerenteEntity gerenteEntity = gerenteRepository.getGerenteById(idGerente);


        UsuarioEntity usuarioAtualizar = gerenteEntity.getUsuario();


        usuarioAtualizar.setRegraEntity(regraRepository.findById(gerenteCreateDTO.getUsuario().getRegra()).orElseThrow(() -> new RegraDeNegocioException("Regra não encontrada!")));
        String senha = new BCryptPasswordEncoder().encode(gerenteCreateDTO.getUsuario().getSenha());
        usuarioAtualizar.setSenha(senha);
        UsuarioEntity user = usuarioRepository.save(usuarioAtualizar);

        gerenteEntity.setUsuario(user);
        gerenteEntity.setEmail(gerenteCreateDTO.getEmail());
        gerenteEntity.setNomeCompleto(gerenteCreateDTO.getNomeCompleto());


        GerenteEntity gerenteAtualizado = gerenteRepository.save(gerenteEntity);

        return fromEntity(gerenteAtualizado);
    }

    private GerenteDTO fromEntity(GerenteEntity gerenteEntity) { // Transforma um entity em um DTO

        GerenteDTO gerenteDTO = objectMapper.convertValue(gerenteEntity, GerenteDTO.class);

        UsuarioDTO usuarioDTO = objectMapper.convertValue(gerenteEntity.getUsuario(),UsuarioDTO.class);
        usuarioDTO.setRegra(gerenteEntity.getUsuario().getRegraEntity().getIdRegra());
        gerenteDTO.setUsuario(usuarioDTO);


        return gerenteDTO;
    }

    public void delete(Integer idGerente) {

        GerenteEntity gerenteEntity = gerenteRepository.getById(idGerente);
        gerenteEntity.setStatus(TipoStatus.INATIVO);
        gerenteRepository.save(gerenteEntity);
        usuarioRepository.delete(gerenteEntity.getUsuario()); // Deleta o usuario e
    }



}
