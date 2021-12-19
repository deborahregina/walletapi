package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.ServicoEntity;
import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.ServicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicoService<ServicosDTO> {

    private final ObjectMapper objectMapper;
    private final ServicoRepository servicoRepository;
    private final GerenteRepository gerenteRepository;

    public ServicoDTO create(ServicoCreateDTO servicoCreateDTO, Integer idGerente) throws RegraDeNegocioException {
        GerenteEntity gerenteEntity = gerenteRepository.findById(idGerente).orElseThrow
                (() -> new RegraDeNegocioException("Gerente não encontrado!"));

        if(gerenteEntity.getStatus() == TipoStatus.INATIVO) {
            throw new RegraDeNegocioException("Serviço deve ser atribuído para gerente ativo"); // Evitar atribuir gerente inativo para servico novo
        }

        ServicoEntity novoServico = objectMapper.convertValue(servicoCreateDTO, ServicoEntity.class);
        novoServico.setGerenteEntity(gerenteEntity);
        novoServico.setStatus(TipoStatus.ATIVO);

        ServicoEntity servicoSalvo = servicoRepository.save(novoServico);
        return fromEntity(servicoSalvo);
    }

    public ServicoDTO update(ServicoAtualizaDTO servicoAtualizaDTO, Integer idServico) throws RegraDeNegocioException{
        ServicoEntity servicoParaAtualizar = findById(idServico);

        if(servicoAtualizaDTO.getIdGerente() != null) {
            GerenteEntity gerente = gerenteRepository.findById(servicoAtualizaDTO.getIdGerente()) // Confere se o gerente existe
                    .orElseThrow(() -> new RegraDeNegocioException("Gerente não encontrado!"));

            if (gerente.getStatus() == TipoStatus.INATIVO) { // Confere se o gerente está ativo
                throw new RegraDeNegocioException("Não é possível atribuir serviço para gerente inativo!");
            }

            servicoParaAtualizar.setGerenteEntity(gerente);
        }

        servicoParaAtualizar.setDescricao(servicoAtualizaDTO.getDescricao()); // atualiza descricao
        servicoParaAtualizar.setMoeda(servicoAtualizaDTO.getMoeda()); // atualiza moeda
        servicoParaAtualizar.setValor(servicoAtualizaDTO.getValor()); // atualiza valor
        servicoParaAtualizar.setNome(servicoAtualizaDTO.getNome()); // atualiza nome
        servicoParaAtualizar.setPeriocidade(servicoAtualizaDTO.getPeriocidade()); // atualiza periodicidade
        servicoParaAtualizar.setWebSite(servicoAtualizaDTO.getWebSite()); // atualiza website
        servicoParaAtualizar.setStatus(TipoStatus.ATIVO);

        ServicoEntity servicoEditado = servicoRepository.save(servicoParaAtualizar);

        return fromEntity(servicoEditado);
    }

    public List<ServicoDTO> list() {
        return servicoRepository.getServicosAtivos().
                stream()
                .map(servico -> fromEntity(servico))
                .collect(Collectors.toList());
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        ServicoEntity servicoEntity = findById(id);
        servicoEntity.setStatus(TipoStatus.INATIVO);
        servicoRepository.save(servicoEntity);
    }

    private ServicoEntity findById(Integer id) throws RegraDeNegocioException {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Serviço não encontrado"));
    }

    public ServicoDTO listById(Integer idServico) throws RegraDeNegocioException {

        ServicoEntity servicoEntity = servicoRepository.findById(idServico) // Lista serviço ativo ou inativo por ID
                .orElseThrow(() -> new RegraDeNegocioException("Serviço não encontrado!"));
        return fromEntity(servicoEntity);
    }


    public List<ServicoDTO> listByName(String nome) { // Lista serviço ativo ou inativo por nome, ignorando case.
        return servicoRepository.findAll()
                .stream()
                .filter(servico -> servico.getNome().toLowerCase().contains(nome.toLowerCase()))
                .collect(Collectors.toList()).stream()
                .map(servico -> fromEntity(servico))
                .collect(Collectors.toList());
    }

    public ServicoDTO fromEntity(ServicoEntity servicoEntity) {

        ServicoDTO servicoDTO = objectMapper.convertValue(servicoEntity, ServicoDTO.class);
        GerenteDTO gerente = objectMapper.convertValue(servicoEntity.getGerenteEntity(), GerenteDTO.class);
        UsuarioDTO user = objectMapper.convertValue(servicoEntity.getGerenteEntity().getUsuario(), UsuarioDTO.class);
        servicoDTO.setGerente(gerente);
        servicoDTO.getGerente().setUsuario(user);
        return servicoDTO;
    }


    public boolean ServicosInativos(List<ServicoEntity> servicoEntities) { // verifica se uma lista de serviços é inativa.

        for(ServicoEntity servico: servicoEntities) {
            if (servico.getStatus() == TipoStatus.ATIVO) {
                return false;
            }
        }
        return true;
    }

    public List<ServicoDTO> listaServicoPorIdGerente(Integer idGerente) throws RegraDeNegocioException {

        GerenteEntity gerente = gerenteRepository.findById(idGerente)
                .orElseThrow(() -> new RegraDeNegocioException("Gerente não encontrado!"));
        List<ServicoEntity> servicos = servicoRepository.getServicosAtivosIdGerente(gerente.getIdGerente());

        return servicos.stream().map(servicoEntity -> objectMapper.convertValue(servicoEntity, ServicoDTO.class))
                .collect(Collectors.toList());

    }


}