package com.dbc.walletapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "regraEntity", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioEntity> usuarios;

    @Override
    public String getAuthority() {
        return nome;
    }
}
