package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GerenteAtualizaDTO {

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    @ApiModelProperty(value = "Nome completo do gerente")
    private String nomeCompleto;
    @NotNull(message = "Email não pode ser nulo")
    @NotEmpty(message = "Email não pode ser vazio")
    @ApiModelProperty(value = "E-mail do gerente")
    @Email(message = "Usar formato de email válido")
    private String email;

}
