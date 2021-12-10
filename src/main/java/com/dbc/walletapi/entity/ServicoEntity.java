package com.dbc.walletapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity(name= "SERIVCO")
public class ServicoEntity implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_servico")
    @SequenceGenerator(name = "seq_servico", sequenceName = "seq_servico", allocationSize = 1)
    @Column(name = "id_servico")
    private Integer idServico;

    private String nome;
    private String descricao;
    private String webSite;
    private BigDecimal valor;
    private TipoMoeda moeda;
    private Integer periocidade;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gerente", referencedColumnName = "id_gerente")
    private GerenteEntity gerenteEntity;


    @Override
    public String getAuthority() {
        return null;
    }
}
