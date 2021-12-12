package com.dbc.walletapi.entity;

import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public enum TipoPeriodicidade {


    MENSAL(1),
    TRIMESTRAL(2),
    SEMESTRAL(3),
    ANUAL(4);

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
