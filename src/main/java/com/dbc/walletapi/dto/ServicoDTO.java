package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoMoeda;
import com.dbc.walletapi.entity.TipoStatus;

import com.dbc.walletapi.entity.GerenteEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoDTO {


    private Integer idServico;
    private String nome;
    private String descricao;
    private String webSite;
    private BigDecimal valor;
    private TipoMoeda moeda;
    private Integer periocidade;
    private TipoStatus status;
    private GerenteDTO gerente;


}
