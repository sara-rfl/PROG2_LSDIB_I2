package model;

import interfaces.OrdenavelPorData;
import interfaces.PessoaInterface;

import java.time.LocalDate;

public abstract class Pessoa implements PessoaInterface, OrdenavelPorData {
    private static int contador = 1000;

    private final int id;
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.id = contador++;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public Pessoa(String nome, LocalDate dataNascimento, int id) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public int getId() { return id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public LocalDate getDataReferencia() {
        return dataNascimento;
    }

    @Override
    public String toString() {
        return String.format("%s (ID: %d, Nasc.: %s)", nome, id, dataNascimento);
    }
}
