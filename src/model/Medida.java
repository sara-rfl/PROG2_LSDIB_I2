package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Classe abstrata que representa uma medida clínica associada a um paciente,
 * como temperatura, frequência cardíaca ou saturação de oxigénio.
 *
 * Cada medida contém um valor, a data e hora do registo, o paciente avaliado
 * e o técnico de saúde responsável pela medição.
 *
 * Esta classe serve como base para as subclasses específicas de sinais vitais.
 */
public abstract class Medida implements Serializable {

    private static final long serialVersionUID = 1L;

    protected double valor;
    protected LocalDateTime dataHora;
    protected Paciente paciente;
    protected TecnicoSaude tecnico;

    /**
     * Construtor para uma medida genérica.
     *
     * @param valor     valor numérico da medida
     * @param dataHora  data e hora do registo
     * @param paciente  paciente a quem a medida se refere
     * @param tecnico   técnico de saúde que realizou a medição
     */
    public Medida(double valor, LocalDateTime dataHora, Paciente paciente, TecnicoSaude tecnico) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.paciente = paciente;
        this.tecnico = tecnico;
    }

    /**
     * Retorna o valor da medida.
     *
     * @return valor da medida (por exemplo, temperatura em ºC, batimentos por minuto, etc.)
     */
    public double getValor() {
        return valor;
    }

    /**
     * Retorna a data e hora em que a medida foi registada.
     *
     * @return objeto {@link LocalDateTime} representando o momento da medição
     */
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    /**
     * Retorna o paciente associado a esta medida.
     *
     * @return objeto {@link Paciente}
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * Retorna o técnico de saúde que registou esta medida.
     *
     * @return objeto {@link TecnicoSaude}
     */
    public TecnicoSaude getTecnico() {
        return tecnico;
    }

    /**
     * Metodo abstrato que deve ser implementado pelas subclasses para
     * identificar o tipo de sinal vital representado (ex: "Temperatura").
     *
     * @return nome do tipo da medida como string
     */
    public abstract String getTipo();
}
