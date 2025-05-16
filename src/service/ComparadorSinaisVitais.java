package service;

import model.Hospital;
import model.Medida;
import model.Paciente;
import util.ClassificacaoComData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ComparadorSinaisVitais {

    public static void comparar(Hospital hospital, Paciente paciente,
                                LocalDate dataInicio, LocalDate dataFim) {

        String[] tipos = {"Frequencia Cardiaca", "Temperatura", "Saturação de Oxigénio"};

        List<Medida> medidas = hospital.getMedidasPorPaciente(paciente);

        System.out.println("\nResultado da Classificação:");

        for (String tipo : tipos) {
            List<ClassificacaoComData> classificacoes = ClassificadorPaciente.classificarSinaisVitais(
                    medidas,
                    tipo,
                    dataInicio.atStartOfDay(),
                    dataFim.atTime(23, 59)
            );

            String resultado;
            if (classificacoes.isEmpty()) {
                resultado = "Nenhum valor registado (" + formatarData(dataInicio) + ") --> Nenhum valor registado (" + formatarData(dataFim) + ")";
            } else if (classificacoes.size() == 1) {
                ClassificacaoComData unica = classificacoes.get(0);
                resultado = unica.getClassificacao() + " (" + formatarData(unica.getData()) + ")"
                        + " --> Nenhum valor registado (" + formatarData(dataFim) + ")";
            } else {
                ClassificacaoComData primeira = classificacoes.get(0);
                ClassificacaoComData ultima = classificacoes.get(classificacoes.size() - 1);

                String inicioClass = getClassificacaoBase(primeira.getClassificacao());
                String fimClass = getClassificacaoBase(ultima.getClassificacao());

                resultado = inicioClass + " (" + formatarData(primeira.getData()) + ")"
                        + " --> " + fimClass + " (" + formatarData(ultima.getData()) + ")";
            }


            System.out.println(resultado + " - " + tipo);
        }
    }

    private static String formatarData(LocalDate data) {
        return String.format("%02d/%02d/%04d", data.getDayOfMonth(), data.getMonthValue(), data.getYear());
    }

    private static String getClassificacaoBase(String classificacao) {
        // Se a classificação for "Normal - Temperatura", devolve apenas "Normal"
        if (classificacao.contains(" - ")) {
            return classificacao.split(" - ")[0];
        }
        return classificacao;
    }

}
