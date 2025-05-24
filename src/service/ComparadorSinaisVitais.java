package service;

import model.Hospital;
import model.Medida;
import model.Paciente;
import util.ClassificacaoComData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static util.TipoUtil.normalizar;

public class ComparadorSinaisVitais {

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

    private static List<ClassificacaoComData> obterClassificacoes(String tipo, List<Medida> medidas,
                                                                  LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59);
        return ClassificadorPaciente.classificarSinaisVitais(medidas, tipo, inicio, fim);
    }

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

    private static String formatarData(LocalDate data) {
        return String.format("%02d/%02d/%04d", data.getDayOfMonth(), data.getMonthValue(), data.getYear());
    }

    private static String getClassificacaoBase(String classificacao) {
        int separador = classificacao.indexOf(" - ");
        return separador != -1 ? classificacao.substring(0, separador) : classificacao;
    }
}
