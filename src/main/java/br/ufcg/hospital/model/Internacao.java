package br.ufcg.hospital.model;

import br.ufcg.hospital.enums.TipoInternacao;
import br.ufcg.hospital.util.CalculadoraInternacao;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

public class Internacao implements Serializable {
    private final String id;
    private final Medico medicoResponsavel;
    private final Paciente paciente;
    private final TipoInternacao tipoInternacao;
    private final String localizacao;
    private final LocalDate dataEntrada;
    private LocalDate dataAlta;

    public Internacao(Medico medicoResponsavel, Paciente paciente, TipoInternacao tipoInternacao, String localizacao, LocalDate dataEntrada) {
        this.id = UUID.randomUUID().toString();
        this.medicoResponsavel = Objects.requireNonNull(medicoResponsavel);
        this.paciente = Objects.requireNonNull(paciente);
        this.tipoInternacao = Objects.requireNonNull(tipoInternacao);
        this.localizacao = Objects.requireNonNull(localizacao).trim();
        this.dataEntrada = Objects.requireNonNull(dataEntrada);
    }

    public String getId() { return id; }
    public Medico getMedicoResponsavel() { return medicoResponsavel; }
    public Paciente getPaciente() { return paciente; }
    public TipoInternacao getTipoInternacao() { return tipoInternacao; }
    public String getLocalizacao() { return localizacao; }
    public LocalDate getDataEntrada() { return dataEntrada; }
    public LocalDate getDataAlta() { return dataAlta; }

    public boolean isAtiva() { return dataAlta == null; }

    public void registrarAlta(LocalDate dataAlta) {
        this.dataAlta = Objects.requireNonNull(dataAlta);
    }

    public long getQuantidadeDias() {
        LocalDate fim = dataAlta != null ? dataAlta : LocalDate.now();
        long dias = ChronoUnit.DAYS.between(dataEntrada, fim);
        return Math.max(1, dias == 0 ? 1 : dias);
    }

    public double calcularValorBase() {
        return CalculadoraInternacao.calcularValorBase(getQuantidadeDias(), tipoInternacao.getDiaria());
    }

    public double calcularValorResponsabilidadePaciente() {
        return CalculadoraInternacao.calcularResponsabilidadePaciente(calcularValorBase(), paciente.getPlanoSaude().getPercentualPagamentoPaciente());
    }

    @Override
    public String toString() {
        return "Internacao{" +
                "id='" + id + '\'' +
                ", medicoResponsavel=" + medicoResponsavel.getNome() +
                ", paciente=" + paciente.getNome() +
                ", tipoInternacao=" + tipoInternacao +
                ", localizacao='" + localizacao + '\'' +
                ", dataEntrada=" + dataEntrada +
                ", dataAlta=" + dataAlta +
                '}';
    }
}
