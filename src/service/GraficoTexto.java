package service;

import manager.GestorPacientes;
import manager.PeriodoAnalise;
import model.Hospital;
import model.Medida;
import model.Paciente;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Classe responsável por gerar representações textuais (gráficos em texto)
 * dos sinais vitais dos pacientes, incluindo médias, últimas medições
 * e grupos de medições reais.
 */
public class GraficoTexto {

    private static final String[] TIPOS = {"Frequência Cardíaca", "Temperatura", "Saturação de Oxigénio"};
    private static final String[] NOMES = {"Frequência cardíaca (bpm)", "Temperatura corporal (ºC)", "Saturação de oxigénio (%)"};
    private static final double[] MAXIMOS = {220.0, 45.0, 100.0};

    /**
     * Apresenta uma barra em texto proporcional ao valor fornecido,
     * com base no valor máximo possível.
     *
     * @param titulo título a apresentar antes da barra.
     * @param valor valor atual do sinal vital.
     * @param maximo valor máximo para o sinal vital.
     */
    public static void mostrarBarra(String titulo, double valor, double maximo) {
        int comprimento = (int) ((valor / maximo) * 10); // Cada * = 10%
        StringBuilder barra = new StringBuilder();
        for (int i = 0; i < comprimento; i++) barra.append("*");
        System.out.printf("%s: %.1f  %s\n", titulo, valor, barra);
    }

    /**
     * Mostra um gráfico com as médias dos sinais vitais de um paciente
     * dentro de um intervalo de tempo selecionado pelo utilizador.
     *
     * @param scanner objeto Scanner para entrada do utilizador.
     * @param hospital hospital com os dados dos pacientes.
     */
    public static void mostrarGraficoMediasPaciente(Scanner scanner, Hospital hospital) {
        Paciente paciente = GestorPacientes.selecionarPaciente(scanner, hospital);
        LocalDate[] datas = PeriodoAnalise.selecionarPeriodoDeAnalisePaciente(scanner, hospital, paciente);
        LocalDateTime inicio = datas[0].atStartOfDay();
        LocalDateTime fim = datas[1].isEqual(LocalDate.now())
                ? LocalDateTime.of(datas[1], LocalTime.now())
                : datas[1].atTime(23, 59);

        List<Medida> todas = hospital.getMedidasPorPaciente(paciente);

        for (int i = 0; i < TIPOS.length; i++) {
            List<Medida> medidas = filtrarPorTipoEPeriodo(todas, TIPOS[i], inicio, fim);
            if (medidas.isEmpty()) {
                System.out.printf("%s: Sem registos no intervalo\n", NOMES[i]);
            } else {
                double soma = 0;
                for (Medida m : medidas) soma += m.getValor();
                mostrarBarra(NOMES[i], soma / medidas.size(), MAXIMOS[i]);
            }
        }
    }

    /**
     * Mostra um resumo gráfico das últimas medições de sinais vitais
     * para um determinado paciente.
     *
     * @param paciente paciente selecionado.
     * @param hospital hospital com os dados das medições.
     */

    public static void mostrarGraficoUltimasMedidas(Paciente paciente, Hospital hospital) {
        System.out.println("\nResumo gráfico dos sinais vitais registados:");
        List<Medida> todas = hospital.getMedidasPorPaciente(paciente);

        for (int i = 0; i < TIPOS.length; i++) {
            Medida ultima = null;
            for (Medida m : todas) {
                if (m.getTipo().equalsIgnoreCase(TIPOS[i]) &&
                        (ultima == null || m.getDataHora().isAfter(ultima.getDataHora()))) {
                    ultima = m;
                }
            }
            if (ultima != null) {
                mostrarBarra(NOMES[i], ultima.getValor(), MAXIMOS[i]);
            } else {
                System.out.printf("%s: Nenhum registo\n%s: -\n", NOMES[i], NOMES[i]);
            }
        }
    }
    /**
     * Mostra grupos de valores reais de sinais vitais, organizados em blocos paralelos,
     * permitindo a comparação temporal entre medições de diferentes tipos.
     *
     * @param scanner objeto Scanner para entrada do utilizador.
     * @param hospital hospital com os dados dos pacientes.
     */
    public static void mostrarGruposValoresReais(Scanner scanner, Hospital hospital) {
        Paciente paciente = GestorPacientes.selecionarPaciente(scanner, hospital);
        List<Medida> todas = hospital.getMedidasPorPaciente(paciente);

        List<Medida>[] separados = new List[]{new ArrayList<>(), new ArrayList<>(), new ArrayList<>()};

        for (Medida m : todas) {
            for (int i = 0; i < TIPOS.length; i++) {
                if (m.getTipo().trim().equalsIgnoreCase(TIPOS[i]) ||
                        (i == 2 && m.getTipo().trim().equalsIgnoreCase("saturacao de oxigenio"))) {
                    separados[i].add(m);
                }
            }
        }

        int grupos = Math.max(separados[0].size(), Math.max(separados[1].size(), separados[2].size()));
        for (int i = 0; i < grupos; i++) {
            Medida[] grupo = new Medida[3];
            for (int j = 0; j < 3; j++) {
                grupo[j] = i < separados[j].size() ? separados[j].get(i) : null;
            }
            mostrarGrupo(grupo, i + 1);
        }
    }

    /**
     * Mostra as medições de um grupo, com as respetivas barras e intervalo de datas.
     *
     * @param grupo array de medidas, uma por tipo de sinal vital.
     * @param numero número do grupo a apresentar.
     */

    private static void mostrarGrupo(Medida[] grupo, int numero) {
        List<LocalDateTime> datas = new ArrayList<>();
        for (Medida m : grupo) if (m != null) datas.add(m.getDataHora());

        if (!datas.isEmpty()) {
            LocalDate inicio = datas.get(0).toLocalDate(), fim = inicio;
            for (LocalDateTime dt : datas) {
                LocalDate d = dt.toLocalDate();
                if (d.isBefore(inicio)) inicio = d;
                if (d.isAfter(fim)) fim = d;
            }
            System.out.printf("%nGrupo %d: %s a %s%n", numero,
                    inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    fim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            System.out.printf("%nGrupo %d: Sem intervalo válido%n", numero);
        }

        for (int i = 0; i < 3; i++) {
            if (grupo[i] != null) {
                mostrarBarra(NOMES[i], grupo[i].getValor(), MAXIMOS[i]);
            } else {
                System.out.printf("%s: Nenhuma nova medição encontrada\n%s: -\n", NOMES[i], NOMES[i]);
            }
        }
        System.out.println("---");
    }

    /**
     * Filtra uma lista de medições por tipo e intervalo temporal.
     *
     * @param medidas lista completa de medições.
     * @param tipo tipo de sinal vital a filtrar.
     * @param inicio início do intervalo.
     * @param fim fim do intervalo.
     * @return lista de medidas filtradas.
     */
    private static List<Medida> filtrarPorTipoEPeriodo(List<Medida> medidas, String tipo, LocalDateTime inicio, LocalDateTime fim) {
        List<Medida> filtradas = new ArrayList<>();
        for (Medida m : medidas) {
            if (m.getTipo().equalsIgnoreCase(tipo) &&
                    !m.getDataHora().isBefore(inicio) &&
                    !m.getDataHora().isAfter(fim)) {
                filtradas.add(m);
            }
        }
        return filtradas;
    }
}
