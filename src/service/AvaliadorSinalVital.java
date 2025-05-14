package service;

import model.*;
import util.ClassificacaoComData;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe responsável por classificar sinais vitais em determinada data
 * com base nas medidas registadas no hospital.
 */
public class AvaliadorSinalVital {

    /**
     * Classifica o primeiro valor do tipo de medida encontrado na data ou após a data-alvo.
     *
     * @param medidas Lista de medidas de um paciente
     * @param alvo    Data-alvo para procurar o primeiro registo
     * @param tipo    Tipo de medida: "Frequência Cardíaca", "Temperatura", "Saturação de Oxigénio"
     * @return Objeto com classificação e data, ou mensagem de ausência
     */
    public static ClassificacaoComData classificarValorEmData(List<Medida> medidas, LocalDate alvo, String tipo) {
        for (Medida m : medidas) {
            LocalDate data = m.getDataHora().toLocalDate();

            // Encontrar primeiro registo igual ou após a data-alvo
            if (!data.isBefore(alvo)) {
                if (tipo.equals("Frequência Cardíaca") && m instanceof FrequenciaCardiaca) {
                    return new ClassificacaoComData(classificarFrequenciaCardiaca(m.getValor()), data);
                } else if (tipo.equals("Temperatura") && m instanceof Temperatura) {
                    return new ClassificacaoComData(classificarTemperatura(m.getValor()), data);
                } else if (tipo.equals("Saturação de Oxigénio") && m instanceof SaturacaoOxigenio) {
                    return new ClassificacaoComData(classificarSaturacao(m.getValor()), data);
                }
            }
        }

        return new ClassificacaoComData("Nenhum valor registado", alvo);
    }

    public static String classificarFrequenciaCardiaca(double valor) {
        if (valor < FrequenciaCardiaca.FC_NORMAL_MIN || valor > FrequenciaCardiaca.FC_ATENCAO_MAX) return "Crítico";
        if (valor > FrequenciaCardiaca.FC_NORMAL_MAX) return "Atenção";
        return "Normal";
    }

    public static String classificarTemperatura(double valor) {
        if (valor < Temperatura.TEMP_NORMAL_MIN || valor > Temperatura.TEMP_ATENCAO_MAX) return "Crítico";
        if (valor > Temperatura.TEMP_NORMAL_MAX) return "Atenção";
        return "Normal";
    }

    public static String classificarSaturacao(double valor) {
        if (valor < SaturacaoOxigenio.SAT_ATENCAO_MIN) return "Crítico";
        if (valor < SaturacaoOxigenio.SAT_NORMAL_MIN) return "Atenção";
        return "Normal";
    }
}

