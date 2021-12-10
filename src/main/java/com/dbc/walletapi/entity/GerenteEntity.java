package com.dbc.walletapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "GERENTE")
public class GerenteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GERENTE")
    @SequenceGenerator(name = "SEQ_GERENTE", sequenceName = "SEQ_GERENTE", allocationSize = 1)
    @Column(name = "ID_GERENTE")
    private Integer idGerente;

    @Column(name = "NOME")
    private String nomeCompleto;

    @Column(name = "EMAIL")
    private String email;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private UsuarioEntity usuario;

    @Column(name = "STATUS")
    private TipoStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "gerenteEntity", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoEntity> servicos;




}
