package com.dbc.walletapi.entity;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum TipoStatus {

    ATIVO(1),
    INATIVO(2);

    private Integer tipo;

    public Integer getTipo() {
        return tipo;
    }

    public static TipoStatus ofTipo(Integer tipo) {
        return Arrays.stream(TipoStatus.values())
                .filter(tp -> tp.getTipo().equals(tipo))
                .findFirst()
                .get();

    }
}
