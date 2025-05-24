package model;

import interfaces.OrdenavelPorData;
import interfaces.PessoaInterface;

import java.time.LocalDate;

/**
 * Classe abstrata que representa uma pessoa com nome, data de nascimento e identificador único.

 * Serve como base para outras classes como {@code Paciente} ou {@code TecnicoSaude}.
 * Implementa {@code PessoaInterface} e {@code OrdenavelPorData} para permitir uniformização
 * no acesso a dados pessoais e ordenação por data de nascimento.
 * O identificador {@code id} é atribuído automaticamente com base num contador interno,
 * salvo se for fornecido diretamente pelo construtor.
 */
abstract class Pessoa implements PessoaInterface, OrdenavelPorData {

    /** Contador interno para gerar identificadores únicos. */
    private static int contador = 1000;

    /** Identificador único da pessoa. */
    private final int id;

    /** Nome da pessoa. */
    private String nome;

    /** Data de nascimento da pessoa. */
    private LocalDate dataNascimento;

    /**
     * Construtor principal que gera automaticamente o identificador.
     *
     * @param nome           o nome da pessoa
     * @param dataNascimento a data de nascimento da pessoa
     */
    public Pessoa(String nome, LocalDate dataNascimento) {
        this.id = contador++;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    /**
     * Construtor alternativo que permite definir manualmente o identificador.
     *
     * @param nome           o nome da pessoa
     * @param dataNascimento a data de nascimento da pessoa
     * @param id             o identificador único a ser atribuído
     */
    public Pessoa(String nome, LocalDate dataNascimento, int id) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    /**
     * Obtém o identificador único da pessoa.
     *
     * @return o identificador {@code id}
     */
    public int getId() {
        return id;
    }

    /**
     * Obtém o nome da pessoa.
     *
     * @return o nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define ou altera o nome da pessoa.
     *
     * @param nome o novo nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a data de nascimento da pessoa.
     *
     * @return a data de nascimento
     */
    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    /**
     * Define ou altera a data de nascimento da pessoa.
     *
     * @param dataNascimento a nova data de nascimento
     */
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * Devolve a data de referência usada para ordenação (neste caso, a data de nascimento).
     *
     * @return a data de nascimento
     */
    @Override
    public LocalDate getDataReferencia() {
        return dataNascimento;
    }

    /**
     * Representação textual da pessoa, incluindo nome, ID e data de nascimento.
     *
     * @return uma string formatada com os dados principais
     */
    @Override
    public String toString() {
        return String.format("%s (ID: %d, Nasc.: %s)", nome, id, dataNascimento);
    }
}
