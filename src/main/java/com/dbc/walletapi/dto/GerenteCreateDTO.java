package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class GerenteCreateDTO {

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    @ApiModelProperty(value = "Nome completo do Gerente")
    @Pattern(regexp = "^([A-z\\'\\.-ᶜ]*(\\s))+[A-z\\'\\.-ᶜ]*$", message = "Digite um nome no formato: Nome Sobrenome")
    private String nomeCompleto;


    @NotNull(message = "Email não pode ser nulo")
    @NotEmpty(message = "Email não pode ser vazio")
    @ApiModelProperty(value = "E-mail do Gerente")
    @Email(message = "Usar formato de email válido")
    private String email;

    @Valid
    @ApiModelProperty(value = "Usuário e senha do gerente")
    private UsuarioCreateDTO usuario;

    @JsonIgnore
    private TipoStatus status;

}
