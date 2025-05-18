package model;

import java.util.List;

public class ScoreGravidade {

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

    private static int pontuarFc(double valor) {
        if (valor >= FrequenciaCardiaca.FC_NORMAL_MIN && valor <= FrequenciaCardiaca.FC_NORMAL_MAX) return 1;
        if (valor > FrequenciaCardiaca.FC_NORMAL_MAX && valor <= FrequenciaCardiaca.FC_ATENCAO_MAX) return 3;
        return 5;
    }

    private static int pontuarTemp(double valor) {
        if (valor >= Temperatura.TEMP_NORMAL_MIN && valor <= Temperatura.TEMP_NORMAL_MAX) return 1;
        if (valor > Temperatura.TEMP_NORMAL_MAX && valor <= Temperatura.TEMP_ATENCAO_MAX) return 3;
        return 5;
    }

    private static int pontuarSat(double valor) {
        if (valor >= SaturacaoOxigenio.SAT_NORMAL_MIN) return 1;
        if (valor >= SaturacaoOxigenio.SAT_ATENCAO_MIN && valor < SaturacaoOxigenio.SAT_NORMAL_MIN) return 3;
        return 5;
    }
}