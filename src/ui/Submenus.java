package ui;

import manager.PeriodoAnalise;
import model.Hospital;
import model.Paciente;
import manager.GestorPacientes;
import manager.ProcessadorMedidas;
import manager.FiltroSinaisVitais;
import model.Medida;
import model.ScoreGravidade;
import service.Serializador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe utilitária responsável por apresentar submenus de funcionalidades específicas,
 * como cálculo de medidas de sumário, visualização de sinais vitais, classificação por período,
 * gráficos de barras e score de gravidade.
 */
public class Submenus {
    /**
     * Apresenta um menu para cálculo de medidas de sumário (média, mínimo, máximo, desvio padrão),
     * podendo ser aplicado a um paciente, grupo ou todos os pacientes.
     *
     * @param scanner  Scanner para leitura do input do utilizador
     * @param hospital Instância do hospital com os dados
     */
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
                ProcessadorMedidas.processarMedidasPaciente(scanner, hospital);
            } else if (opcao == 2) {
                ProcessadorMedidas.processarMedidasGrupo(scanner, hospital);
            } else if (opcao == 3) {
                ProcessadorMedidas.processarMedidasTodos(scanner, hospital);
            } else if (opcao == 4) {
                continuar = false;
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }
    /**
     * Apresenta um submenu para análise de sinais vitais dentro de um determinado período.
     * Permite escolher entre frequência cardíaca, saturação, temperatura ou todos.
     *
     * @param scanner   Scanner para input do utilizador
     * @param hospital  Instância do hospital com os dados
     * @param pacientes Lista de pacientes a analisar
     * @param inicio    Data de início do período
     * @param fim       Data de fim do período
     */
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
    /**
     * Processa e calcula medidas estatísticas para o sinal vital escolhido,
     * chamando a filtragem e apresentação dos valores.
     *
     * @param opcao     Tipo de sinal vital (1 a 3) ou todos (4)
     * @param hospital  Instância do hospital com os dados
     * @param pacientes Lista de pacientes
     * @param inicio    Data de início do período
     * @param fim       Data de fim do período
     */
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

    /**
     * Apresenta o menu de classificação de pacientes com base nos sinais vitais
     * num determinado período selecionado.
     *
     * @param scanner  Scanner para input do utilizador
     * @param hospital Instância do hospital com os dados
     */
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

    /**
     * Apresenta um menu com opções para gerar gráficos de barras:
     * - Médias dos sinais vitais
     * - Valores reais agrupados por tipo
     *
     * @param scanner  Scanner para input do utilizador
     * @param hospital Instância do hospital com os dados
     */
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

    /**
     * Apresenta o menu de análise do Score de Gravidade:
     * - Determina o paciente mais grave
     * - Calcula score de gravidade individual e interpreta o risco
     *
     * @param scanner  Scanner para input do utilizador
     * @param hospital Instância do hospital com os dados
     */
    public static void menuScoreGravidade(Scanner scanner, Hospital hospital) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n || SCORE DE GRAVIDADE ||");
            System.out.println("1 - Paciente mais grave");
            System.out.println("2 - Calcular Score de Gravidade");
            System.out.println("3 - Voltar ao menu principal");
            int opcao = scanner.nextInt();
            scanner.nextLine();
            if (opcao == 1) {
                Paciente maisGrave = ScoreGravidade.pacienteMaisGrave(hospital.getPacientes(), hospital);
                if (maisGrave != null) {
                    System.out.println("Paciente mais grave: " + maisGrave.getNome());
                    double scoreMaisGrave = ScoreGravidade.scorePaciente(maisGrave, hospital);
                    System.out.println("Score: " + scoreMaisGrave);
                    ScoreGravidade.interpretarScore(scoreMaisGrave);
                } else {
                    System.out.println("Nenhum paciente encontrado.");
                }
            } else if (opcao == 2) {
                Paciente paciente = GestorPacientes.selecionarPaciente(scanner, hospital.getPacientes());
                if (paciente != null) {
                    System.out.println("\nPaciente selecionado: " + paciente.getNome());
                    double score = ScoreGravidade.scorePaciente(paciente, hospital);
                    String gravidade = ScoreGravidade.interpretarScore(score);
                    System.out.println("Score: " + score + " -> " + gravidade);
                } else {
                    System.out.println("Paciente não encontrado.");
                }
            } else if (opcao == 3) {
                continuar = false;
            } else {
                System.out.println("Opção inválida.");
            }
        }
    }

    /**
     * Apresenta o menu de análise de Serialização, permite:
     * - Guardar os dados atuais do hospital num ficheiro binário
     * - Carregar os dados guardados previamente a partir do ficheiro
     * - Voltar ao menu principal
     *
     * @param scanner objeto {@code Scanner} utilizado para ler as opções do utilizador
     * @param hospital instância atual do {@code Hospital} que será guardada ou atualizada com base no ficheiro
     */
    public static void menuSerializador(Scanner scanner, Hospital hospital) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("\n || GESTÃO DE DADOS || ");
            System.out.println("1 - Guardar dados");
            System.out.println("2 - Carregar dados");
            System.out.println("3 - Voltar ao menu principal");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                Serializador.guardarHospital(hospital, "hospital.dat");
            } else if (opcao == 2) {
                Hospital carregado = Serializador.carregarHospital("hospital.dat");
                if (carregado != null) {
                    hospital.getPacientes().clear();
                    hospital.getPacientes().addAll(carregado.getPacientes());

                    hospital.getTecnicos().clear();
                    hospital.getTecnicos().addAll(carregado.getTecnicos());

                    hospital.getMedidas().clear();
                    hospital.getMedidas().addAll(carregado.getMedidas());

                    System.out.println("Dados carregados com sucesso.");
                } else {
                    System.out.println("Erro ao carregar dados.");
                }
            } else if (opcao == 3) {
                continuar = false;
            } else {
                System.out.println("Opção inválida.");
            }

        }
    }

}
