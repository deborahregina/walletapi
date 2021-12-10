package com.dbc.walletapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    @NotNull
    private Integer idUsuario;
    @NotNull
    private String usuario;
    private Integer regra;
}