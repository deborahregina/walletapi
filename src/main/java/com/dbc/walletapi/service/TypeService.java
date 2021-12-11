package com.dbc.walletapi.service;


import com.dbc.walletapi.dto.ServicoDTO;
import com.dbc.walletapi.dto.TypeDTO;
import com.dbc.walletapi.dto.UsuarioDTO;
import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.ServicoEntity;
import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    private final GerenteRepository gerenteRepository;


    public TypeDTO list(String username) throws RegraDeNegocioException {

        TypeDTO typeUserSistema = new TypeDTO();
        UsuarioEntity usuarioRecuperado = usuarioService.findByLogin(username)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));

        typeUserSistema.setIdUser(usuarioRecuperado.getIdUsuario());
        typeUserSistema.setUsuario(usuarioRecuperado.getUsuario());
        if(usuarioRecuperado.getIdUsuario() == 1) {
            return typeUserSistema;
        }

        GerenteEntity gerenteEntity = gerenteRepository.findById(usuarioRecuperado.getGerenteEntity().getIdGerente())
                .orElseThrow(() ->new RegraDeNegocioException("Gerente não encontrado!"));

            List<ServicoDTO> listServicosDTO = gerenteEntity.getServicos().stream()
                    .map(servicoEntity -> objectMapper.convertValue(servicoEntity,ServicoDTO.class)).collect(Collectors.toList());


        typeUserSistema.setIdGerente(gerenteEntity.getIdGerente());
        typeUserSistema.setEmail(gerenteEntity.getEmail());
        typeUserSistema.setNomeCompleto(gerenteEntity.getNomeCompleto());
        typeUserSistema.setServicoDTOList(listServicosDTO);
        typeUserSistema.setStatus(gerenteEntity.getStatus());

        return typeUserSistema;

    }
}