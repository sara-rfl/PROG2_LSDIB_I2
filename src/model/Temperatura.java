package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Representa uma medida de temperatura corporal de um paciente.
 * Estende a classe abstrata {@link Medida} e inclui limites para classificação
 * dos valores como "Normal", "Atenção" ou "Crítico".
 * Esta classe é serializável, permitindo que os dados sejam guardados.
 */
public class Temperatura extends Medida implements Serializable {

    /**
     * Limite inferior da temperatura considerada normal (em ºC).
     */
    public static final double TEMP_NORMAL_MIN = 36.0;

    /**
     * Limite superior da temperatura considerada normal (em ºC).
     */
    public static final double TEMP_NORMAL_MAX = 37.5;

    /**
     * Limite máximo para estado de atenção. Acima deste valor considera-se crítico (em ºC).
     */
    public static final double TEMP_ATENCAO_MAX = 38.5;

    /**
     * Construtor da classe Temperatura.
     *
     * @param valor    valor da temperatura registada (em ºC)
     * @param dataHora data e hora do registo
     * @param paciente paciente ao qual a medida pertence
     * @param tecnico  técnico de saúde que registou a medida
     */
    public Temperatura(double valor, LocalDateTime dataHora, Paciente paciente, TecnicoSaude tecnico) {
        super(valor, dataHora, paciente, tecnico);
    }

    /**
     * Retorna o tipo da medida, neste caso "Temperatura".
     *
     * @return a string "Temperatura"
     */
    @Override
    public String getTipo() {
        return "Temperatura";
    }
}
