package br.ufcg.hospital.manager;

import br.ufcg.hospital.enums.Especialidade;
import br.ufcg.hospital.exception.EntidadeNaoEncontradaException;
import br.ufcg.hospital.exception.OperacaoInvalidaException;
import br.ufcg.hospital.model.Medico;
import br.ufcg.hospital.util.Validador;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class GerenciadorMedicos implements Serializable {
    private final Map<String, Medico> medicos = new LinkedHashMap<>();

    public void cadastrar(String nome, String crm, Especialidade especialidade) {
        Validador.exigirTexto(nome, "Nome");
        Validador.validarCrm(crm);
        if (medicos.containsKey(crm)) {
            throw new OperacaoInvalidaException("Já existe médico cadastrado com esse CRM.");
        }
        medicos.put(crm, new Medico(nome, crm, especialidade));
    }

    public Medico buscarPorCrm(String crm) {
        Validador.validarCrm(crm);
        Medico medico = medicos.get(crm);
        if (medico == null) {
            throw new EntidadeNaoEncontradaException("Médico não encontrado para o CRM informado.");
        }
        return medico;
    }

    public void atualizarEspecialidade(String crm, Especialidade especialidade) {
        buscarPorCrm(crm).setEspecialidade(especialidade);
    }

    public void removerLogicamente(String crm) {
        buscarPorCrm(crm).desativar();
    }

    public void reativar(String crm) {
        buscarPorCrm(crm).ativar();
    }

    public List<Medico> listarAtivos() {
        return medicos.values().stream().filter(Medico::isAtivo).collect(Collectors.toList());
    }
}
