package br.ufcg.hospital.model;

import java.io.Serializable;
import java.util.Objects;

public class Paciente implements Serializable {
    private final String nome;
    private final String cpf;
    private final String telefone;
    private PlanoSaude planoSaude;

    public Paciente(String nome, String cpf, String telefone, PlanoSaude planoSaude) {
        this.nome = Objects.requireNonNull(nome).trim();
        this.cpf = Objects.requireNonNull(cpf).trim();
        this.telefone = Objects.requireNonNull(telefone).trim();
        this.planoSaude = Objects.requireNonNull(planoSaude);
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getTelefone() { return telefone; }
    public PlanoSaude getPlanoSaude() { return planoSaude; }

    public void atualizarPlanoSaude(PlanoSaude planoSaude) {
        this.planoSaude = Objects.requireNonNull(planoSaude);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", telefone='" + telefone + '\'' +
                ", planoSaude=" + planoSaude +
                '}';
    }
}
