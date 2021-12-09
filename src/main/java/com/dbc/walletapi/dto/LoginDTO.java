package com.dbc.walletapi.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull
    private String usuario;
    @NotNull
    private String senha;
}
