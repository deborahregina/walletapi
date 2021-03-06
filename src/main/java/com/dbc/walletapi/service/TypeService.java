package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final GerenteRepository gerenteRepository;
    private final ServicoService servicoService;


    public TypeDTO list(String idUsuario) throws RegraDeNegocioException {

        try{
            Integer idUser = Integer.valueOf(idUsuario); // Transforma a string que contém o ID do usuário em inteiro
            TypeDTO typeUserSistema = new TypeDTO();
            UsuarioEntity usuarioRecuperado = usuarioRepository.findById(idUser)
                    .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!")); // Recupera usuário
            typeUserSistema.setIdUser(usuarioRecuperado.getIdUsuario());
            typeUserSistema.setUsuario(usuarioRecuperado.getUsuario());
            if(usuarioRecuperado.getRegraEntity().getIdRegra() == 1) { // Caso for admin (regra 1), não há mais dados para incrementar nessa variável.
                return typeUserSistema;
            }

            GerenteEntity gerenteEntity = gerenteRepository.findById(usuarioRecuperado.getGerenteEntity().getIdGerente()) // caso for um gerente, precisa salvar serviços
                    .orElseThrow(() ->new RegraDeNegocioException("Gerente não encontrado!"));

            List<ServicoDTO> listServicosDTO = gerenteEntity.getServicos().stream()
                    .map(servicoEntity -> servicoService.fromEntity(servicoEntity))
                    .collect(Collectors.toList());
                     // Filtra apenas serviços ativos daquele gerente

            typeUserSistema.setIdGerente(gerenteEntity.getIdGerente());
            typeUserSistema.setEmail(gerenteEntity.getEmail());
            typeUserSistema.setNomeCompleto(gerenteEntity.getNomeCompleto());
            typeUserSistema.setServicoDTOList(listServicosDTO);
            typeUserSistema.setStatus(gerenteEntity.getStatus());

            return typeUserSistema;

        } catch (NumberFormatException ex) {
            throw new RegraDeNegocioException("Usuário ou senha inválidos");
        }
    }

    public TypeDTO alterarSenhaELoginUsuarioDoAutenticado(String idUser, LoginCreateDTO loginDTO) throws RegraDeNegocioException {

        try{
            Integer idUsuario = Integer.valueOf(idUser); // Transforma a string que contém o ID do usuário em inteiro
            TypeDTO typeUserSistema = new TypeDTO();
            UsuarioEntity usuarioRecuperado = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!")); // Recupera usuário

            usuarioRecuperado.setUsuario(loginDTO.getUsuario());
            usuarioRecuperado.setSenha(new BCryptPasswordEncoder().encode(loginDTO.getSenha()));
            UsuarioEntity novosDadosUsuario = usuarioRepository.save(usuarioRecuperado);

            typeUserSistema.setIdUser(novosDadosUsuario.getIdUsuario());
            typeUserSistema.setUsuario(novosDadosUsuario.getUsuario());

            if(usuarioRecuperado.getRegraEntity().getIdRegra() == 1) { // Caso for gerente, não há mais dados para incrementar nessa variável.
                return typeUserSistema;
            }

            GerenteEntity gerenteEntity = gerenteRepository.findById(usuarioRecuperado.getGerenteEntity().getIdGerente()) // caso for um gerente, precisa salvar serviços
                    .orElseThrow(() ->new RegraDeNegocioException("Gerente não encontrado!"));

            List<ServicoDTO> listServicosDTO = gerenteEntity.getServicos().stream()
                    .map(servicoEntity -> servicoService.fromEntity(servicoEntity))
                    .filter(servicoDTO -> servicoDTO.getStatus().equals(TipoStatus.ATIVO)).collect(Collectors.toList()); // Filtra apenas serviços ativos daquele gerente

            typeUserSistema.setIdGerente(gerenteEntity.getIdGerente());
            typeUserSistema.setEmail(gerenteEntity.getEmail());
            typeUserSistema.setNomeCompleto(gerenteEntity.getNomeCompleto());
            typeUserSistema.setServicoDTOList(listServicosDTO);
            typeUserSistema.setStatus(gerenteEntity.getStatus());

            return typeUserSistema;

        } catch (NumberFormatException | RegraDeNegocioException ex) {
            throw new RegraDeNegocioException("Usuário ou senha inválidos");
        }
    }
}
