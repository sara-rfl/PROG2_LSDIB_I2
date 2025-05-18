package manager;

import model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PeriodoAnalise {

    /**
     * Solicita ao utilizador um período de análise válido.
     */
    public static LocalDate[] obterPeriodoDeAnalise(Scanner scanner) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataInicio = null;
        LocalDate dataFim = null;
        boolean datasValidas = false;

        while (!datasValidas) {
            System.out.print("Introduza a data de início (dd/mm/aaaa): ");
            String dataInicioStr = scanner.nextLine();
            System.out.print("Introduza a data de fim (dd/mm/aaaa): ");
            String dataFimStr = scanner.nextLine();

            dataInicio = parseDataBasica(dataInicioStr, formatter);
            dataFim = parseDataBasica(dataFimStr, formatter);

            if (dataInicio != null && dataFim != null && !dataFim.isBefore(dataInicio)) {
                datasValidas = true;
            } else {
                System.out.println("Datas inválidas ou em ordem incorreta. Tente novamente.");
            }
        }

        return new LocalDate[]{dataInicio, dataFim};
    }

    public static String obterIntervaloDeRegistos(Hospital hospital, List<Paciente> pacientes) {
        LocalDateTime min = null;
        LocalDateTime max = null;

        for (Paciente p : pacientes) {
            List<Medida> medidas = hospital.getMedidasPorPaciente(p);
            for (Medida m : medidas) {
                LocalDateTime data = m.getDataHora();
                if (min == null || data.isBefore(min)) min = data;
                if (max == null || data.isAfter(max)) max = data;
            }
        }

        if (min == null || max == null) return "Sem registos";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return min.toLocalDate().format(formatter) + " a " + max.toLocalDate().format(formatter);
    }


    /**
     * Mostra intervalo de registos e pede um período com dados para o paciente.
     */
    public static LocalDate[] selecionarPeriodoDeAnalisePaciente(Scanner scanner, Hospital hospital, Paciente paciente) {
        System.out.println("\n|| Registos disponíveis para o paciente " + paciente.getId() + ": ||");
        String intervalo = obterIntervaloDeRegistos(hospital, paciente);
        System.out.println("Intervalo disponível: " + intervalo + "\n");

        while (true) {
            LocalDate[] periodo = obterPeriodoDeAnalise(scanner);
            if (temMedidasNoIntervalo(hospital, paciente, periodo[0], periodo[1])) {
                return periodo;
            } else {
                System.out.println("Sem registos nesse intervalo. Tente novamente.");
            }
        }
    }

    /**
     * Mostra intervalos e seleciona um período válido para um grupo de pacientes.
     */
    public static LocalDate[] selecionarPeriodoDeAnaliseGrupo(Scanner scanner, Hospital hospital, List<Paciente> pacientes) {
        System.out.println("\n|| Registos dos pacientes selecionados ||");
        for (Paciente p : pacientes) {
            String intervalo = obterIntervaloDeRegistos(hospital, p);
            System.out.println("ID " + p.getId() + ": " + intervalo);
        }
        while (true) {
            LocalDate[] periodo = obterPeriodoDeAnalise(scanner);
            for (Paciente p : pacientes) {
                if (temMedidasNoIntervalo(hospital, p, periodo[0], periodo[1])) {
                    return periodo;
                }
            }
            System.out.println("Nenhum paciente tem dados nesse intervalo. Tente novamente.");
        }
    }

    /**
     * Verifica se há medidas no intervalo para um dado paciente.
     */
    public static boolean temMedidasNoIntervalo(Hospital hospital, Paciente paciente, LocalDate inicio, LocalDate fim) {
        List<Medida> medidas = hospital.getMedidasPorPaciente(paciente);
        for (Medida m : medidas) {
            LocalDate data = m.getDataHora().toLocalDate();
            if (!data.isBefore(inicio) && !data.isAfter(fim)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Mostra o intervalo total de datas com medidas registadas no hospital para o paciente.
     */
    public static String obterIntervaloDeRegistos(Hospital hospital, Paciente paciente) {
        List<Medida> medidas = hospital.getMedidasPorPaciente(paciente);

        if (medidas.isEmpty()) return "Sem registos";

        LocalDateTime min = null;
        LocalDateTime max = null;

        for (Medida m : medidas) {
            LocalDateTime dataHora = m.getDataHora();
            if (min == null || dataHora.isBefore(min)) min = dataHora;
            if (max == null || dataHora.isAfter(max)) max = dataHora;
        }

        // Formata apenas a parte da data para mostrar ao utilizador
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return min.toLocalDate().format(formatter) + " a " + max.toLocalDate().format(formatter);
    }

    public static LocalDate parseDataBasica(String input, DateTimeFormatter formatter) {
        if (!formatoValido(input)) return null;

        String[] partes = input.split("/");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int ano = Integer.parseInt(partes[2]);

        if (!dataValida(dia, mes, ano)) return null;

        return LocalDate.of(ano, mes, dia);
    }

    public static boolean formatoValido(String input) {
        if (input == null) return false;
        String[] partes = input.split("/");
        if (partes.length != 3) return false;

        int[] tamanhos = {2, 2, 4};
        for (int i = 0; i < 3; i++) {
            if (partes[i].length() != tamanhos[i] || !partes[i].chars().allMatch(Character::isDigit))
                return false;
        }
        return true;
    }

    public static boolean dataValida(int dia, int mes, int ano) {
        if (ano < 1960 || ano > 2026) return false;
        if (mes < 1 || mes > 12) return false;

        int[] diasPorMes = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (mes == 2 && isAnoBissexto(ano)) return dia >= 1 && dia <= 29;
        return dia >= 1 && dia <= diasPorMes[mes - 1];
    }

    public static boolean isAnoBissexto(int ano) {
        return (ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0);
    }
}
