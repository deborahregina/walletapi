package com.dbc.walletapi.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginDTO {

    @NotNull(message = "Usuario não pode ser nulo")
    @NotEmpty(message = "Usuario não pode ser vazio")
    private String usuario;

    @NotNull(message = "Senha não pode ser nulo")
    @NotEmpty(message = "Senha não pode ser vazio")
    private String senha;
}
