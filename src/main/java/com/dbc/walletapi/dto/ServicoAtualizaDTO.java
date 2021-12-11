package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoMoeda;
import com.dbc.walletapi.entity.TipoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoAtualizaDTO {

    @NotNull
    private String nome;
    @NotNull
    private String descricao;
    @NotNull
    private String webSite;
    @NotNull
    private BigDecimal valor;
    @NotNull
    private TipoMoeda moeda;
    @NotNull
    private Integer periocidade;
    @NotNull
    private TipoStatus status;
    @NotNull
    private Integer idGerente;


}
