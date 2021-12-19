package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.*;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.RegraRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GerenteService {

    private final ObjectMapper objectMapper;
    private final UsuarioRepository usuarioRepository;
    private final GerenteRepository gerenteRepository;
    private final RegraRepository regraRepository;
    private final ServicoService servicoService;

    public GerenteDTO create(GerenteCreateDTO gerenteCreateDTO) throws RegraDeNegocioException {
        // Primeiro deve ser salvo o usuário do novo gerente criado.
        gerenteCreateDTO.getUsuario().setRegra(2);
        // seta regra 2 (gerente) para o novo usuário que vai ser criado, para o gerente.
        UsuarioEntity usuarioNovo = objectMapper.convertValue(gerenteCreateDTO.getUsuario(), UsuarioEntity.class); //conversão de UsuarioCreateDTO para UsuarioEntity.

        usuarioNovo.setRegraEntity(regraRepository.findById(gerenteCreateDTO.getUsuario()
                .getRegra()).orElseThrow(() -> new RegraDeNegocioException("Regra não encontrada!"))); // setando regra para usuário a ser criado.

        try {
            usuarioNovo.setSenha(new BCryptPasswordEncoder().encode(usuarioNovo.getPassword()));
            usuarioNovo.setStatus(TipoStatus.ATIVO); // usuário novo definido como ativo
            UsuarioEntity user = usuarioRepository.save(usuarioNovo); // Salvando usuário
            // Agora se cria o gerente, depois de ter se criado seu usuário.
            GerenteEntity gerenteEntity = objectMapper.convertValue(gerenteCreateDTO, GerenteEntity.class);
            gerenteEntity.setUsuario(user); // usuário criado é setado para o novo gerente.
            gerenteEntity.setStatus(TipoStatus.ATIVO); // status do novo gerente definido como ativo
            GerenteEntity novoGerente = gerenteRepository.save(gerenteEntity); // salvando gerente novo no banco de dados
            return fromEntity(novoGerente); // retorno de um GerenteDTO.

        } catch (IllegalArgumentException ex) {
            throw new RegraDeNegocioException("Senha não pode ser nula!");
        }

    }

    public List<GerenteDTO> list() {
        List<GerenteEntity> listaDeGerentesEntity = gerenteRepository.listaGerentesAtivos(); // Lista apenas gerentes ativos.
        return listaDeGerentesEntity.stream()
                .map(gerenteEntity -> fromEntity(gerenteEntity)).collect(Collectors.toList());
    }

    public GerenteDTO listById(Integer idGerente) throws RegraDeNegocioException { //Lista gerentes por ID independente dos status
        return fromEntity(gerenteRepository.findById(idGerente)
                .orElseThrow(() -> new RegraDeNegocioException("Gerente não encontrado!")));
    }

    public GerenteDTO update(Integer idGerente, GerenteAtualizaDTO gerenteAtualizaDTO) throws RegraDeNegocioException {
        GerenteEntity gerenteEntity = gerenteRepository.findById(idGerente).orElseThrow(() -> new RegraDeNegocioException("Gerente não encontrado!"));
        gerenteEntity.setEmail(gerenteAtualizaDTO.getEmail());
        gerenteEntity.setNomeCompleto(gerenteAtualizaDTO.getNomeCompleto());
        GerenteEntity gerenteAtualizado = gerenteRepository.save(gerenteEntity);
        return fromEntity(gerenteAtualizado);
    }

    public GerenteDTO fromEntity(GerenteEntity gerenteEntity) { // Transforma um entity em um DTO, método genérico.
        GerenteDTO gerenteDTO = objectMapper.convertValue(gerenteEntity, GerenteDTO.class);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(gerenteEntity.getUsuario(),UsuarioDTO.class);
        usuarioDTO.setRegra(gerenteEntity.getUsuario().getRegraEntity().getIdRegra());
        gerenteDTO.setUsuario(usuarioDTO);
        return gerenteDTO;
    }

    public void delete(Integer idGerente) throws RegraDeNegocioException {
        GerenteEntity gerenteEntity = gerenteRepository.findById(idGerente).orElseThrow(() -> new RegraDeNegocioException("Gerente não encontrado"));
        if (gerenteEntity.getStatus() == TipoStatus.INATIVO) {
            throw new RegraDeNegocioException("Este gerente já está inativo.");
        }
        if (servicoService.servicosInativos(gerenteEntity.getServicos())) { // se o gerente não tem serviços ativos, ele é inativado, e seu usuário também.
            gerenteEntity.setStatus(TipoStatus.INATIVO);
            gerenteEntity.getUsuario().setStatus(TipoStatus.INATIVO); // desativa usuário do gerente
            gerenteRepository.save(gerenteEntity);
        }
        else {
            throw new RegraDeNegocioException("Gerente tem serviços ativos");
        }
    }

    public List<GerenteDTO> listByName(String nome) { // Lista por nome independente do status.
        return gerenteRepository.findAll()
                .stream()
                .filter(gerente -> gerente.getNomeCompleto().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList()).stream()
                .map(gerente -> fromEntity(gerente)).collect(Collectors.toList());
    }


    public GerenteDTO alteraSenha(LoginCreateDTO loginDTO, Integer idGerente) throws RegraDeNegocioException {

        GerenteEntity gerente = gerenteRepository.findById(idGerente)
                .orElseThrow(() -> new RegraDeNegocioException("Gerente não encontrado!"));

        UsuarioEntity usuario = gerente.getUsuario();
        usuario.setSenha(new BCryptPasswordEncoder().encode(loginDTO.getSenha()));
        usuario.setUsuario(loginDTO.getUsuario());

        usuarioRepository.save(usuario);

        return fromEntity(gerente);
    }
}
