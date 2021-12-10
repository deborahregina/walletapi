package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoStatus;
import lombok.Data;

@Data
public class GerenteCreateDTO {

    private String nomeCompleto;
    private String email;
    private UsuarioCreateDTO usuario;
    private TipoStatus status;

}
