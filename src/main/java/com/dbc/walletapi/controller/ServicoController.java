package com.dbc.walletapi.controller;

import com.dbc.walletapi.dto.GerenteDTO;
import com.dbc.walletapi.dto.ServicoAtualizaDTO;
import com.dbc.walletapi.dto.ServicoCreateDTO;
import com.dbc.walletapi.dto.ServicoDTO;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.service.ServicoService;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/create-servico")
    @ApiOperation(value = "Criar serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Serviço criado com sucesso"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou faltantes"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public ServicoDTO createServico(@RequestBody @Valid ServicoCreateDTO servicoCreateDTO,
                                    @RequestParam Integer idGerente) throws RegraDeNegocioException {
        return servicoService.create(servicoCreateDTO, idGerente);
    }

    @PutMapping("/edit-servico")
    @ApiOperation(value = "Editar serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Serviço editado com sucesso"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou faltantes"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public ServicoDTO updateServico(@RequestBody @Valid ServicoAtualizaDTO servicoAtualizaDTO,
                                    @RequestParam Integer idServico) throws RegraDeNegocioException {
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

    @DeleteMapping("/trocar-status")
    @ApiOperation(value = "Trocar status do serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Troca de status feita com sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public void trocaStatus(@RequestParam Integer idServico) throws RegraDeNegocioException {
        servicoService.trocaStatus(idServico);
    }

    @GetMapping("/{idServico}")
    @ApiOperation(value = "Lista servico por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gerente listado com sucesso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema"),
            @ApiResponse(code = 400, message = "Gerente não encontrado")
    })
    public ServicoDTO listById (@PathVariable("idServico") Integer idServico) throws RegraDeNegocioException {
        return servicoService.listById(idServico);
    }

    @GetMapping("/listar-por-nome")
    @ApiOperation(value = "Lista serviços pelo nome")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Serviços listados com sucesso"),
            @ApiResponse(code = 400, message = "Listagem não encontrada"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    public List<ServicoDTO> listarPorNome(@RequestParam("nome") String nome){
        return servicoService.listByName(nome);
    }

}
