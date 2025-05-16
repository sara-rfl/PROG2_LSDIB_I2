package service;

import model.Hospital;
import model.Medida;
import model.Paciente;
import util.ClassificacaoComData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClassificadorSinaisVitais {

    public static List<Double> obterValoresFiltrados(Hospital hospital, Paciente paciente,
                                                     String tipo, LocalDate inicio, LocalDate fim) {

        List<Medida> medidas = hospital.getMedidasPorPaciente(paciente);
        List<ClassificacaoComData> classificacoes = ClassificadorPaciente.classificarSinaisVitais(
                medidas, tipo, inicio.atStartOfDay(), fim.atTime(23, 59, 59));

        List<Double> valoresNumericos = new ArrayList<>();
        for (ClassificacaoComData c : classificacoes) {
            String classe = c.getClassificacao().toLowerCase();
            if (classe.contains("crítico")) valoresNumericos.add(3.0);
            else if (classe.contains("atenção")) valoresNumericos.add(2.0);
            else valoresNumericos.add(1.0);
        }

        return valoresNumericos;
    }
}

