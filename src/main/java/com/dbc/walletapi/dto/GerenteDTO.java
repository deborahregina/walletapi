package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.entity.UsuarioEntity;
import lombok.Data;

@Data
public class GerenteDTO {

    private Integer idGerente;
    private String nomeCompleto;
    private String email;
    private UsuarioDTO usuario;
    private TipoStatus status;

}
