package br.ufcg.hospital.model;

import br.ufcg.hospital.enums.TipoInternacao;

import java.io.Serializable;
import java.util.Objects;

public class PlanoSaude implements Serializable {
    private final String operadora;
    private final TipoInternacao tipoMaximoPermitido;
    private final double percentualPagamentoPaciente;

    public PlanoSaude(String operadora, TipoInternacao tipoMaximoPermitido, double percentualPagamentoPaciente) {
        this.operadora = Objects.requireNonNull(operadora, "Operadora não pode ser nula").trim();
        this.tipoMaximoPermitido = Objects.requireNonNull(tipoMaximoPermitido, "Tipo permitido não pode ser nulo");
        this.percentualPagamentoPaciente = percentualPagamentoPaciente;
    }

    public static PlanoSaude particular() {
        return new PlanoSaude("Particular", TipoInternacao.APARTAMENTO, 1.0);
    }

    public boolean permite(TipoInternacao tipoInternacao) {
        if (tipoInternacao == TipoInternacao.UTI) {
            return true;
        }
        if ("Particular".equalsIgnoreCase(operadora)) {
            return true;
        }
        if (tipoMaximoPermitido == TipoInternacao.APARTAMENTO) {
            return true;
        }
        return tipoMaximoPermitido == TipoInternacao.ENFERMARIA && tipoInternacao == TipoInternacao.ENFERMARIA;
    }

    public String getOperadora() {
        return operadora;
    }

    public TipoInternacao getTipoMaximoPermitido() {
        return tipoMaximoPermitido;
    }

    public double getPercentualPagamentoPaciente() {
        return percentualPagamentoPaciente;
    }

    @Override
    public String toString() {
        return "PlanoSaude{" +
                "operadora='" + operadora + '\'' +
                ", tipoMaximoPermitido=" + tipoMaximoPermitido +
                ", percentualPagamentoPaciente=" + percentualPagamentoPaciente +
                '}';
    }
}
