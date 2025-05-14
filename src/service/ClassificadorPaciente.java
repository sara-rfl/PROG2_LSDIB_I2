package service;

import model.*;

import java.time.LocalDateTime;
import java.util.List;

public class ClassificadorPaciente {

    public static String classificarPacienteNoPeriodo(Hospital hospital, Paciente paciente, LocalDateTime inicio, LocalDateTime fim) {
        List<Medida> medidas = hospital.getMedidasPorPaciente(paciente);

        String fcEstado = classificarTipo("Frequência Cardíaca", medidas, inicio, fim);
        String tempEstado = classificarTipo("Temperatura", medidas, inicio, fim);
        String satEstado = classificarTipo("Saturação de Oxigénio", medidas, inicio, fim);

        if (fcEstado.equals("Crítico") || tempEstado.equals("Crítico") || satEstado.equals("Crítico"))
            return "Crítico";
        if (fcEstado.equals("Atenção") || tempEstado.equals("Atenção") || satEstado.equals("Atenção"))
            return "Atenção";
        return "Normal";
    }

    private static String classificarTipo(String tipo, List<Medida> medidas, LocalDateTime inicio, LocalDateTime fim) {
        for (Medida m : medidas) {
            if (!m.getTipo().equals(tipo)) continue;
            if (m.getDataHora().isBefore(inicio) || m.getDataHora().isAfter(fim)) continue;

            double v = m.getValor();

            if (tipo.equals("Frequência Cardíaca")) {
                if (v < FrequenciaCardiaca.FC_NORMAL_MIN || v > FrequenciaCardiaca.FC_ATENCAO_MAX) return "Crítico";
                else if (v > FrequenciaCardiaca.FC_NORMAL_MAX) return "Atenção";
            } else if (tipo.equals("Temperatura")) {
                if (v < Temperatura.TEMP_NORMAL_MIN || v > Temperatura.TEMP_ATENCAO_MAX) return "Crítico";
                else if (v > Temperatura.TEMP_NORMAL_MAX) return "Atenção";
            } else if (tipo.equals("Saturação de Oxigénio")) {
                if (v < SaturacaoOxigenio.SAT_ATENCAO_MIN) return "Crítico";
                else if (v < SaturacaoOxigenio.SAT_NORMAL_MIN) return "Atenção";
            }
        }
        return "Normal";
    }
}
