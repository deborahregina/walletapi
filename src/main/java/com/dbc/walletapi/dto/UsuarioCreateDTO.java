package com.dbc.walletapi.dto;

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
    private List<Integer> grupos;
}
