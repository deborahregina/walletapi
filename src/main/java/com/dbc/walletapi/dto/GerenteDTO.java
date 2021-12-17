package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class GerenteDTO {

    @ApiModelProperty(value = "ID do Gerente")
    private Integer idGerente;

    @ApiModelProperty(value = "Nome completo do Gerente")
    private String nomeCompleto;

    @ApiModelProperty(value = "E-mail do Gerente")
    private String email;

    @ApiModelProperty(value = "Usuário e senha do Gerente")
    private UsuarioDTO usuario;

    @ApiModelProperty(value = "Status do Gerente")
    private TipoStatus status;

}
