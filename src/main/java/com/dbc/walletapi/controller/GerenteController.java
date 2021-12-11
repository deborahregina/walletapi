package com.dbc.walletapi.controller;

import com.dbc.walletapi.dto.GerenteCreateDTO;
import com.dbc.walletapi.dto.GerenteDTO;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.service.GerenteService;
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
@RequestMapping("/gerente")
@Validated
@RequiredArgsConstructor
@Slf4j
public class GerenteController {

    private final GerenteService gerenteService;

    @PostMapping("/CreateGerentes")
    @ApiOperation(value = "Cria novo gerente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "gerente criado com sucesso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    public GerenteDTO create(@RequestBody @Valid GerenteCreateDTO gerenteCreateDTO) throws RegraDeNegocioException {
        return gerenteService.create(gerenteCreateDTO);
    }

    @GetMapping("/getGerentes")
    @ApiOperation(value = "Lista todos os gerentes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "gerentes listados com sucesso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    public List<GerenteDTO> list() throws RegraDeNegocioException {
        return gerenteService.list();
    }

    @GetMapping("/{idGerente}")
    @ApiOperation(value = "Lista gerente por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "gerente listado com sucesso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema"),
            @ApiResponse(code = 400, message = "Gerente não encontrado")
    })
    public GerenteDTO listById(@PathVariable("idGerente") Integer idGerente) throws RegraDeNegocioException {
        return gerenteService.listById(idGerente);
    }

    @PutMapping("/idGerente")
    @ApiOperation(value = "Altera Gerente pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "gerente alterado com sucesso"),
            @ApiResponse(code = 400, message = "gerente não foi encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    public GerenteDTO update(@RequestParam("idGerente") Integer idGerente, @RequestBody @Valid GerenteCreateDTO gerenteCreateDTO) throws RegraDeNegocioException {
        return gerenteService.update(idGerente,gerenteCreateDTO);
    }

    @DeleteMapping("/{idGerente}")
    @ApiOperation(value = "Exclui o produto pelo id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Produtos listados com sucesso"),
            @ApiResponse(code = 400, message = "Produto não encontrado"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção no sistema")
    })
    public void delete(@PathVariable("idGerente") Integer idGerente) throws Exception {
        gerenteService.delete(idGerente);
    }
}
