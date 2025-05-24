package service;

import model.Hospital;
import model.Medida;
import model.Paciente;
import util.ClassificacaoComData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static util.TipoUtil.normalizar;

public class ClassificadorPaciente {

    public static List<ClassificacaoComData> classificarSinaisVitais(List<Medida> medidas,
                                                                     String tipo,
                                                                     LocalDateTime inicio,
                                                                     LocalDateTime fim) {
        List<Medida> filtradas = new ArrayList<>();

        for (Medida m : medidas) {
            if (normalizar(m.getTipo()).equals(normalizar(tipo)) &&
                    !m.getDataHora().isBefore(inicio) &&
                    !m.getDataHora().isAfter(fim)) {
                filtradas.add(m);
            }
        }

        List<ClassificacaoComData> resultado = new ArrayList<>();
        for (Medida m : filtradas) {
            double valor = m.getValor();
            LocalDateTime dataHora = m.getDataHora();
            String classificacao = AvaliadorSinaisVitais.classificarValor(tipo, valor);
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
            boolean temCritico = false;

            for (Medida m : medidas) {
                LocalDateTime data = m.getDataHora();
                if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                    String classificacao = AvaliadorSinaisVitais.classificarValor(m.getTipo(), m.getValor());
                    if (classificacao.toLowerCase().startsWith("crítico")) {
                        temCritico = true;
                    }
                }
            }

            if (temCritico) criticos++;
        }

        return (criticos * 100.0) / pacientes.size();
    }
}
