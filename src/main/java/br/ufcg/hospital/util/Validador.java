package br.ufcg.hospital.util;

import br.ufcg.hospital.exception.ValidacaoException;

public class Validador {
    private Validador() {}

    public static void exigirTexto(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacaoException(campo + " não pode ser nulo ou vazio.");
        }
    }

    public static void validarCpf(String cpf) {
        exigirTexto(cpf, "CPF");
        if (!cpf.matches("\\d{11}")) {
            throw new ValidacaoException("CPF deve ter exatamente 11 dígitos numéricos.");
        }
    }

    public static void validarTelefone(String telefone) {
        exigirTexto(telefone, "Telefone");
        if (!telefone.matches("\\d{11}")) {
            throw new ValidacaoException("Telefone deve ter exatamente 11 dígitos numéricos.");
        }
    }

    public static void validarCrm(String crm) {
        exigirTexto(crm, "CRM");
        if (!crm.matches("\\d{6}/[A-Z]{2}")) {
            throw new ValidacaoException("CRM deve seguir o formato 123456/UF.");
        }
    }

    public static void validarPercentual(double percentual) {
        if (percentual != 0.0 && percentual != 0.15 && percentual != 1.0) {
            throw new ValidacaoException("Percentual de pagamento deve ser 0.0, 0.15 ou 1.0.");
        }
    }
}
