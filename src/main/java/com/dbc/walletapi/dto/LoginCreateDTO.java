package com.dbc.walletapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class LoginCreateDTO {

    @NotNull(message = "Usuario não pode ser nulo")
    @NotEmpty(message = "Usuario não pode ser vazio")
    @ApiModelProperty(value = "Login (Usuário)")
    @Pattern(regexp = "^[a-z0-9_-]{3,15}$",
            message = "Usuário deve conter de 3-15 caracteres minúsculos, sem caracteres especiais.")
    private String usuario;

    @NotNull(message = "Senha não pode ser nulo")
    @NotEmpty(message = "Senha não pode ser vazio")
    @ApiModelProperty(value = "Senha")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$"
            , message = "Senha deve conter no mínimo oito caracteres, pelo menos uma letra maiúscula" +
            ", uma letra minúscula, um número e um caractere especial")
    private String senha;
}
