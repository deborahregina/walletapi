package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class GerenteCreateDTO {

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nomeCompleto;


    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    @Email(message = "Usar formato de email válido")
    private String email;

    private UsuarioCreateDTO usuario;

    @JsonIgnore
    private TipoStatus status;

}
