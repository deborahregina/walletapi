package com.dbc.walletapi.service;

import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.*;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.repository.GerenteRepository;
import com.dbc.walletapi.repository.ServicoRepository;
import com.dbc.walletapi.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicoService<ServicosDTO> {

    private final ObjectMapper objectMapper;
    private final ServicoRepository servicoRepository;
    private final GerenteRepository gerenteRepository;
    private final UsuarioRepository usuarioRepository;

    public ServicoDTO create(ServicoCreateDTO servicoCreateDTO, Integer idGerente) throws RegraDeNegocioException {
        GerenteEntity gerenteEntity = gerenteRepository.findById(idGerente).orElseThrow
                (() -> new RegraDeNegocioException("Gerente não encontrado!"));

        if (gerenteEntity.getStatus() == TipoStatus.INATIVO) {
            throw new RegraDeNegocioException("Serviço deve ser atribuído para gerente ativo"); // Evitar atribuir gerente inativo para servico novo
        }

        ServicoEntity novoServico = objectMapper.convertValue(servicoCreateDTO, ServicoEntity.class);
        novoServico.setGerenteEntity(gerenteEntity);
        novoServico.setStatus(TipoStatus.ATIVO);

        ServicoEntity servicoSalvo = servicoRepository.save(novoServico);
        return fromEntity(servicoSalvo);
    }

    public ServicoDTO update(ServicoAtualizaDTO servicoAtualizaDTO, Integer idServico) throws RegraDeNegocioException {
        ServicoEntity servicoParaAtualizar = findById(idServico);

        if (servicoAtualizaDTO.getIdGerente() != null) {
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
        servicoParaAtualizar.setValorOriginal(servicoAtualizaDTO.getValorOriginal());
        servicoParaAtualizar.setStatus(TipoStatus.ATIVO);

        ServicoEntity servicoEditado = servicoRepository.save(servicoParaAtualizar);

        return fromEntity(servicoEditado);
    }

    public List<ServicoDTO> list() {
        return servicoRepository.findAll().stream()
                .map(servicoEntity -> fromEntity(servicoEntity)).collect(Collectors.toList());
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        ServicoEntity servicoEntity = findById(id);
        servicoEntity.setStatus(TipoStatus.INATIVO);
        servicoEntity.setDataDeletado(LocalDate.now());
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
                .map(this::fromEntity)
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


    public boolean servicosInativos(List<ServicoEntity> servicoEntities) { // verifica se uma lista de serviços é inativa.

        for (ServicoEntity servico : servicoEntities) {
            if (servico.getStatus() == TipoStatus.ATIVO) {
                return false;
            }
        }
        return true;
    }

    public List<ServicoDTO> listaServicoPorIdGerente(Integer idGerente) throws RegraDeNegocioException {

        GerenteEntity gerente = gerenteRepository.findById(idGerente)
                .orElseThrow(() -> new RegraDeNegocioException("Gerente não encontrado!"));
        if (gerente.getStatus() == TipoStatus.INATIVO) {
            throw new RegraDeNegocioException("Este gerente está inativo!");
        }

        return gerente.getServicos().stream().map(servicoEntity -> fromEntity(servicoEntity))
                .collect(Collectors.toList());


    }

    public BigDecimal listByMesEAno(Integer ano, Integer mes, String idUser) throws RegraDeNegocioException {

        try {
            Integer idUsuario = Integer.valueOf(idUser); // Transforma a string que contém o ID do usuário em inteiro

            UsuarioEntity usuarioRecuperado = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!")); // Recupera usuário

            if (usuarioRecuperado.getRegraEntity().getIdRegra() == 1) { // caso usuário for o admin
                return calculaValorMensal(servicoRepository.getServicosPorMesEAnoAtivosInativos(ano, mes))
                        .subtract(calculaValorMensal(servicoRepository.getServicosPorMesEAnoInativos(ano,mes)));

            }
            GerenteEntity gerenteEntity = gerenteRepository.findById(usuarioRecuperado.getGerenteEntity().getIdGerente()) // caso for um gerente, precisa salvar serviços
                    .orElseThrow(() ->new RegraDeNegocioException("Gerente não encontrado!"));

            return calculaValorMensal(servicoRepository.getServicosPorMesEAnoEIDGerenteAtivoEInativo(ano,mes,gerenteEntity.getIdGerente()))
                    .subtract(calculaValorMensal(servicoRepository.getServicosPorMesEAnoEIDGerenteInativo(ano,mes,gerenteEntity.getIdGerente())));

        } catch (NumberFormatException ex) {
            throw new RegraDeNegocioException("Usuário ou senha inválidos");
        }
    }

    public BigDecimal calculaValorMensal(List<ServicoEntity> servicoEntities) {

        BigDecimal somaTotal = BigDecimal.ZERO;
        for(ServicoEntity servico :  servicoEntities) {
            if (servico.getPeriocidade() == TipoPeriodicidade.TRIMESTRAL) {
                somaTotal = somaTotal.add(servico.getValor().divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_UP));
            }
            if(servico.getPeriocidade() == TipoPeriodicidade.SEMESTRAL) {
                somaTotal = somaTotal.add(servico.getValor().divide(BigDecimal.valueOf(6),2, RoundingMode.HALF_UP));
            }
            if(servico.getPeriocidade() == TipoPeriodicidade.ANUAL) {
                somaTotal = somaTotal.add(servico.getValor().divide(BigDecimal.valueOf(12),2, RoundingMode.HALF_UP));
            } if(servico.getPeriocidade() == TipoPeriodicidade.MENSAL) {
                somaTotal = somaTotal.add(servico.getValor());
            }
        }
        return somaTotal;
    }

    public BigDecimal getValorOriginal(String idUser, Integer tipoMoeda) throws RegraDeNegocioException {

        try {
            Integer idUsuario = Integer.valueOf(idUser);

            UsuarioEntity usuarioRecuperado = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado!"));

            if (usuarioRecuperado.getRegraEntity().getIdRegra() == 1) { // caso usuário for o admin
               List<ServicoEntity> listaServicos = servicoRepository.getServicosAtivosPorMoeda(tipoMoeda);
               return calculaValorOriginal(listaServicos);

            }
            GerenteEntity gerenteEntity = gerenteRepository.findById(usuarioRecuperado.getGerenteEntity().getIdGerente())
                    .orElseThrow(() ->new RegraDeNegocioException("Gerente não encontrado!"));

            List<ServicoEntity> listaServicos = servicoRepository.getServicosAtivosPorMoedaIdGerente(tipoMoeda,gerenteEntity.getIdGerente());
            return calculaValorOriginal(listaServicos);

        } catch (NumberFormatException ex) {
            throw new RegraDeNegocioException("Usuário ou senha inválidos");
        }
    }



    public BigDecimal calculaValorOriginal(List<ServicoEntity> servicoEntities) {

        BigDecimal somaTotal = BigDecimal.ZERO;
        for(ServicoEntity servico :  servicoEntities) {
            if (servico.getPeriocidade() == TipoPeriodicidade.TRIMESTRAL) {
                somaTotal = somaTotal.add(servico.getValorOriginal().divide(BigDecimal.valueOf(3),2, RoundingMode.HALF_UP));
            }
            if(servico.getPeriocidade() == TipoPeriodicidade.SEMESTRAL) {
                somaTotal = somaTotal.add(servico.getValorOriginal().divide(BigDecimal.valueOf(6),2, RoundingMode.HALF_UP));
            }
            if(servico.getPeriocidade() == TipoPeriodicidade.ANUAL) {
                somaTotal = somaTotal.add(servico.getValorOriginal().divide(BigDecimal.valueOf(12),2, RoundingMode.HALF_UP));
            } if(servico.getPeriocidade() == TipoPeriodicidade.MENSAL) {
                somaTotal = somaTotal.add(servico.getValorOriginal());
            }
        }
        return somaTotal;
    }
}