package com.dbc.walletapi.controller;

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
            @ApiResponse(code = 200, message = "Criação feita com sucesso"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou faltantes"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public ServicoDTO createServico(@RequestBody @Valid ServicoCreateDTO servicoCreateDTO,
                                    @RequestParam Integer idGerente) throws RegraDeNegocioException {
        return servicoService.create(servicoCreateDTO, idGerente);
    }

    @PostMapping("/edit-servico")
    @ApiOperation(value = "Editar serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Edição feita com sucesso"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou faltantes"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public ServicoDTO updateServico(@RequestBody @Valid ServicoCreateDTO servicoCreateDTO,
                                    @RequestParam Integer idServico) throws RegraDeNegocioException {
        return servicoService.update(servicoCreateDTO, idServico);
    }

    @PostMapping("/list-servico")
    @ApiOperation(value = "Listar serviços")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem feita com sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public List<ServicoDTO> listServico(){
        return servicoService.list();
    }

    @PostMapping("/delete-servico")
    @ApiOperation(value = "Excluir serviço")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Exclusão feita com sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public void deleteServico(@RequestParam Integer idServico) throws RegraDeNegocioException {
        servicoService.delete(idServico);
    }



}
