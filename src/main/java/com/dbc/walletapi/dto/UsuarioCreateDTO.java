package com.dbc.walletapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCreateDTO {
    private String usuario;
    private String senha;
    private Integer regra;
}
