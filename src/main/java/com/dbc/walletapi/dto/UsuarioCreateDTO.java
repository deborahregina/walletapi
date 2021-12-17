package com.dbc.walletapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {

    @NotNull(message = "Usuario não pode ser nulo")
    @NotEmpty(message = "Usuario não pode ser vazio")
    @ApiModelProperty(value = "Usuário")
    @Pattern(regexp = "^[a-z0-9_-]{3,15}$", message = "Usuário deve ter de 3-15 caracteres, letras minúsculas e não conter espaços")
    private String usuario;

    @NotNull(message = "Senha não pode ser nulo")
    @NotEmpty(message = "Senha não pode ser vazio")
    @ApiModelProperty(value = "Senha")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$", message = "Deve conter no mínimo oito caracteres, pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial")
    private String senha;

    @JsonIgnore
    @ApiModelProperty(value = "Regra: ADM/GERENTE")
    private Integer regra;
}
