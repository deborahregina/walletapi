package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoMoeda;
import com.dbc.walletapi.entity.TipoPeriodicidade;
import com.dbc.walletapi.entity.TipoStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // Esta anotação foi inserida para testes no mockito.
public class ServicoDTO {

    private Integer idServico;

    @ApiModelProperty(value = "Nome do serviço")
    private String nome;

    @ApiModelProperty(value = "Descrição do serviço")
    private String descricao;

    @ApiModelProperty(value = "Website do serviço")
    private String webSite;

    @ApiModelProperty(value = "Valor do serviço em decimal, separado por .")
    private BigDecimal valor;

    @ApiModelProperty(value = "Nome da moeda em que se contrata o serviço: DOLAR:0, REAL:1, EURO:2")
    private TipoMoeda moeda;

    @ApiModelProperty(value = "Periodicidade: MENSAL:0, TRIMESTRAL:1, SEMESTRAL:2, ANUAL:3")
    private TipoPeriodicidade periocidade;

    @ApiModelProperty(value = "Status do serviço: ATIVO/INATIVO")
    private TipoStatus status;

    @ApiModelProperty(value = "Gerente responsável pelo serviço")
    private GerenteDTO gerente;

    @ApiModelProperty(value = "Data de criação do serviço")
    private LocalDate data;

    private BigDecimal valorOriginal;

    private LocalDate dataDeletado;

}
