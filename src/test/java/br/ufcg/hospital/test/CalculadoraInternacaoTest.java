package br.ufcg.hospital.test;

import br.ufcg.hospital.util.CalculadoraInternacao;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadoraInternacaoTest {

    @Test
    public void testCalcularValorBase() {

        double valorEsperado = 1600.0;
        double valorCalculado = CalculadoraInternacao.calcularValorBase(5, 320.0);


        assertEquals(valorEsperado, valorCalculado, 0.0001, "O cálculo do valor base da internação está incorreto.");
    }

    @Test
    public void testCalcularResponsabilidadePaciente() {

        double valorEsperado = 300.0;
        double valorCalculado = CalculadoraInternacao.calcularResponsabilidadePaciente(2000.0, 0.15);


        assertEquals(valorEsperado, valorCalculado, 0.0001, "O cálculo da parcela de responsabilidade do paciente está incorreto.");
    }
}
