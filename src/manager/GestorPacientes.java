package manager;

import model.Hospital;
import model.Paciente;
import service.Estatistica;
import ui.Submenus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorPacientes {

    public static Paciente selecionarPaciente(Scanner scanner, List<Paciente> pacientes) {
        mostrarLista(pacientes);

        System.out.print("Introduza o ID do paciente: ");
        int idEscolhido = scanner.nextInt();
        scanner.nextLine();

        for (Paciente p : pacientes) {
            if (p.getId() == idEscolhido) {
                return p;
            }
        }

        System.out.println("Paciente com ID " + idEscolhido + " não encontrado.");
        return null;
    }

    public static List<Paciente> selecionarGrupoPacientes(Scanner scanner, List<Paciente> pacientes) {
        System.out.println("Selecione um grupo de pacientes (IDs separados por espaço):");
        mostrarLista(pacientes);
        System.out.print("Introduza os IDs: ");
        String[] ids = scanner.nextLine().split(" ");
        List<Paciente> selecionados = new ArrayList<>();
        for (String idStr : ids) {
            try {
                int id = Integer.parseInt(idStr);
                for (Paciente paciente : pacientes) {
                    if (paciente.getId() == id) {
                        selecionados.add(paciente);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("ID inválido: " + idStr);
            }
        }
        if (selecionados.isEmpty()) {
            System.out.println("Nenhum paciente encontrado.");
        }
        return selecionados;
    }

    public static void mostrarLista(List<Paciente> pacientes) {
        System.out.println("\nLista de Pacientes:");
        for (Paciente paciente : pacientes) {
            System.out.println("ID " + paciente.getId() + ": " + paciente.getNome());
        }
    }

    public static void imprimirMedidas(String sinalVital, List<Double> valores) {
        Estatistica estatistica = new Estatistica(valores);
        System.out.println("\nDados para " + sinalVital + ":");
        System.out.println("Média da " + sinalVital + ": " + String.format("%.2f", estatistica.calcularMedia()));
        System.out.println("Desvio Padrão da " + sinalVital + ": " + String.format("%.2f", estatistica.calcularDesvioPadrao()));
        System.out.println("Mínimo da " + sinalVital + ": " + String.format("%.2f", estatistica.calcularMin()));
        System.out.println("Máximo da " + sinalVital + ": " + String.format("%.2f", estatistica.calcularMax()));
    }

    public static void imprimirMedidasSelecionadas(String sinalVital, List<Double> valores) {
        if (sinalVital.equals("Frequência Cardíaca")) {
            imprimirMedidas("Frequência Cardíaca", valores);
        } else if (sinalVital.equals("Temperatura")) {
            imprimirMedidas("Temperatura", valores);
        } else if (sinalVital.equals("Saturação de Oxigénio")) {
            imprimirMedidas("Saturação de Oxigénio", valores);
        }
    }

    public static void processarMedidasPaciente(Scanner scanner, Hospital hospital) {
        Paciente paciente = selecionarPaciente(scanner, hospital.getPacientes());
        if (paciente != null) {
            LocalDate[] periodo = PeriodoAnalise.selecionarPeriodoDeAnalisePaciente(scanner, hospital, paciente);
            Submenus.sinaisVitais(scanner, hospital, List.of(paciente), periodo[0], periodo[1]);
        }
    }

    public static void processarMedidasGrupo(Scanner scanner, Hospital hospital) {
        List<Paciente> grupo = selecionarGrupoPacientes(scanner, hospital.getPacientes());
        if (!grupo.isEmpty()) {
            LocalDate[] periodo = PeriodoAnalise.selecionarPeriodoDeAnaliseGrupo(scanner, hospital, grupo);
            Submenus.sinaisVitais(scanner, hospital, grupo, periodo[0], periodo[1]);
        }
    }

    public static void processarMedidasTodos(Scanner scanner, Hospital hospital) {
        LocalDate[] periodo = PeriodoAnalise.selecionarPeriodoDeAnaliseGrupo(scanner, hospital, hospital.getPacientes());
         Submenus.sinaisVitais(scanner, hospital, hospital.getPacientes(), periodo[0], periodo[1]);
    }
}
