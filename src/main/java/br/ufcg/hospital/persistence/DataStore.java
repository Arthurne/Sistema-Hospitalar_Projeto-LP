package br.ufcg.hospital.persistence;

import br.ufcg.hospital.manager.GerenciadorInternacoes;
import br.ufcg.hospital.manager.GerenciadorMedicos;
import br.ufcg.hospital.manager.GerenciadorPacientes;
import br.ufcg.hospital.manager.GerenciadorPagamentos;

import java.io.Serializable;

public class DataStore implements Serializable {
    private final GerenciadorPacientes gerenciadorPacientes;
    private final GerenciadorMedicos gerenciadorMedicos;
    private final GerenciadorInternacoes gerenciadorInternacoes;
    private final GerenciadorPagamentos gerenciadorPagamentos;

    public DataStore(GerenciadorPacientes gerenciadorPacientes, GerenciadorMedicos gerenciadorMedicos,
                     GerenciadorInternacoes gerenciadorInternacoes, GerenciadorPagamentos gerenciadorPagamentos) {
        this.gerenciadorPacientes = gerenciadorPacientes;
        this.gerenciadorMedicos = gerenciadorMedicos;
        this.gerenciadorInternacoes = gerenciadorInternacoes;
        this.gerenciadorPagamentos = gerenciadorPagamentos;
    }

    public GerenciadorPacientes getGerenciadorPacientes() { return gerenciadorPacientes; }
    public GerenciadorMedicos getGerenciadorMedicos() { return gerenciadorMedicos; }
    public GerenciadorInternacoes getGerenciadorInternacoes() { return gerenciadorInternacoes; }
    public GerenciadorPagamentos getGerenciadorPagamentos() { return gerenciadorPagamentos; }
}
