package service;

import model.Hospital;
import model.Medida;
import model.Paciente;
import util.ClassificacaoComData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Classe responsável por comparar os sinais vitais de um paciente
 * num determinado intervalo de tempo e apresentar a evolução da classificação.
 */
public class ComparadorSinaisVitais {

    /**
     * Compara os sinais vitais de um paciente entre duas datas e imprime
     * a evolução das classificações para cada tipo de sinal vital.
     *
     * @param hospital hospital que contém os dados dos pacientes.
     * @param paciente paciente selecionado.
     * @param dataInicio data de início do intervalo de análise.
     * @param dataFim data de fim do intervalo de análise.
     */
    public static void comparar(Hospital hospital, Paciente paciente,
                                LocalDate dataInicio, LocalDate dataFim) {

        String[] tipos = {"Frequência Cardíaca", "Temperatura", "Saturação de Oxigénio"};
        List<Medida> medidas = hospital.getMedidasPorPaciente(paciente);

        System.out.println("\nResultado da Classificação:");

        for (String tipo : tipos) {
            List<ClassificacaoComData> classificacoes = obterClassificacoes(tipo, medidas, dataInicio, dataFim);
            String resultado = construirResultado(classificacoes, dataInicio, dataFim);
            System.out.println(resultado + " - " + tipo);
        }
    }

    /**
     * Obtém a lista de classificações de sinais vitais filtradas por tipo e período.
     *
     * @param tipo tipo de sinal vital a analisar.
     * @param medidas lista de medidas do paciente.
     * @param dataInicio data de início do intervalo.
     * @param dataFim data de fim do intervalo.
     * @return lista de classificações com a data correspondente.
     */
    private static List<ClassificacaoComData> obterClassificacoes(String tipo, List<Medida> medidas,
                                                                  LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59);
        return ClassificadorPaciente.classificarSinaisVitais(medidas, tipo, inicio, fim);
    }

    /**
     * Constrói a string que representa a evolução das classificações ao longo do tempo.
     *
     * @param classificacoes lista de classificações obtidas.
     * @param dataInicio data de início do intervalo.
     * @param dataFim data de fim do intervalo.
     * @return string com a descrição da evolução entre as datas.
     */
    private static String construirResultado(List<ClassificacaoComData> classificacoes,
                                             LocalDate dataInicio, LocalDate dataFim) {
        if (classificacoes.isEmpty()) {
            return "Nenhum valor registado (" + formatarData(dataInicio) + ") --> Nenhum valor registado (" + formatarData(dataFim) + ")";
        }

        if (classificacoes.size() == 1) {
            ClassificacaoComData unica = classificacoes.get(0);
            return unica.getClassificacao() + " (" + formatarData(unica.getData()) + ")"
                    + " --> Nenhum valor registado (" + formatarData(dataFim) + ")";
        }

        ClassificacaoComData primeira = classificacoes.get(0);
        ClassificacaoComData ultima = classificacoes.get(classificacoes.size() - 1);
        return getClassificacaoBase(primeira.getClassificacao()) + " (" + formatarData(primeira.getData()) + ")"
                + " --> " + getClassificacaoBase(ultima.getClassificacao()) + " (" + formatarData(ultima.getData()) + ")";
    }

    /**
     * Formata uma data no formato "dd/MM/aaaa".
     *
     * @param data data a formatar.
     * @return string da data formatada.
     */
    private static String formatarData(LocalDate data) {
        return String.format("%02d/%02d/%04d", data.getDayOfMonth(), data.getMonthValue(), data.getYear());
    }

    /**
     * Extrai a parte base da classificação (ex: "Normal" de "Normal - Temperatura").
     *
     * @param classificacao string da classificação completa.
     * @return parte base da classificação (sem tipo).
     */
    private static String getClassificacaoBase(String classificacao) {
        int separador = classificacao.indexOf(" - ");
        return separador != -1 ? classificacao.substring(0, separador) : classificacao;
    }
}
