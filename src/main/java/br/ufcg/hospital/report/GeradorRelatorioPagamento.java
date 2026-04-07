package br.ufcg.hospital.report;

import br.ufcg.hospital.model.Pagamento;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collection;

public class GeradorRelatorioPagamento {
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    private GeradorRelatorioPagamento() {}

    public static String gerar(Collection<Pagamento> pagamentos, String caminhoArquivo) {
        StringBuilder sb = new StringBuilder();
        sb.append("RELATORIO DE PAGAMENTOS\n");
        sb.append("===========================================\n");
        double total = 0.0;
        for (Pagamento pagamento : pagamentos) {
            total += pagamento.getValorPago();
            sb.append("Internacao: ").append(pagamento.getInternacao().getId()).append("\n")
              .append("Entrada: ").append(pagamento.getInternacao().getDataEntrada()).append("\n")
              .append("Alta: ").append(pagamento.getInternacao().getDataAlta()).append("\n")
              .append("CPF: ").append(pagamento.getInternacao().getPaciente().getCpf()).append("\n")
              .append("Valor Pago: R$ ").append(DF.format(pagamento.getValorPago())).append("\n")
              .append("Forma de Pagamento: ").append(pagamento.getFormaPagamento()).append("\n")
              .append("-------------------------------------------\n");
        }
        sb.append("TOTAL RECEBIDO: R$ ").append(DF.format(total)).append("\n");

        File arquivo = new File(caminhoArquivo);
        File pasta = arquivo.getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            pw.print(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar relatório de pagamentos em TXT.", e);
        }
        return sb.toString();
    }
}
