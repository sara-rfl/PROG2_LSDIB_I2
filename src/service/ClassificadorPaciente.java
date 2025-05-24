package service;

import model.Hospital;
import model.Medida;
import model.Paciente;
import util.ClassificacaoComData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static util.TipoUtil.normalizar;

/**
 * Classe responsável por classificar sinais vitais de pacientes com base
 * em limites fisiológicos e calcular estatísticas relacionadas com essas classificações.
 */
public class ClassificadorPaciente {

    /**
     * Classifica os sinais vitais de um determinado tipo dentro de um intervalo de tempo.
     *
     * @param medidas lista de medidas de um paciente.
     * @param tipo tipo de sinal vital (ex: "Temperatura", "Frequência Cardíaca").
     * @param inicio data/hora de início do intervalo de análise.
     * @param fim data/hora de fim do intervalo de análise.
     * @return lista de classificações com as respetivas datas.
     */
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

    /**
     * Calcula a percentagem de pacientes que apresentam pelo menos
     * um sinal vital classificado como "Crítico" durante um determinado período.
     *
     * @param hospital hospital que contém os pacientes e as respetivas medidas.
     * @param pacientes lista de pacientes a avaliar.
     * @param inicio data/hora de início do intervalo de análise.
     * @param fim data/hora de fim do intervalo de análise.
     * @return percentagem de pacientes com sinais vitais críticos.
     */
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
