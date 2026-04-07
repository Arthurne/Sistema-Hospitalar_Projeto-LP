package br.ufcg.hospital.enums;

import java.io.Serializable;

public enum FormaPagamento implements Serializable {
    DINHEIRO_OU_PIX(0.90),
    CARTAO_CREDITO_A_VISTA(1.00),
    PARCELADO_EM_3X(1.08);

    private final double multiplicador;

    FormaPagamento(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public double aplicar(double valorBase) {
        return valorBase * multiplicador;
    }
}
