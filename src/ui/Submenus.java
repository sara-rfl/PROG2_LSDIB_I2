package ui;

import manager.PeriodoAnalise;
import model.Hospital;
import model.Paciente;
import manager.GestorPacientes;
import manager.FiltroSinaisVitais;
import model.Medida;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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
        String[] tipos = {"Frequência Cardíaca", "Saturação de Oxigénio", "Temperatura"};

        if (opcao >= 1 && opcao <= 3) {
            String tipo = tipos[opcao - 1];
            List<Double> valores = new ArrayList<>();

            for (Paciente p : pacientes) {
                List<Medida> medidas = hospital.getMedidasPorPaciente(p);
                List<Medida> filtradas = FiltroSinaisVitais.filtrarPorTipoEPeriodo(
                        medidas, tipo, inicio.atStartOfDay(), fim.atTime(23, 59, 59));
                for (Medida m : filtradas) {
                    valores.add(m.getValor());
                }
            }
            GestorPacientes.imprimirMedidasSelecionadas(tipo, valores);

        } else if (opcao == 4) {
            for (int i = 1; i <= 3; i++) {
                processarOpcao(i, hospital, pacientes, inicio, fim);
            }
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


    public static void menuGraficoBarras(Scanner scanner, Hospital hospital) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n || GRÁFICOS DE BARRAS || ");
            System.out.println("1 - De Médias");
            System.out.println("2 - De Valores Reais");
            System.out.println("3 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha == 1) {
                Paciente paciente = GestorPacientes.selecionarPaciente(scanner, hospital.getPacientes());
                if (paciente != null) {

                    System.out.println("\nPaciente selecionado com sucesso!");
                    service.GraficoTexto.mostrarGraficoMediasPaciente(scanner, hospital);
                }


            } else if (escolha == 2) {
                Paciente paciente = GestorPacientes.selecionarPaciente(scanner, hospital.getPacientes());
                if (paciente != null) {
                    System.out.println("\nPaciente selecionado com sucesso!");

                    service.GraficoTexto.mostrarGruposValoresReais(scanner, hospital);
                }

            } else  if (escolha == 3) {
                continuar = false;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

}
