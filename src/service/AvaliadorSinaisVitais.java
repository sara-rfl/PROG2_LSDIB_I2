package service;

import static model.FrequenciaCardiaca.*;
import static model.SaturacaoOxigenio.*;
import static model.Temperatura.*;

import static util.TipoUtil.normalizar;


/**
 * Classe responsável por avaliar e classificar valores numéricos
 * de sinais vitais com base em limites fisiológicos.
 */
public class AvaliadorSinaisVitais {

    /**
     * Classifica um valor de sinal vital com base no tipo especificado.
     * O tipo é normalizado internamente para permitir variações ortográficas.
     *
     * @param tipo  o tipo de sinal vital (ex: "Temperatura", "Frequência Cardíaca").
     * @param valor o valor numérico a ser classificado.
     * @return uma string com a classificação: "Normal", "Atenção" ou "Crítico",
     *         seguida do tipo de sinal vital.
     */
    public static String classificarValor(String tipo, double valor) {
        String tipoNormalizado = normalizar(tipo);

        if (tipoNormalizado.contains("frequencia")) {
            return classificarFrequenciaCardiaca(valor);
        } else if (tipoNormalizado.contains("temperatura")) {
            return classificarTemperatura(valor);
        } else if (tipoNormalizado.contains("saturacao")) {
            return classificarSaturacao(valor);
        } else {
            return "Tipo desconhecido";
        }
    }

    /**
     * Classifica um valor de frequência cardíaca.
     *
     * @param valor valor da frequência cardíaca.
     * @return classificação: "Normal", "Atenção" ou "Crítico" com descrição.
     */
    private static String classificarFrequenciaCardiaca(double valor) {
        if (valor < FC_NORMAL_MIN || valor > FC_ATENCAO_MAX) {
            return "Crítico - Frequência Cardíaca";
        } else if (valor > FC_NORMAL_MAX) {
            return "Atenção - Frequência Cardíaca";
        } else {
            return "Normal - Frequência Cardíaca";
        }
    }

    /**
     * Classifica um valor de temperatura corporal.
     *
     * @param valor valor da temperatura.
     * @return classificação: "Normal", "Atenção" ou "Crítico" com descrição.
     */
    private static String classificarTemperatura(double valor) {
        if (valor < TEMP_NORMAL_MIN || valor > TEMP_ATENCAO_MAX) {
            return "Crítico - Temperatura";
        } else if (valor > TEMP_NORMAL_MAX) {
            return "Atenção - Temperatura";
        } else {
            return "Normal - Temperatura";
        }
    }

    /**
     * Classifica um valor de saturação de oxigénio.
     *
     * @param valor valor da saturação de oxigénio.
     * @return classificação: "Normal", "Atenção" ou "Crítico" com descrição.
     */
    private static String classificarSaturacao(double valor) {
        if (valor < SAT_ATENCAO_MIN) {
            return "Crítico - Saturação de Oxigénio";
        } else if (valor < SAT_NORMAL_MIN) {
            return "Atenção - Saturação de Oxigénio";
        } else {
            return "Normal - Saturação de Oxigénio";
        }
    }
}
