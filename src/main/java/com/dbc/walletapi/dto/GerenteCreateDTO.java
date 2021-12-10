package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoStatus;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class GerenteCreateDTO {

    @NotNull
    private String nomeCompleto;
    @Email
    private String email;
    private UsuarioCreateDTO usuario;
    private TipoStatus status;

}
