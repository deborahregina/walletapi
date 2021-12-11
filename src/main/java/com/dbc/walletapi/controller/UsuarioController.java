package com.dbc.walletapi.controller;

import com.dbc.walletapi.dto.ServicoDTO;
import com.dbc.walletapi.dto.UsuarioDTO;
import com.dbc.walletapi.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@Validated
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/list-usuarios")
    @ApiOperation(value = "Listar usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem feita com sucesso"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public List<UsuarioDTO> listUsuario(){
        return usuarioService.list();
    }
}
