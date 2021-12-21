package com.dbc.walletapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Entity(name= "SERVICO")
public class ServicoEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_servico")
    @SequenceGenerator(name = "seq_servico", sequenceName = "seq_servico", allocationSize = 1)
    @Column(name = "id_servico")
    private Integer idServico;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "website")
    private String webSite;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "moeda")
    private TipoMoeda moeda;

    @Column(name = "periodicidade")
    private TipoPeriodicidade periocidade;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gerente", referencedColumnName = "id_gerente")
    private GerenteEntity gerenteEntity;

    @Column(name = "status")
    private TipoStatus status;

    @Column(name = "data_criacao")
    private LocalDate data;

    @Column(name = "DATA_DELETE")
    private LocalDate dataDeletado;

    @Column(name = "VALOR_ORIGINAL")
    private BigDecimal valorOriginal;
}
