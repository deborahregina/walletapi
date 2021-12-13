package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoStatus;
import com.dbc.walletapi.entity.UsuarioEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
