package service;

import static model.FrequenciaCardiaca.*;

import static model.SaturacaoOxigenio.*;

import static model.SaturacaoOxigenio.*;

import static model.Temperatura.*;

public class AvaliadorSinaisVitais {

    public static String classificarValor(String tipo, double valor) {
        String tipoNormalizado = tipo
                .toLowerCase()
                .replace("é", "e")
                .replace("ã", "a")
                .replace("ç", "c");

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

    private static String classificarFrequenciaCardiaca(double valor) {
        if (valor < FC_NORMAL_MIN || valor > FC_ATENCAO_MAX) {
            return "Crítico - Frequência Cardíaca";
        } else if (valor > FC_NORMAL_MAX) {
            return "Atenção - Frequência Cardíaca";
        } else {
            return "Normal - Frequência Cardíaca";
        }
    }

    private static String classificarTemperatura(double valor) {
        if (valor < TEMP_NORMAL_MIN || valor > TEMP_ATENCAO_MAX) {
            return "Crítico - Temperatura";
        } else if (valor > TEMP_NORMAL_MAX) {
            return "Atenção - Temperatura";
        } else {
            return "Normal - Temperatura";
        }
    }

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

