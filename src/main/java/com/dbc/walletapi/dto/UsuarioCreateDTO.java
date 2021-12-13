package com.dbc.walletapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {

    @NotNull(message = "Usuario não pode ser nulo")
    @NotEmpty(message = "Usuario não pode ser vazio")
    @ApiModelProperty(value = "Usuário")
    private String usuario;
    @NotNull(message = "Senha não pode ser nulo")
    @NotEmpty(message = "Senha não pode ser vazio")
    @ApiModelProperty(value = "Senha")
    private String senha;
    @JsonIgnore
    @ApiModelProperty(value = "Regra: ADM/GERENTE")
    private Integer regra;
}
