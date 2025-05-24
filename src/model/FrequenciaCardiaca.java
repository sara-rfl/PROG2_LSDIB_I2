package model;

import java.time.LocalDateTime;

/**
 * Representa uma medida de frequência cardíaca de um paciente,
 * armazenando o valor, a data/hora do registo, e os envolvidos (paciente e técnico).
 * Estende a classe abstrata {@link Medida} e define os limites associados
 * à classificação da frequência cardíaca.
 */
public class FrequenciaCardiaca extends Medida {

    /**
     * Limite inferior para frequência cardíaca considerada normal (em bpm).
     */
    public static final double FC_NORMAL_MIN = 60.0;

    /**
     * Limite superior para frequência cardíaca considerada normal (em bpm).
     */
    public static final double FC_NORMAL_MAX = 100.0;

    /**
     * Limite superior para frequência cardíaca na categoria "Atenção" (em bpm).
     * Valores acima disto são considerados críticos.
     */
    public static final double FC_ATENCAO_MAX = 120.0;

    /**
     * Construtor que cria uma instância de frequência cardíaca.
     *
     * @param valor    valor medido da frequência cardíaca (em bpm)
     * @param dataHora data e hora do registo
     * @param paciente paciente ao qual a medida pertence
     * @param tecnico  técnico de saúde que registou a medida
     */
    public FrequenciaCardiaca(double valor, LocalDateTime dataHora, Paciente paciente, TecnicoSaude tecnico) {
        super(valor, dataHora, paciente, tecnico);
    }

    /**
     * Retorna o tipo da medida.
     *
     * @return a string "Frequência Cardíaca"
     */
    @Override
    public String getTipo() {
        return "Frequência Cardíaca";
    }
}
