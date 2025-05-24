package ui;

import manager.AlteradorSinaisVitais;
import model.*;
import service.DadosERegisto;
import manager.Listas;
import io.LeitorFicheiros;

import java.util.Scanner;

/**
 * Classe principal que define o ponto de entrada para a interface textual do sistema.
 */
public class MenuPrincipal {

    private final Hospital hospital;
    private final Scanner scanner;

    /**
     * Construtor principal da interface.
     *
     * @param hospital Instância do hospital a ser usada
     */
    public MenuPrincipal(Hospital hospital) {
        this.hospital = hospital;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Permite ao utilizador escolher entre criar dados automaticamente
     * ou carregar de ficheiro. Caso contrário, inicia com base vazia.
     */
    private void inicializarDados() {
        System.out.println("\nDeseja:");
        System.out.println("1 - Criar dados automaticamente");
        System.out.println("2 - Carregar dados a partir de ficheiro");
        System.out.print("Escolha (1 ou 2): ");

        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha == 1) {
            DadosERegisto.exemplo(hospital);
        } else if (escolha == 2) {
            System.out.print("Nome do ficheiro (ex: medidas.txt): ");
            String nomeFicheiro = scanner.nextLine();
            LeitorFicheiros.importarMedidas(nomeFicheiro, hospital);
        } else {
            System.out.println("Opção inválida. A iniciar sem dados.");
        }
    }

        /**
         * Metodo principal que apresenta o menu inicial ao utilizador.
         * Permite registar pacientes, carregar dados ou aceder ao menu de análise.
         */
        public void menuPrincipal() {
            System.out.println("\n-- INTERFACE HOSPITAL XYZ --");
            System.out.println("|| BEM-VINDO, UTILIZADOR. MONITORIZAÇÃO DE UCI ||");

            inicializarDados(); // primeiro escolhe como carregar os dados

            boolean continuar = true;
            while (continuar) {
                System.out.println("\nEscolha uma opção:");
                System.out.println("1 - Registar novo paciente");
                System.out.println("2 - Analisar dados");
                System.out.println("3 - Gestão de dados");
                System.out.println("4 - Sair");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao == 1) DadosERegisto.registoNovoPaciente(hospital, scanner);
                else if (opcao == 2) menuAnalise();
                else if (opcao == 3) Submenus.menuSerializador(scanner, hospital);
                else if (opcao == 4) {
                    System.out.println("A sair...");
                    continuar = false;
                } else {
                    System.out.println("Opção inválida.");
                }
            }
        }

        /**
         * Apresenta o submenu de análise de dados do hospital,
         * incluindo medidas de sumário, classificação, gráficos e score.
         */
        private void menuAnalise() {
            boolean voltar = false;
            while (!voltar) {
                System.out.println("\n--- ANÁLISE DE DADOS ---");
                System.out.println("1 - Cálculo de medidas de sumário");
                System.out.println("2 - Classificação de sinais vitais");
                System.out.println("3 - Listas");
                System.out.println("4 - Simulação da alteração percentual dos sinais");
                System.out.println("5 - Percentagem de pacientes críticos");
                System.out.println("6 - Gráfico de barras");
                System.out.println("7 - Score de Gravidade");
                System.out.println("8 - Voltar");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao == 1) Submenus.medidasSumario(scanner, hospital);
                else if (opcao == 2) Submenus.menuClassificacaoPacientes(scanner, hospital);
                else if (opcao == 3) menuListas();
                else if (opcao == 4) new AlteradorSinaisVitais(hospital).iniciarAlteracao(scanner);
                else if (opcao == 5) Listas.mostrarPercentagemCriticos(scanner, hospital);
                else if (opcao == 6) Submenus.menuGraficoBarras(scanner, hospital);
                else if (opcao == 7) Submenus.menuScoreGravidade (scanner, hospital);
                else if (opcao == 8) voltar = true;
                else System.out.println("Opção inválida.");
            }
        }

        /**
         * Apresenta as opções de listagens de pacientes e técnicos,
         * ordenados por data de nascimento ou nome, respetivamente.
         */
        private void menuListas() {
            boolean voltar = false;

            while (!voltar) {
                System.out.println("\n--- LISTAS DISPONÍVEIS ---");
                System.out.println("1 - Lista de pacientes por data de nascimento");
                System.out.println("2 - Lista de técnicos por nome");
                System.out.println("3 - Voltar");

                int opcao = scanner.nextInt();
                scanner.nextLine();

                if (opcao == 1) {
                    Listas.ordenarPacientesPorData(hospital.getPacientes());
                    Listas.mostrarPacientes(hospital.getPacientes());
                } else if (opcao == 2) {
                    Listas.ordenarTecnicosPorNome(hospital.getTecnicos());
                    Listas.mostrarTecnicos(hospital.getTecnicos());
                } else if (opcao == 3) voltar = true;
                else System.out.println("Opção inválida.");
            }
        }
    }
