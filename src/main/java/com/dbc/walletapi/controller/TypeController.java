package com.dbc.walletapi.controller;

import com.dbc.walletapi.dto.ServicoDTO;
import com.dbc.walletapi.dto.TypeDTO;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.service.TypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/type")
@Validated
@RequiredArgsConstructor
@Slf4j
public class TypeController {

    private final TypeService typeService;

    @GetMapping("/{username}")
    @ApiOperation(value = "Devolve user com infos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem feita com sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public TypeDTO listServico(@PathVariable String username) throws RegraDeNegocioException {
        return typeService.list(username);
    }

}
