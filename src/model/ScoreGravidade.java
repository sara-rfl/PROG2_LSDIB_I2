package model;

import java.util.List;

/**
 * Classe utilitária responsável pelo cálculo do score de gravidade de pacientes com base
 * nas últimas medições de sinais vitais.
 * O score indica o grau de gravidade do estado do paciente.
 *
 * O cálculo é feito com base em pesos definidos e em intervalos clínicos,
 * atribuindo uma pontuação a cada sinal vital.
 *
 * As pontuações individuais são combinadas para determinar o score total.
 * A gravidade pode ser classificada como baixa, moderada ou alta.
 */
public class ScoreGravidade {

    /**
     * Limite máximo para a gravidade baixa.
     */
    public static final double GRAVIDADE_LIMITE_BAIXA = 2.0;
    /**
     * Limite máximo para a gravidade moderada.
     */
    public static final double GRAVIDADE_LIMITE_MODERADA = 3.5;

    /**
     * Calcula o score de gravidade de um paciente com base nas suas últimas medições.
     * Os sinais analisados são: frequência cardíaca, temperatura e saturação de oxigénio.
     * Cada sinal é pontuado entre 1 e 5.
     *
     * @param p o paciente a ser avaliado
     * @param h o hospital onde se obtêm as medições do paciente
     * @return o score de gravidade do paciente (entre 1.0 e 5.0)
     */
    public static double scorePaciente(Paciente p, Hospital h) {
        double fcScore = 0, tempScore = 0, satScore = 0;

        Medida fc = h.getUltimaMedidaDoTipo(p, "FrequenciaCardiaca");
        Medida temp = h.getUltimaMedidaDoTipo(p, "Temperatura");
        Medida sat = h.getUltimaMedidaDoTipo(p, "SaturacaoOxigenio");

        if (fc != null) fcScore = pontuarFc(((FrequenciaCardiaca) fc).getValor());
        if (temp != null) tempScore = pontuarTemp(((Temperatura) temp).getValor());
        if (sat != null) satScore = pontuarSat(((SaturacaoOxigenio) sat).getValor());

        return (fcScore * 0.3) + (tempScore * 0.4) + (satScore * 0.3);
    }

    /**
     * Atribui uma pontuação à frequência cardíaca com base nos limites definidos.
     *
     * @param valor valor da frequência cardíaca (bpm)
     * @return 1 se normal, 3 se em atenção, 5 se crítica
     */
    private static int pontuarFc(double valor) {
        if (valor >= FrequenciaCardiaca.FC_NORMAL_MIN && valor <= FrequenciaCardiaca.FC_NORMAL_MAX) return 1;
        if (valor > FrequenciaCardiaca.FC_NORMAL_MAX && valor <= FrequenciaCardiaca.FC_ATENCAO_MAX) return 3;
        return 5;
    }

    /**
     * Atribui uma pontuação à temperatura corporal com base nos limites definidos.
     *
     * @param valor valor da temperatura (ºC)
     * @return 1 se normal, 3 se em atenção, 5 se crítica
     */
    private static int pontuarTemp(double valor) {
        if (valor >= Temperatura.TEMP_NORMAL_MIN && valor <= Temperatura.TEMP_NORMAL_MAX) return 1;
        if (valor > Temperatura.TEMP_NORMAL_MAX && valor <= Temperatura.TEMP_ATENCAO_MAX) return 3;
        return 5;
    }

    /**
     * Atribui uma pontuação à saturação de oxigénio com base nos limites definidos.
     *
     * @param valor valor da saturação de oxigénio (%)
     * @return 1 se normal, 3 se em atenção, 5 se crítica
     */
    private static int pontuarSat(double valor) {
        if (valor >= SaturacaoOxigenio.SAT_NORMAL_MIN) return 1;
        if (valor >= SaturacaoOxigenio.SAT_ATENCAO_MIN && valor < SaturacaoOxigenio.SAT_NORMAL_MIN) return 3;
        return 5;
    }

    /**
     * Devolve o paciente com o maior score de gravidade numa lista de pacientes.
     *
     * @param pacientes lista de pacientes a analisar
     * @param hospital hospital onde estão armazenadas as medições
     * @return o paciente com maior score de gravidade, ou null se a lista estiver vazia
     */
    public static Paciente pacienteMaisGrave(List<Paciente> pacientes, Hospital hospital) {
        Paciente maisGrave = null;
        double maiorScore = 0;

        for (Paciente p : pacientes) {
            double score = scorePaciente(p, hospital);
            if (score > maiorScore) {
                maiorScore = score;
                maisGrave = p;
            }
        }
        return maisGrave;
    }

    /**
     * Interpreta o score de gravidade como um nível textual:
     * Baixa, Moderada ou Alta.
     *
     * @param score o valor do score de gravidade
     * @return uma string com o nível de gravidade correspondente
     */
    public static String interpretarScore(double score) {
        if (score <= GRAVIDADE_LIMITE_BAIXA) return "Gravidade Baixa";
        if (score <= GRAVIDADE_LIMITE_MODERADA) return "Gravidade Moderada";
        else return "Gravidade Alta";
    }
}