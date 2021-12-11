package com.dbc.walletapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {
    @NotNull
    private String usuario;
    @NotNull
    private String senha;
    @JsonIgnore
    private Integer regra;
}
