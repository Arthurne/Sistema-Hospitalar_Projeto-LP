package br.ufcg.hospital.model;

import br.ufcg.hospital.enums.Especialidade;

import java.io.Serializable;
import java.util.Objects;

public class Medico implements Serializable {
    private final String nome;
    private final String crm;
    private Especialidade especialidade;
    private boolean ativo;

    public Medico(String nome, String crm, Especialidade especialidade) {
        this.nome = Objects.requireNonNull(nome).trim();
        this.crm = Objects.requireNonNull(crm).trim();
        this.especialidade = Objects.requireNonNull(especialidade);
        this.ativo = true;
    }

    public String getNome() { return nome; }
    public String getCrm() { return crm; }
    public Especialidade getEspecialidade() { return especialidade; }
    public boolean isAtivo() { return ativo; }

    public void setEspecialidade(Especialidade especialidade) { this.especialidade = Objects.requireNonNull(especialidade); }
    public void desativar() { this.ativo = false; }
    public void ativar() { this.ativo = true; }

    @Override
    public String toString() {
        return "Medico{" +
                "nome='" + nome + '\'' +
                ", crm='" + crm + '\'' +
                ", especialidade=" + especialidade +
                ", ativo=" + ativo +
                '}';
    }
}
