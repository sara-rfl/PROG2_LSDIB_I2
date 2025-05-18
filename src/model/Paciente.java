package model;

import interfaces.Classificavel;
import java.time.LocalDate;

/**
 * Representa um paciente com dados pessoais e características físicas.
 * A classificação e os registos de sinais vitais estão externalizados (guardados no Hospital).
 */
public class Paciente extends Pessoa implements Comparable<Paciente>, Classificavel {

    private double altura;
    private double peso;

    public Paciente(String nome, LocalDate dataNascimento, double altura, double peso) {
        super(nome, dataNascimento);
        this.altura = altura;
        this.peso = peso;
    }

    public Paciente(String nome, LocalDate dataNascimento, double altura, double peso, int id) {
        super(nome, dataNascimento, id);  // chama o novo construtor
        this.altura = altura;
        this.peso = peso;
    }


    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Devolve a classificação geral do paciente com base nos últimos valores de sinais vitais.
     * Nota: depende do ClassificadorPaciente que consulta as medidas no Hospital.
     */
    @Override
    public String getClassificacao() {
        return "Classificação não disponível diretamente. Consultar via ClassificadorPaciente.";
    }

    /**
     * Compara dois pacientes por data de nascimento.
     */
    @Override
    public int compareTo(Paciente outro) {
        return this.getDataNascimento().compareTo(outro.getDataNascimento());
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Altura: %.2f m | Peso: %.1f kg", altura, peso);
    }
}
