package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.ServicoEntity;
import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.ServicoRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.reflect.ReflectionWorld;
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
        return servicoDTO;
    }

    public ServicoDTO update(ServicoCreateDTO servicoAtualizaDTO, Integer idServico) throws RegraDeNegocioException{
        ServicoEntity servicoParaAtualizar = findById(idServico);

        servicoParaAtualizar.setDescricao(servicoAtualizaDTO.getDescricao()); // atualiza descricao

        servicoParaAtualizar.setMoeda(servicoAtualizaDTO.getMoeda()); // atualiza moeda
        servicoParaAtualizar.setValor(servicoAtualizaDTO.getValor()); // atualiza valor
        servicoParaAtualizar.setNome(servicoAtualizaDTO.getNome()); // atualiza nome
        servicoParaAtualizar.setPeriocidade(servicoAtualizaDTO.getPeriocidade()); // atualiza periodicidade
        servicoParaAtualizar.setWebSite(servicoAtualizaDTO.getWebSite()); // atualiza website

        ServicoEntity servicoEditado = servicoRepository.save(servicoParaAtualizar);
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
        servicoEntity.setStatus(TipoStatus.INATIVO);
        servicoRepository.save(servicoEntity);
    }

    public ServicoEntity findById(Integer id) throws RegraDeNegocioException {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Serviço não encontrado"));
    }

    public ServicoDTO listById(Integer idServico) throws RegraDeNegocioException {
        servicoRepository.findById(idServico).orElseThrow(() -> new RegraDeNegocioException("Serviço não encontrado!"));
        ServicoEntity servicoEntity = servicoRepository.getServicoById(idServico);
        return objectMapper.convertValue(servicoEntity, ServicoDTO.class);

    }




    public List<ServicoDTO> listByName(String nome) {
        return servicoRepository.findAll()
                .stream()
                .filter(servico -> servico.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList()).stream()
                .map(servico -> objectMapper.convertValue(servico, ServicoDTO.class))
                .collect(Collectors.toList());
    }

}