package com.dbc.walletapi.controller;

import com.dbc.walletapi.dto.ServicoAtualizaDTO;
import com.dbc.walletapi.dto.ServicoCreateDTO;
import com.dbc.walletapi.dto.ServicoDTO;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.service.ServicoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/servico")
@Validated
@RequiredArgsConstructor
@Slf4j
public class ServicoController {
    private final ServicoService servicoService;

    @PostMapping("/create-servico/{idGerente}")
    @ApiOperation(value = "Criar serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Serviço criado com sucesso"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou faltantes"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public ServicoDTO createServico(
            @ApiParam(name = "Servico e ID gerente",value = "Endpoint para criar conta para algum gerente")
            @RequestBody @Valid ServicoCreateDTO servicoCreateDTO,
            @PathVariable Integer idGerente) throws RegraDeNegocioException {
        return servicoService.create(servicoCreateDTO, idGerente);
    }

    @PutMapping("/edit-servico/{idServico}")
    @ApiOperation(value = "Editar serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Serviço editado com sucesso"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou faltantes"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public ServicoDTO updateServico(
            @ApiParam(name = "Servico novo e ID",value = "Endpoint para alterar conta existente")
            @RequestBody @Valid ServicoAtualizaDTO servicoAtualizaDTO,
            @PathVariable Integer idServico) throws RegraDeNegocioException {
        return servicoService.update(servicoAtualizaDTO, idServico);
    }

    @GetMapping("/list-servico")
    @ApiOperation(value = "Listar serviços")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Serviços listados com sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public List<ServicoDTO> listServico(){
        return servicoService.list();
    }

    @DeleteMapping("/delete-servico/{idServico}")
    @ApiOperation(value = "Deleta Serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Serviço deletado com sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public void delete(
            @PathVariable Integer idServico) throws RegraDeNegocioException {
        servicoService.delete(idServico);
    }

    @GetMapping("/{idServico}")
    @ApiOperation(value = "Lista servico por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gerente listado com sucesso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema"),
            @ApiResponse(code = 400, message = "Gerente não encontrado")
    })
    public ServicoDTO listById (
            @ApiParam(name = "ID do serviço",value = "Endpoint para listar conta existente")
            @PathVariable("idServico") Integer idServico) throws RegraDeNegocioException {
        return servicoService.listById(idServico);
    }

    @GetMapping("/listar-por-nome")
    @ApiOperation(value = "Lista serviços pelo nome")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Serviços listados com sucesso"),
            @ApiResponse(code = 400, message = "Listagem não encontrada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    public List<ServicoDTO> listarPorNome(
            @ApiParam(name = "Nome",value = "Endpoint para listar contas por parte do nome")
            @RequestParam("nome") String nome){
        return servicoService.listByName(nome);
    }

}