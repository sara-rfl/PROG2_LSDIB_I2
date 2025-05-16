package ui;

import manager.PeriodoAnalise;
import model.Hospital;
import model.Paciente;
import manager.GestorPacientes;
import service.ClassificadorPaciente;
import service.ClassificadorSinaisVitais;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static manager.FiltroSinaisVitais.filtrarPorTipoEPeriodo;

public class Submenus {

    public static void medidasSumario(Scanner scanner, Hospital hospital) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n || CÁLCULO DE MEDIDAS DE SUMÁRIO ||");
            System.out.println("1 - Paciente individual");
            System.out.println("2 - Grupo de pacientes");
            System.out.println("3 - Todos os pacientes");
            System.out.println("4 - Voltar ao menu principal");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                GestorPacientes.processarMedidasPaciente(scanner, hospital);
            } else if (opcao == 2) {
                GestorPacientes.processarMedidasGrupo(scanner, hospital);
            } else if (opcao == 3) {
                GestorPacientes.processarMedidasTodos(scanner, hospital);
            } else if (opcao == 4) {
                continuar = false;
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }

    public static void sinaisVitais(Scanner scanner, Hospital hospital, List<Paciente> pacientes, LocalDate inicio, LocalDate fim) {
        int opcao;
        do {
            System.out.println("\n--- Menu Sinais Vitais ---");
            System.out.println("1. Frequência Cardíaca");
            System.out.println("2. Saturação de Oxigénio");
            System.out.println("3. Temperatura");
            System.out.println("4. Todas");
            System.out.println("5. Voltar");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao >= 1 && opcao <= 4) {
                processarOpcao(opcao, hospital, pacientes, inicio, fim);
            } else if (opcao != 5) {
                System.out.println("Opção inválida.");
            }
        } while (opcao != 5);
    }

    public static void processarOpcao(int opcao, Hospital hospital, List<Paciente> pacientes, LocalDate inicio, LocalDate fim) {
        List<Double> valores = new ArrayList<>();

        if (opcao == 1) {
            for (Paciente p : pacientes) {
                ClassificadorSinaisVitais.obterValoresFiltrados(hospital, p, "Frequência Cardíaca", inicio, fim);
            }
            GestorPacientes.imprimirMedidasSelecionadas("Frequência Cardíaca", valores);

        } else if (opcao == 2) {
            for (Paciente p : pacientes) {
                ClassificadorSinaisVitais.obterValoresFiltrados(hospital, p, "Saturação de Oxigénio", inicio, fim);
            }
            GestorPacientes.imprimirMedidasSelecionadas("Saturação de Oxigénio", valores);

        } else if (opcao == 3) {
            for (Paciente p : pacientes) {
                ClassificadorSinaisVitais.obterValoresFiltrados(hospital, p, "Temperatura", inicio, fim);
            }
            GestorPacientes.imprimirMedidasSelecionadas("Temperatura", valores);

        } else if (opcao == 4) {
            processarOpcao(1, hospital, pacientes, inicio, fim);
            processarOpcao(2, hospital, pacientes, inicio, fim);
            processarOpcao(3, hospital, pacientes, inicio, fim);
        }
    }

    public static void menuClassificacaoPacientes(Scanner scanner, Hospital hospital) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n || CLASSIFICAÇÃO DE PACIENTES || ");
            System.out.println("1 - Selecionar e classificar paciente por período");
            System.out.println("2 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();
            if (escolha == 1) {
                Paciente paciente = GestorPacientes.selecionarPaciente(scanner, hospital.getPacientes());
                if (paciente != null) {
                    LocalDate[] periodo = PeriodoAnalise.selecionarPeriodoDeAnalisePaciente(scanner, hospital, paciente);

                    System.out.println("\nPaciente selecionado com sucesso!");
                    service.ComparadorSinaisVitais.comparar(hospital, paciente, periodo[0], periodo[1]);
                }

        } else if (escolha == 2) {
                continuar = false;

            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

}
