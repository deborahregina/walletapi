package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoStatus;

import com.dbc.walletapi.entity.GerenteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoDTO {

    @NotNull
    private Integer idServico;
    @NotNull
    private String nome;
    @NotNull
    private String descricao;
    @NotNull
    private String webSite;
    @NotNull
    private Double valor;
    @NotNull
    private Double moeda;
    @NotNull
    private Integer periocidade;

    private TipoStatus status;

    @NotNull
    private GerenteDTO gerente;


}
