package br.ufcg.hospital.app;

import br.ufcg.hospital.enums.Especialidade;
import br.ufcg.hospital.enums.FormaPagamento;
import br.ufcg.hospital.enums.TipoInternacao;
import br.ufcg.hospital.facade.HospitalFacade;
import br.ufcg.hospital.model.Internacao;
import br.ufcg.hospital.model.Medico;
import br.ufcg.hospital.model.Paciente;
import br.ufcg.hospital.model.Pagamento;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String DB_PATH = "data/hospital.dat";
    private static final String RELATORIO_PATH = "data/relatorio_pagamentos.txt";
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    public static void main(String[] args) {
        HospitalFacade facade = new HospitalFacade(DB_PATH);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Olá! Sou o assistente do hospital. Vamos organizar as internações?\n");

        boolean executando = true;
        while (executando) {
            try {
                exibirMenu();
                String opcao = scanner.nextLine().trim();
                switch (opcao) {
                    case "1" -> cadastrarPaciente(scanner, facade);
                    case "2" -> buscarPaciente(scanner, facade);
                    case "3" -> atualizarPlano(scanner, facade);
                    case "4" -> cadastrarMedico(scanner, facade);
                    case "5" -> atualizarEspecialidadeMedico(scanner, facade);
                    case "6" -> removerMedico(scanner, facade);
                    case "7" -> listarMedicosAtivos(facade);
                    case "8" -> registrarInternacao(scanner, facade);
                    case "9" -> registrarAlta(scanner, facade);
                    case "10" -> calcularValor(scanner, facade);
                    case "11" -> pesquisarInternacoesPaciente(scanner, facade);
                    case "12" -> listarInternacoesAtivas(facade);
                    case "13" -> listarInternacoesConcluidas(facade);
                    case "14" -> pagarInternacao(scanner, facade);
                    case "15" -> gerarRelatorioPagamentos(facade);
                    case "0" -> {
                        executando = false;
                        System.out.println("Até logo! Sistema finalizado com os dados persistidos.");
                    }
                    default -> System.out.println("Opção inválida. Tenta novamente.\n");
                }
            } catch (Exception e) {
                System.out.println("Ops! Não consegui concluir a operação: " + e.getMessage() + "\n");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("================ MENU ================");
        System.out.println("1. Cadastrar paciente");
        System.out.println("2. Buscar paciente por CPF");
        System.out.println("3. Atualizar plano de saúde do paciente");
        System.out.println("4. Cadastrar médico");
        System.out.println("5. Atualizar especialidade do médico");
        System.out.println("6. Remover médico (lógico)");
        System.out.println("7. Listar médicos ativos");
        System.out.println("8. Registrar internação");
        System.out.println("9. Registrar alta");
        System.out.println("10. Calcular valor da internação");
        System.out.println("11. Pesquisar internações por CPF");
        System.out.println("12. Relatório de internações ativas");
        System.out.println("13. Relatório de internações concluídas");
        System.out.println("14. Registrar pagamento de internação concluída");
        System.out.println("15. Gerar relatório TXT de pagamentos");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarPaciente(Scanner scanner, HospitalFacade facade) {
        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF (11 dígitos): ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone (11 dígitos): ");
        String telefone = scanner.nextLine();
        PlanoInput plano = lerPlano(scanner);
        facade.cadastrarPaciente(nome, cpf, telefone, plano.operadora(), plano.tipoInternacao(), plano.percentual());
        System.out.println("Paciente cadastrado com sucesso!\n");
    }

    private static void buscarPaciente(Scanner scanner, HospitalFacade facade) {
        System.out.print("CPF do paciente: ");
        Paciente paciente = facade.buscarPaciente(scanner.nextLine());
        System.out.println(paciente + "\n");
    }

    private static void atualizarPlano(Scanner scanner, HospitalFacade facade) {
        System.out.print("CPF do paciente: ");
        String cpf = scanner.nextLine();
        PlanoInput plano = lerPlano(scanner);
        facade.atualizarPlanoPaciente(cpf, plano.operadora(), plano.tipoInternacao(), plano.percentual());
        System.out.println("Plano atualizado com sucesso!\n");
    }

    private static void cadastrarMedico(Scanner scanner, HospitalFacade facade) {
        System.out.print("Nome do médico: ");
        String nome = scanner.nextLine();
        System.out.print("CRM (123456/UF): ");
        String crm = scanner.nextLine().toUpperCase();
        Especialidade especialidade = lerEspecialidade(scanner);
        facade.cadastrarMedico(nome, crm, especialidade);
        System.out.println("Médico cadastrado com sucesso!\n");
    }

    private static void atualizarEspecialidadeMedico(Scanner scanner, HospitalFacade facade) {
        System.out.print("CRM do médico: ");
        String crm = scanner.nextLine().toUpperCase();
        Especialidade especialidade = lerEspecialidade(scanner);
        facade.atualizarEspecialidadeMedico(crm, especialidade);
        System.out.println("Especialidade atualizada!\n");
    }

    private static void removerMedico(Scanner scanner, HospitalFacade facade) {
        System.out.print("CRM do médico: ");
        facade.removerMedico(scanner.nextLine().toUpperCase());
        System.out.println("Médico removido logicamente.\n");
    }

    private static void listarMedicosAtivos(HospitalFacade facade) {
        List<Medico> medicos = facade.listarMedicosAtivos();
        if (medicos.isEmpty()) {
            System.out.println("Nenhum médico ativo cadastrado.\n");
            return;
        }
        medicos.forEach(System.out::println);
        System.out.println();
    }

    private static void registrarInternacao(Scanner scanner, HospitalFacade facade) {
        System.out.print("CRM do médico responsável: ");
        String crm = scanner.nextLine().toUpperCase();
        System.out.print("CPF do paciente: ");
        String cpf = scanner.nextLine();
        TipoInternacao tipo = lerTipoInternacao(scanner);
        System.out.print("Quarto/enfermaria/ala da UTI: ");
        String localizacao = scanner.nextLine();
        LocalDate dataEntrada = lerData(scanner, "Data de entrada");
        String id = facade.registrarInternacao(crm, cpf, tipo, localizacao, dataEntrada);
        System.out.println("Internação registrada com sucesso! ID: " + id + "\n");
    }

    private static void registrarAlta(Scanner scanner, HospitalFacade facade) {
        System.out.print("ID da internação: ");
        String id = scanner.nextLine();
        LocalDate dataAlta = lerData(scanner, "Data de alta");
        facade.registrarAlta(id, dataAlta);
        System.out.println("Alta registrada com sucesso!\n");
    }

    private static void calcularValor(Scanner scanner, HospitalFacade facade) {
        System.out.print("ID da internação: ");
        double valor = facade.calcularValorInternacaoPaciente(scanner.nextLine());
        System.out.println("Valor devido pelo paciente: R$ " + DF.format(valor) + "\n");
    }

    private static void pesquisarInternacoesPaciente(Scanner scanner, HospitalFacade facade) {
        System.out.print("CPF do paciente: ");
        List<Internacao> internacoes = facade.pesquisarInternacoesPorCpf(scanner.nextLine());
        if (internacoes.isEmpty()) {
            System.out.println("Nenhuma internação encontrada para esse paciente.\n");
            return;
        }
        internacoes.forEach(System.out::println);
        System.out.println();
    }

    private static void listarInternacoesAtivas(HospitalFacade facade) {
        List<Internacao> internacoes = facade.listarInternacoesAtivas();
        if (internacoes.isEmpty()) {
            System.out.println("Nenhuma internação ativa no momento.\n");
            return;
        }
        internacoes.forEach(System.out::println);
        System.out.println();
    }

    private static void listarInternacoesConcluidas(HospitalFacade facade) {
        List<Internacao> internacoes = facade.listarInternacoesConcluidas();
        if (internacoes.isEmpty()) {
            System.out.println("Nenhuma internação concluída no sistema.\n");
            return;
        }
        internacoes.forEach(i -> System.out.println("ID=" + i.getId() + ", tipo=" + i.getTipoInternacao() + ", cpf=" + i.getPaciente().getCpf() + ", medico=" + i.getMedicoResponsavel().getNome() + ", periodo=" + i.getDataEntrada() + " até " + i.getDataAlta()));
        System.out.println();
    }

    private static void pagarInternacao(Scanner scanner, HospitalFacade facade) {
        System.out.print("ID da internação: ");
        String id = scanner.nextLine();
        FormaPagamento formaPagamento = lerFormaPagamento(scanner);
        Pagamento pagamento = facade.pagarInternacao(id, formaPagamento);
        System.out.println("Pagamento registrado! Valor final: R$ " + DF.format(pagamento.getValorPago()) + "\n");
    }

    private static void gerarRelatorioPagamentos(HospitalFacade facade) {
        String relatorio = facade.gerarRelatorioPagamentos(RELATORIO_PATH);
        System.out.println(relatorio);
        System.out.println("Arquivo TXT salvo em: " + RELATORIO_PATH + "\n");
    }

    private static LocalDate lerData(Scanner scanner, String label) {
        System.out.print(label + " - dia: ");
        int dia = Integer.parseInt(scanner.nextLine());
        System.out.print(label + " - mês: ");
        int mes = Integer.parseInt(scanner.nextLine());
        System.out.print(label + " - ano: ");
        int ano = Integer.parseInt(scanner.nextLine());
        return LocalDate.of(ano, mes, dia);
    }

    private static Especialidade lerEspecialidade(Scanner scanner) {
        System.out.println("Especialidades disponíveis:");
        Especialidade[] valores = Especialidade.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }
        int opcao = Integer.parseInt(scanner.nextLine());
        return valores[opcao - 1];
    }

    private static TipoInternacao lerTipoInternacao(Scanner scanner) {
        System.out.println("Tipos de internação:");
        TipoInternacao[] valores = TipoInternacao.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i] + " - diária R$ " + DF.format(valores[i].getDiaria()));
        }
        int opcao = Integer.parseInt(scanner.nextLine());
        return valores[opcao - 1];
    }

    private static FormaPagamento lerFormaPagamento(Scanner scanner) {
        System.out.println("Formas de pagamento:");
        FormaPagamento[] valores = FormaPagamento.values();
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }
        int opcao = Integer.parseInt(scanner.nextLine());
        return valores[opcao - 1];
    }

    private static PlanoInput lerPlano(Scanner scanner) {
        System.out.println("Escolha o cenário do plano:");
        System.out.println("1. Plano sem coparticipação");
        System.out.println("2. Plano com coparticipação");
        System.out.println("3. Sem plano (Particular)");
        int opcao = Integer.parseInt(scanner.nextLine());
        if (opcao == 3) {
            return new PlanoInput("Particular", TipoInternacao.APARTAMENTO, 1.0);
        }
        System.out.print("Nome da operadora: ");
        String operadora = scanner.nextLine();
        System.out.println("Tipo máximo permitido pelo plano:");
        System.out.println("1. ENFERMARIA");
        System.out.println("2. APARTAMENTO");
        int tipo = Integer.parseInt(scanner.nextLine());
        TipoInternacao permitido = tipo == 1 ? TipoInternacao.ENFERMARIA : TipoInternacao.APARTAMENTO;
        double percentual = opcao == 1 ? 0.0 : 0.15;
        return new PlanoInput(operadora, permitido, percentual);
    }

    private record PlanoInput(String operadora, TipoInternacao tipoInternacao, double percentual) {}
}
