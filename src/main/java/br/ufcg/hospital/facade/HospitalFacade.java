package br.ufcg.hospital.facade;

import br.ufcg.hospital.enums.Especialidade;
import br.ufcg.hospital.enums.FormaPagamento;
import br.ufcg.hospital.enums.TipoInternacao;
import br.ufcg.hospital.manager.GerenciadorInternacoes;
import br.ufcg.hospital.manager.GerenciadorMedicos;
import br.ufcg.hospital.manager.GerenciadorPacientes;
import br.ufcg.hospital.manager.GerenciadorPagamentos;
import br.ufcg.hospital.model.*;
import br.ufcg.hospital.persistence.DataStore;
import br.ufcg.hospital.persistence.Persistencia;
import br.ufcg.hospital.util.Validador;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class HospitalFacade {
    private final Persistencia persistencia;
    private GerenciadorPacientes gerenciadorPacientes;
    private GerenciadorMedicos gerenciadorMedicos;
    private GerenciadorInternacoes gerenciadorInternacoes;
    private GerenciadorPagamentos gerenciadorPagamentos;

    public HospitalFacade(String caminhoArquivo) {
        this.persistencia = new Persistencia(caminhoArquivo);
        carregarEstado();
    }

    private void carregarEstado() {
        DataStore dataStore = persistencia.carregar();
        this.gerenciadorPacientes = dataStore.getGerenciadorPacientes();
        this.gerenciadorMedicos = dataStore.getGerenciadorMedicos();
        this.gerenciadorInternacoes = dataStore.getGerenciadorInternacoes();
        this.gerenciadorPagamentos = dataStore.getGerenciadorPagamentos();
    }

    private void salvarEstado() {
        persistencia.salvar(new DataStore(gerenciadorPacientes, gerenciadorMedicos, gerenciadorInternacoes, gerenciadorPagamentos));
    }

    public void cadastrarPaciente(String nome, String cpf, String telefone, String operadora, TipoInternacao tipoMaximoPermitido, double percentual) {
        Validador.validarPercentual(percentual);
        gerenciadorPacientes.cadastrarPaciente(nome, cpf, telefone, new PlanoSaude(operadora, tipoMaximoPermitido, percentual));
        salvarEstado();
    }

    public Paciente buscarPaciente(String cpf) { return gerenciadorPacientes.localizarPorCpf(cpf); }

    public void atualizarPlanoPaciente(String cpf, String operadora, TipoInternacao tipoMaximoPermitido, double percentual) {
        Validador.validarPercentual(percentual);
        gerenciadorPacientes.atualizarPlanoSaude(cpf, new PlanoSaude(operadora, tipoMaximoPermitido, percentual));
        salvarEstado();
    }

    public void cadastrarMedico(String nome, String crm, Especialidade especialidade) {
        gerenciadorMedicos.cadastrar(nome, crm, especialidade);
        salvarEstado();
    }

    public void atualizarEspecialidadeMedico(String crm, Especialidade especialidade) {
        gerenciadorMedicos.atualizarEspecialidade(crm, especialidade);
        salvarEstado();
    }

    public void removerMedico(String crm) {
        gerenciadorMedicos.removerLogicamente(crm);
        salvarEstado();
    }

    public List<Medico> listarMedicosAtivos() { return gerenciadorMedicos.listarAtivos(); }

    public String registrarInternacao(String crm, String cpf, TipoInternacao tipoInternacao, String localizacao, LocalDate dataEntrada) {
        String id = gerenciadorInternacoes.registrar(gerenciadorMedicos.buscarPorCrm(crm), gerenciadorPacientes.localizarPorCpf(cpf), tipoInternacao, localizacao, dataEntrada);
        salvarEstado();
        return id;
    }

    public void registrarAlta(String idInternacao, LocalDate dataAlta) {
        gerenciadorInternacoes.registrarAlta(idInternacao, dataAlta);
        salvarEstado();
    }

    public double calcularValorInternacaoPaciente(String idInternacao) {
        return gerenciadorInternacoes.calcularValorResponsabilidadePaciente(idInternacao);
    }

    public List<Internacao> pesquisarInternacoesPorCpf(String cpf) { return gerenciadorInternacoes.pesquisarPorCpf(cpf); }
    public List<Internacao> listarInternacoesAtivas() { return gerenciadorInternacoes.listarAtivasOrdenadas(); }
    public List<Internacao> listarInternacoesConcluidas() { return gerenciadorInternacoes.listarConcluidas(); }

    public Pagamento pagarInternacao(String idInternacao, FormaPagamento formaPagamento) {
        Pagamento pagamento = gerenciadorPagamentos.registrarPagamento(gerenciadorInternacoes.buscarPorId(idInternacao), formaPagamento);
        salvarEstado();
        return pagamento;
    }

    public String gerarRelatorioPagamentos(String caminhoTxt) {
        String relatorio = gerenciadorPagamentos.gerarRelatorioTxt(caminhoTxt);
        salvarEstado();
        return relatorio;
    }

    public Collection<Pagamento> listarPagamentos() { return gerenciadorPagamentos.listarTodos(); }
}
