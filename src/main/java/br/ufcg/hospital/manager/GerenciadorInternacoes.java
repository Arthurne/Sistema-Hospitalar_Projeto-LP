package br.ufcg.hospital.manager;

import br.ufcg.hospital.enums.TipoInternacao;
import br.ufcg.hospital.exception.EntidadeNaoEncontradaException;
import br.ufcg.hospital.exception.OperacaoInvalidaException;
import br.ufcg.hospital.model.Internacao;
import br.ufcg.hospital.model.Medico;
import br.ufcg.hospital.model.Paciente;
import br.ufcg.hospital.util.Validador;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class GerenciadorInternacoes implements Serializable {
    private final Map<String, Internacao> internacoes = new LinkedHashMap<>();

    public String registrar(Medico medico, Paciente paciente, TipoInternacao tipoInternacao, String localizacao, LocalDate dataEntrada) {
        Validador.exigirTexto(localizacao, "Localização");
        if (!medico.isAtivo()) {
            throw new OperacaoInvalidaException("Não é possível internar paciente com médico inativo.");
        }
        if (!paciente.getPlanoSaude().permite(tipoInternacao)) {
            throw new OperacaoInvalidaException("Plano de saúde do paciente não permite esse tipo de internação.");
        }
        Internacao internacao = new Internacao(medico, paciente, tipoInternacao, localizacao, dataEntrada);
        internacoes.put(internacao.getId(), internacao);
        return internacao.getId();
    }

    public Internacao buscarPorId(String id) {
        Internacao internacao = internacoes.get(id);
        if (internacao == null) {
            throw new EntidadeNaoEncontradaException("Internação não encontrada para o ID informado.");
        }
        return internacao;
    }

    public void registrarAlta(String id, LocalDate dataAlta) {
        Internacao internacao = buscarPorId(id);
        if (!internacao.isAtiva()) {
            throw new OperacaoInvalidaException("A internação informada já recebeu alta.");
        }
        if (dataAlta.isBefore(internacao.getDataEntrada())) {
            throw new OperacaoInvalidaException("Data de alta não pode ser anterior à data de entrada.");
        }
        internacao.registrarAlta(dataAlta);
    }

    public double calcularValorResponsabilidadePaciente(String id) {
        return buscarPorId(id).calcularValorResponsabilidadePaciente();
    }

    public List<Internacao> pesquisarPorCpf(String cpf) {
        Validador.validarCpf(cpf);
        return internacoes.values().stream().filter(i -> i.getPaciente().getCpf().equals(cpf)).collect(Collectors.toList());
    }

    public List<Internacao> listarAtivasOrdenadas() {
        return internacoes.values().stream()
                .filter(Internacao::isAtiva)
                .sorted(Comparator.comparing(Internacao::getDataEntrada).reversed())
                .collect(Collectors.toList());
    }

    public List<Internacao> listarConcluidas() {
        return internacoes.values().stream().filter(i -> !i.isAtiva()).collect(Collectors.toList());
    }
}
