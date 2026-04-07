package br.ufcg.hospital.manager;

import br.ufcg.hospital.exception.EntidadeNaoEncontradaException;
import br.ufcg.hospital.exception.OperacaoInvalidaException;
import br.ufcg.hospital.model.Paciente;
import br.ufcg.hospital.model.PlanoSaude;
import br.ufcg.hospital.util.Validador;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class GerenciadorPacientes implements Serializable {
    private final Map<String, Paciente> pacientes = new LinkedHashMap<>();

    public void cadastrarPaciente(String nome, String cpf, String telefone, PlanoSaude planoSaude) {
        Validador.exigirTexto(nome, "Nome");
        Validador.validarCpf(cpf);
        Validador.validarTelefone(telefone);
        if (pacientes.containsKey(cpf)) {
            throw new OperacaoInvalidaException("Já existe paciente cadastrado com esse CPF.");
        }
        pacientes.put(cpf, new Paciente(nome, cpf, telefone, planoSaude));
    }

    public Paciente localizarPorCpf(String cpf) {
        Validador.validarCpf(cpf);
        Paciente paciente = pacientes.get(cpf);
        if (paciente == null) {
            throw new EntidadeNaoEncontradaException("Paciente não encontrado para o CPF informado.");
        }
        return paciente;
    }

    public void atualizarPlanoSaude(String cpf, PlanoSaude novoPlano) {
        localizarPorCpf(cpf).atualizarPlanoSaude(novoPlano);
    }

    public Collection<Paciente> listarTodos() {
        return pacientes.values();
    }
}
