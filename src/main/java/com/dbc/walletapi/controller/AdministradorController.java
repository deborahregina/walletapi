package com.dbc.walletapi.controller;


import com.dbc.walletapi.dto.*;
import com.dbc.walletapi.entity.UsuarioEntity;
import com.dbc.walletapi.exceptions.CustomGlobalExceptionHandler;
import com.dbc.walletapi.exceptions.RegraDeNegocioException;
import com.dbc.walletapi.service.GerenteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.dbc.walletapi.security.TokenService;
import com.dbc.walletapi.service.UsuarioService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AdministradorController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final GerenteService gerenteService;

    @PostMapping
    @ApiOperation(value = "Autenticação")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Autenticação feita com sucesso"),
            @ApiResponse(code = 400, message = "Dados incorretos"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public String auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsuario(),
                        loginDTO.getSenha()
                );

        try {
            Authentication authenticate = authenticationManager.authenticate(user);

            String token = tokenService.generateToken((UsuarioEntity) authenticate.getPrincipal());
            return token;
        } catch (BadCredentialsException ex) {
            throw new RegraDeNegocioException("Usuário ou senha inválidos");
        }

    }


    @PostMapping("/create-gerente")
    @ApiOperation(value = "Criar gerente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Criação feita com sucesso"),
            @ApiResponse(code = 400, message = "Dados inconsistentes ou faltantes"),
            @ApiResponse(code = 403, message = "Você não tem permissao para acessar esse recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma excessão"),
    })
    public GerenteDTO postGerente(@RequestBody GerenteCreateDTO gerenteCreateDTO) throws RegraDeNegocioException {

            return gerenteService.create(gerenteCreateDTO);

    }

}