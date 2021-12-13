package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.GerenteEntity;
import com.dbc.walletapi.entity.TipoMoeda;
import com.dbc.walletapi.entity.TipoPeriodicidade;
import com.dbc.walletapi.entity.TipoStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoCreateDTO {

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;
    @NotNull(message = "Descricao não pode ser nulo")
    @NotEmpty(message = "Descricao não pode ser vazio")
    private String descricao;
    @NotNull(message = "Website não pode ser nulo")
    @NotEmpty(message = "Website não pode ser vazio")
    private String webSite;
    @NotNull
    private BigDecimal valor;
    @NotNull
    private TipoMoeda moeda;
    @NotNull
    private TipoPeriodicidade periocidade;
    @NotNull
    private TipoStatus status;

}
