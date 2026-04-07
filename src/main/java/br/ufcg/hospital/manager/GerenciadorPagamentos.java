package br.ufcg.hospital.manager;

import br.ufcg.hospital.enums.FormaPagamento;
import br.ufcg.hospital.exception.OperacaoInvalidaException;
import br.ufcg.hospital.model.Internacao;
import br.ufcg.hospital.model.Pagamento;
import br.ufcg.hospital.report.GeradorRelatorioPagamento;
import br.ufcg.hospital.util.CalculadoraPagamento;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class GerenciadorPagamentos implements Serializable {
    private final Map<String, Pagamento> pagamentosPorInternacao = new LinkedHashMap<>();

    public Pagamento registrarPagamento(Internacao internacao, FormaPagamento formaPagamento) {
        if (internacao.isAtiva()) {
            throw new OperacaoInvalidaException("A internação ainda está ativa. Registre a alta antes do pagamento.");
        }
        if (pagamentosPorInternacao.containsKey(internacao.getId())) {
            throw new OperacaoInvalidaException("Pagamento dessa internação já foi registrado.");
        }
        double valorBasePaciente = internacao.calcularValorResponsabilidadePaciente();
        double valorPago = CalculadoraPagamento.calcularValorFinal(valorBasePaciente, formaPagamento);
        Pagamento pagamento = new Pagamento(internacao, formaPagamento, valorPago);
        pagamentosPorInternacao.put(internacao.getId(), pagamento);
        return pagamento;
    }

    public boolean existePagamento(String idInternacao) {
        return pagamentosPorInternacao.containsKey(idInternacao);
    }

    public Collection<Pagamento> listarTodos() {
        return pagamentosPorInternacao.values();
    }

    public String gerarRelatorioTxt(String caminhoArquivo) {
        return GeradorRelatorioPagamento.gerar(listarTodos(), caminhoArquivo);
    }
}
