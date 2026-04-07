package br.ufcg.hospital.util;

import br.ufcg.hospital.enums.FormaPagamento;

public class CalculadoraPagamento {
    private CalculadoraPagamento() {}

    public static double calcularValorFinal(double valorBasePaciente, FormaPagamento formaPagamento) {
        return formaPagamento.aplicar(valorBasePaciente);
    }
}
