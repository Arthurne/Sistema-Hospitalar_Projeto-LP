package br.ufcg.hospital.model;

import br.ufcg.hospital.enums.FormaPagamento;

import java.io.Serializable;
import java.util.Objects;

public class Pagamento implements Serializable {
    private final Internacao internacao;
    private final FormaPagamento formaPagamento;
    private final double valorPago;

    public Pagamento(Internacao internacao, FormaPagamento formaPagamento, double valorPago) {
        this.internacao = Objects.requireNonNull(internacao);
        this.formaPagamento = Objects.requireNonNull(formaPagamento);
        this.valorPago = valorPago;
    }

    public Internacao getInternacao() { return internacao; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public double getValorPago() { return valorPago; }

    @Override
    public String toString() {
        return "Pagamento{" +
                "internacao=" + internacao.getId() +
                ", formaPagamento=" + formaPagamento +
                ", valorPago=" + valorPago +
                '}';
    }
}
