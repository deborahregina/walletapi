package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GerenteCreateDTO {

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    @ApiModelProperty(value = "Nome completo do Gerente")
    private String nomeCompleto;


    @NotNull(message = "Email não pode ser nulo")
    @NotEmpty(message = "Email não pode ser vazio")
    @Email(message = "Usar formato de email válido")
    @ApiModelProperty(value = "E-mail do Gerente")
    private String email;

    @ApiModelProperty(value = "Usuário e senha do gerente")
    private UsuarioCreateDTO usuario;

    @JsonIgnore
    private TipoStatus status;

}
