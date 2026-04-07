package br.ufcg.hospital.test;

import br.ufcg.hospital.enums.FormaPagamento;
import br.ufcg.hospital.util.CalculadoraPagamento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadoraPagamentoTest {

    @Test
    public void testPagamentoDinheiroOuPix() {

        double valorEsperado = 900.0;
        double valorCalculado = CalculadoraPagamento.calcularValorFinal(1000.0, FormaPagamento.DINHEIRO_OU_PIX);

        assertEquals(valorEsperado, valorCalculado, 0.0001, "O desconto de 10% para PIX ou Dinheiro está incorreto.");
    }

    @Test
    public void testPagamentoParcelado() {

        double valorEsperado = 1080.0;
        double valorCalculado = CalculadoraPagamento.calcularValorFinal(1000.0, FormaPagamento.PARCELADO_EM_3X);

        assertEquals(valorEsperado, valorCalculado, 0.0001, "O acréscimo de 8% para pagamento parcelado em 3x está incorreto.");
    }

    @Test
    public void testPagamentoCartaoAVista() {

        double valorEsperado = 1000.0;
        double valorCalculado = CalculadoraPagamento.calcularValorFinal(1000.0, FormaPagamento.CARTAO_CREDITO_A_VISTA);

        assertEquals(valorEsperado, valorCalculado, 0.0001, "O pagamento no cartão à vista não deve aplicar juros ou descontos.");
    }
}
