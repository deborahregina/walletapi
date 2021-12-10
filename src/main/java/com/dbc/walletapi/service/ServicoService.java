package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.GerenteDTO;
import com.dbc.walletapi.dto.ServicoCreateDTO;
import com.dbc.walletapi.dto.ServicoDTO;
import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.ServicoEntity;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.ServicoRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicoService {

    private final ObjectMapper objectMapper;
    private final ServicoRepository servicoRepository;
    private final GerenteRepository gerenteRepository;

    public ServicoDTO create(ServicoCreateDTO servicoCreateDTO, Integer idGerente) throws RegraDeNegocioException {
        GerenteEntity gerenteEntity = gerenteRepository.findById(idGerente).orElseThrow
                (() -> new RegraDeNegocioException("Gerente não encontrado!"));
        ServicoEntity novoServico = objectMapper.convertValue(servicoCreateDTO, ServicoEntity.class);
        novoServico.setGerenteEntity(gerenteEntity);
        ServicoEntity servicoSalvo = servicoRepository.save(novoServico);
        ServicoDTO servicoDTO = objectMapper.convertValue(servicoSalvo, ServicoDTO.class);
        GerenteDTO gerenteDTO = objectMapper.convertValue(gerenteEntity, GerenteDTO.class);
        servicoDTO.setGerente(gerenteDTO);
        return servicoDTO;
    }

    public ServicoDTO update(ServicoCreateDTO servicoCreateDTO, Integer idServico) throws RegraDeNegocioException{
        findById(idServico);
        ServicoEntity servicoEntity = objectMapper.convertValue(servicoCreateDTO, ServicoEntity.class);
        servicoEntity.setIdServico(idServico);
        ServicoEntity servicoEditado = servicoRepository.save(servicoEntity);
        return objectMapper.convertValue(servicoEditado, ServicoDTO.class);
    }

    public List<ServicoDTO> list() {
        return servicoRepository.findAll().
                stream()
                .map(servico -> objectMapper.convertValue(servico, ServicoDTO.class))
                .collect(Collectors.toList());
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        ServicoEntity servicoEntity = findById(id);
        servicoRepository.delete(servicoEntity);
    }

    public ServicoEntity findById(Integer id) throws RegraDeNegocioException {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Serviço não encontrado"));
    }


}
