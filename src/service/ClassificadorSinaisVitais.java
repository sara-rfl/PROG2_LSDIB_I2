package service;

import model.FrequenciaCardiaca;
import model.Temperatura;
import model.SaturacaoOxigenio;

/**
 * Classe auxiliar responsável por classificar individualmente
 * valores de sinais vitais como Frequência Cardíaca, Temperatura
 * e Saturação de Oxigénio.
 *
 * Cada método devolve uma String com o nível de risco.
 */
public class ClassificadorSinaisVitais {

    public static String classificarFrequenciaCardiaca(double valor) {
        if (valor < FrequenciaCardiaca.FC_NORMAL_MIN || valor > FrequenciaCardiaca.FC_ATENCAO_MAX) {
            return "Crítico - Frequência Cardíaca";
        } else if (valor > FrequenciaCardiaca.FC_NORMAL_MAX) {
            return "Atenção - Frequência Cardíaca";
        } else {
            return "Normal - Frequência Cardíaca";
        }
    }

    public static String classificarTemperatura(double valor) {
        if (valor < Temperatura.TEMP_NORMAL_MIN || valor > Temperatura.TEMP_ATENCAO_MAX) {
            return "Crítico - Temperatura";
        } else if (valor > Temperatura.TEMP_NORMAL_MAX) {
            return "Atenção - Temperatura";
        } else {
            return "Normal - Temperatura";
        }
    }

    public static String classificarSaturacao(double valor) {
        if (valor < SaturacaoOxigenio.SAT_ATENCAO_MIN) {
            return "Crítico - Saturação de Oxigénio";
        } else if (valor < SaturacaoOxigenio.SAT_NORMAL_MIN) {
            return "Atenção - Saturação de Oxigénio";
        } else {
            return "Normal - Saturação de Oxigénio";
        }
    }
}
