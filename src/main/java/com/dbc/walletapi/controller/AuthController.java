package com.dbc.walletapi.controller;


import com.dbc.walletapi.dto.LoginDTO;
import com.dbc.walletapi.dto.UsuarioCreateDTO;
import com.dbc.walletapi.dto.UsuarioDTO;
import com.dbc.walletapi.entity.UsuarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dbc.walletapi.security.TokenService;
import com.dbc.walletapi.service.UsuarioService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) {
        UsernamePasswordAuthenticationToken user =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsuario(),
                        loginDTO.getSenha()
                );

        Authentication authenticate = authenticationManager.authenticate(user);

        String token = tokenService.generateToken((UsuarioEntity) authenticate.getPrincipal());
        return token;
    }

    @PostMapping("/create")
    public UsuarioDTO postUsuario(@RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        return usuarioService.create(usuarioCreateDTO);
    }
}