package br.ufcg.hospital.enums;

import java.io.Serializable;

public enum TipoInternacao implements Serializable {
    ENFERMARIA(320.0),
    APARTAMENTO(780.0),
    UTI(2450.0);

    private final double diaria;

    TipoInternacao(double diaria) {
        this.diaria = diaria;
    }

    public double getDiaria() {
        return diaria;
    }
}
