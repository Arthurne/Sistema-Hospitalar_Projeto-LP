package br.ufcg.hospital.util;

public class CalculadoraInternacao {
    private CalculadoraInternacao() {}

    public static double calcularValorBase(long quantidadeDias, double diaria) {
        return quantidadeDias * diaria;
    }

    public static double calcularResponsabilidadePaciente(double valorBase, double percentual) {
        return valorBase * percentual;
    }
}
