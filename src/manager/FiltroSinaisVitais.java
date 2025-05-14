package manager;

import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária para filtrar sinais vitais com base no tipo e intervalo de datas,
 * usando o armazenamento centralizado do Hospital.
 */
public class FiltroSinaisVitais {

    /**
     * Filtra os valores de sinais vitais de um paciente, com base no tipo e no intervalo de datas.
     *
     * @param hospital hospital onde estão registadas todas as medidas
     * @param paciente paciente cujos valores queremos analisar
     * @param tipo tipo do sinal vital ("Frequência Cardíaca", "Temperatura", "Saturação de Oxigénio")
     * @param inicio data de início (inclusive)
     * @param fim data de fim (inclusive)
     * @return lista de valores desse tipo dentro do intervalo
     */
    public static List<Double> obterValoresFiltrados(Hospital hospital, Paciente paciente, String tipo, LocalDate inicio, LocalDate fim) {
        List<Double> resultados = new ArrayList<>();
        List<Medida> medidas = hospital.getMedidasPorPaciente(paciente);

        for (Medida m : medidas) {
            LocalDate data = m.getDataHora().toLocalDate();
            boolean dentroDoIntervalo = !data.isBefore(inicio) && !data.isAfter(fim);

            if (dentroDoIntervalo) {
                if (tipo.equals("Frequência Cardíaca") && m instanceof FrequenciaCardiaca) {
                    resultados.add(m.getValor());
                } else if (tipo.equals("Temperatura") && m instanceof Temperatura) {
                    resultados.add(m.getValor());
                } else if (tipo.equals("Saturação de Oxigénio") && m instanceof SaturacaoOxigenio) {
                    resultados.add(m.getValor());
                }
            }
        }

        return resultados;
    }
}
