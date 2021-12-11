package com.dbc.walletapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String usuario;
    @NotNull(message = "Senha não pode ser nulo")
    @NotEmpty(message = "Senha não pode ser vazio")
    private String senha;
    @JsonIgnore
    private Integer regra;
}
