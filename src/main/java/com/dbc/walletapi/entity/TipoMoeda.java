package com.dbc.walletapi.entity;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum TipoMoeda {

    MOEDA1(1),
    MOEDA2(2);

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
