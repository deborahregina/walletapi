package com.dbc.walletapi.dto;

import com.dbc.walletapi.entity.TipoMoeda;
import com.dbc.walletapi.entity.TipoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServicoAtualizaDTO {

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;
    @NotNull(message = "Descrição não pode ser nula")
    @NotEmpty(message = "Descrição não pode ser vazia")
    private String descricao;
    @NotNull(message = "Website não pode ser nulo")
    @NotEmpty(message = "Website não pode ser vazio")
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
