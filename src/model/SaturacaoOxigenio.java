package model;

import java.time.LocalDateTime;

/**
 * Representa uma medição de saturação de oxigénio no sangue de um paciente.
 * Esta classe estende {@code Medida} e inclui limites de referência para interpretação clínica.
 * Os valores de referência são:
 * <ul>
 *   <li>{@code SAT_NORMAL_MIN} — valor mínimo para considerar a saturação como normal (≥ 95%)</li>
 *   <li>{@code SAT_ATENCAO_MIN} — valor mínimo para considerar como situação de atenção (≥ 90%)</li>
 * </ul>
 * Valores abaixo de 90% são geralmente considerados críticos.
 *
 */
public class SaturacaoOxigenio extends Medida {

    /** Valor mínimo (%) para considerar a saturação como normal. */
    public static final double SAT_NORMAL_MIN = 95.0;

    /** Valor mínimo (%) para considerar a saturação como situação de atenção. */
    public static final double SAT_ATENCAO_MIN = 90.0;

    /**
     * Construtor que cria uma medição de saturação de oxigénio.
     *
     * @param valor    o valor da saturação (%)
     * @param dataHora data e hora da medição
     * @param paciente o paciente a quem a medição foi realizada
     * @param tecnico  o técnico de saúde que realizou a medição
     */
    public SaturacaoOxigenio(double valor, LocalDateTime dataHora, Paciente paciente, TecnicoSaude tecnico) {
        super(valor, dataHora, paciente, tecnico);
    }

    /**
     * Devolve o tipo da medida como string descritiva.
     *
     * @return "Saturação de Oxigénio"
     */
    @Override
    public String getTipo() {
        return "Saturação de Oxigénio";
    }
}
