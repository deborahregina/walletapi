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
    private final GerenteRepository gerenteRepository;



    public Optional<UsuarioEntity> findByLogin(String login) throws RegraDeNegocioException {
        Optional<UsuarioEntity> userEntity = usuarioRepository.findByUsuario(login);

        try {
            if(userEntity.get().getIdUsuario() != 1) {
                GerenteEntity gerenteEntity = gerenteRepository.findById(userEntity.get().getGerenteEntity().getIdGerente())
                        .orElseThrow(()-> new RegraDeNegocioException("Gerente não encontrado!"));

                if(gerenteEntity.getStatus() == TipoStatus.INATIVO) {
                    throw new RegraDeNegocioException("Não é possível logar com usuário inativo");
                }
            }
            return userEntity;
        } catch (NoSuchElementException ex) {
            throw new RegraDeNegocioException("Usuário ou senha inválidos");
        }

    }

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {

        UsuarioEntity entity = new UsuarioEntity();
        entity.setUsuario(usuarioCreateDTO.getUsuario());
        entity.setSenha(new BCryptPasswordEncoder().encode(usuarioCreateDTO.getSenha()));
        entity.setRegraEntity(regraRepository.findById(usuarioCreateDTO.getRegra())
                .orElseThrow(() ->  new RegraDeNegocioException("Regra não encontrada!")));

        entity.setStatus(TipoStatus.ATIVO);
        UsuarioEntity save = usuarioRepository.save(entity);
        return new UsuarioDTO(save.getIdUsuario(), save.getUsername() , save.getRegraEntity().getIdRegra());

    }

    public List<UsuarioDTO> list() {

        List<UsuarioEntity> usuarioEntities = usuarioRepository.findAll();

        return usuarioEntities.stream().map(usuarioEntity -> objectMapper.convertValue(usuarioEntity, UsuarioDTO.class)).collect(Collectors.toList());

    }


}