package com.dbc.walletapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    @NotNull
    @ApiModelProperty(value = "ID do usuário")
    private Integer idUsuario;
    @NotNull
    @ApiModelProperty(value = "usuário")
    private String usuario;
    @JsonIgnore
    @ApiModelProperty(value = "senha")
    private Integer regra;

}