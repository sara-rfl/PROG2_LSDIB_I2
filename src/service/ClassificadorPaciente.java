package service;

import manager.FiltroSinaisVitais;
import model.Hospital;
import model.Medida;
import model.Paciente;
import util.ClassificacaoComData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClassificadorPaciente {

    public static List<ClassificacaoComData> classificarSinaisVitais(List<Medida> medidas,
                                                                     String tipo,
                                                                     LocalDateTime inicio,
                                                                     LocalDateTime fim) {
        List<Medida> filtradas = FiltroSinaisVitais.filtrarPorTipoEPeriodo(medidas, tipo, inicio, fim);
        List<ClassificacaoComData> resultado = new ArrayList<>();


        for (Medida m : filtradas) {


            double valor = m.getValor();
            LocalDateTime dataHora = m.getDataHora();
            String classificacao = AvaliadorSinaisVitais.classificarValor(tipo , valor);
            resultado.add(new ClassificacaoComData(classificacao, dataHora.toLocalDate()));
        }

        return resultado;
    }

    public static double calcularPercentagemCriticos(Hospital hospital, List<Paciente> pacientes,
                                                     LocalDateTime inicio, LocalDateTime fim) {
        if (pacientes.isEmpty()) return 0.0;
        int criticos = 0;

        for (Paciente paciente : pacientes) {
            List<Medida> medidas = hospital.getMedidasPorPaciente(paciente);

            for (Medida m : medidas) {
                LocalDateTime data = m.getDataHora();
                if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                    String classificacao = AvaliadorSinaisVitais.classificarValor(m.getTipo(), m.getValor());

                    if (classificacao.toLowerCase().startsWith("crítico")) {
                        criticos++;
                        break;
                    }
                }
            }
        }
        return (criticos * 100.0) / pacientes.size();
    }
}
