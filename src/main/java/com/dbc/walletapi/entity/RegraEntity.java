package com.dbc.walletapi.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity(name = "REGRA")
public class RegraEntity implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_regra")
    @SequenceGenerator(name = "seq_regra", sequenceName = "seq_regra", allocationSize = 1)
    @Column(name = "id_regra")
    private Integer idRegra;

    private String nome;

    @ManyToMany(mappedBy = "regras")
    private List<GrupoEntity> grupos;

    @Override
    public String getAuthority() {
        return nome;
    }
}
