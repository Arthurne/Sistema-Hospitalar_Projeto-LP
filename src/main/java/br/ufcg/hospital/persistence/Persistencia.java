package br.ufcg.hospital.persistence;

import br.ufcg.hospital.manager.GerenciadorInternacoes;
import br.ufcg.hospital.manager.GerenciadorMedicos;
import br.ufcg.hospital.manager.GerenciadorPacientes;
import br.ufcg.hospital.manager.GerenciadorPagamentos;

import java.io.*;

public class Persistencia {
    private final File arquivo;

    public Persistencia(String caminhoArquivo) {
        this.arquivo = new File(caminhoArquivo);
        File diretorio = arquivo.getParentFile();
        if (diretorio != null && !diretorio.exists()) {
            diretorio.mkdirs();
        }
    }

    public DataStore carregar() {
        if (!arquivo.exists()) {
            return new DataStore(new GerenciadorPacientes(), new GerenciadorMedicos(), new GerenciadorInternacoes(), new GerenciadorPagamentos());
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (DataStore) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao carregar dados do sistema.", e);
        }
    }

    public void salvar(DataStore dataStore) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(dataStore);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar dados do sistema.", e);
        }
    }
}
