package model;

import interfaces.Classificavel;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Representa um paciente com dados pessoais e características físicas (altura e peso).
 * As medições e a classificação clínica associada ao paciente são geridas externamente,
 * por exemplo, pela classe {@code Hospital} e um sistema de classificação como {@code ClassificadorPaciente}.
 * Esta classe implementa {@code Comparable<Paciente>} para permitir ordenação por data de nascimento,
 * e {@code Classificavel} para permitir a obtenção de uma classificação (embora aqui esteja apenas como placeholder).
 * Esta classe é serializável, permitindo que os dados sejam guardados.
 */
public class Paciente extends Pessoa implements Comparable<Paciente>, Classificavel, Serializable {

    /** Altura do paciente, em metros. */
    private double altura;

    /** Peso do paciente, em quilogramas. */
    private double peso;

    /**
     * Construtor principal para criar um paciente com nome, data de nascimento, altura e peso.
     *
     * @param nome           o nome do paciente
     * @param dataNascimento a data de nascimento do paciente
     * @param altura         a altura em metros
     * @param peso           o peso em quilogramas
     */
    public Paciente(String nome, LocalDate dataNascimento, double altura, double peso) {
        super(nome, dataNascimento);
        this.altura = altura;
        this.peso = peso;
    }

    /**
     * Construtor alternativo que permite definir também o ID do paciente.
     *
     * @param nome           o nome do paciente
     * @param dataNascimento a data de nascimento do paciente
     * @param altura         a altura em metros
     * @param peso           o peso em quilogramas
     * @param id             o identificador único do paciente
     */
    public Paciente(String nome, LocalDate dataNascimento, double altura, double peso, int id) {
        super(nome, dataNascimento, id);
        this.altura = altura;
        this.peso = peso;
    }

    /**
     * Obtém a altura do paciente.
     *
     * @return a altura em metros
     */
    public double getAltura() {
        return altura;
    }

    /**
     * Define a altura do paciente.
     *
     * @param altura a nova altura em metros
     */
    public void setAltura(double altura) {
        this.altura = altura;
    }

    /**
     * Obtém o peso do paciente.
     *
     * @return o peso em quilogramas
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Define o peso do paciente.
     *
     * @param peso o novo peso em quilogramas
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Devolve a classificação geral do paciente com base nos últimos valores de sinais vitais.
     * <p>
     * Esta implementação devolve uma string indicativa, já que a lógica real está no {@code ClassificadorPaciente}.
     * </p>
     *
     * @return uma mensagem indicativa da classificação externa
     */
    @Override
    public String getClassificacao() {
        return "Classificação não disponível diretamente. Consultar via ClassificadorPaciente.";
    }

    /**
     * Compara este paciente com outro com base na data de nascimento (ordem cronológica).
     *
     * @param outro o outro paciente a comparar
     * @return valor negativo se este paciente for mais velho, positivo se mais novo, 0 se forem iguais
     */
    @Override
    public int compareTo(Paciente outro) {
        return this.getDataNascimento().compareTo(outro.getDataNascimento());
    }

    /**
     * Representação textual do paciente, incluindo nome, data de nascimento, altura e peso.
     *
     * @return uma string com a informação formatada do paciente
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Altura: %.2f m | Peso: %.1f kg", altura, peso);
    }
}
