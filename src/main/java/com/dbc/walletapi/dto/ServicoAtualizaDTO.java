package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoMoeda;
import com.dbc.walletapi.entity.TipoPeriodicidade;
import com.dbc.walletapi.entity.TipoStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoAtualizaDTO {

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    @ApiModelProperty(value = "Nome do serviço")
    private String nome;


    @NotNull(message = "Descricao não pode ser nulo")
    @NotEmpty(message = "Descricao não pode ser vazio")
    @ApiModelProperty(value = "Descrição do serviço")
    private String descricao;


    @NotNull(message = "Website não pode ser nulo")
    @NotEmpty(message = "Website não pode ser vazio")
    @ApiModelProperty(value = "Website do serviço")
    @Pattern(regexp = "^((ftp|http|https):\\/\\/)?(www.)?(?!.*(ftp|http|https|www.))[a-zA-Z0-9_-]+(\\.[a-zA-Z]+)+((\\/)[\\w#]+)*(\\/\\w+\\?[a-zA-Z0-9_]+=\\w+(&[a-zA-Z0-9_]+=\\w+)*)?$", message = "Digite um formato de URL válido.")
    private String webSite;


    @NotNull
    @ApiModelProperty(value = "Valor do serviço em decimal, separado por .")
    @Min(0)
    private BigDecimal valor;


    @NotNull
    @ApiModelProperty(value = "Nome da moeda em que se contrata o serviço: DOLAR:0, REAL:1, EURO:2, IENE:3, YUAN:4")
    private TipoMoeda moeda;


    @NotNull
    @ApiModelProperty(value = "Periodicidade: MENSAL:0, TRIMESTRAL:1, SEMESTRAL:2, ANUAL:3")
    private TipoPeriodicidade periocidade;


    @ApiModelProperty(value = "Id do gerente do serviço")
    @Min(value = 1)
    private Integer idGerente;



}
