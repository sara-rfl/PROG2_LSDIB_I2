package ui;

import manager.AlteradorSinaisVitais;
import model.*;
import service.ClassificadorPaciente;
import service.DadosERegisto;
import manager.Listas;
import service.GraficoTexto;

import java.util.Scanner;

public class MenuPrincipal {

    private Hospital hospital;
    private Scanner scanner;

    public MenuPrincipal(Hospital hospital) {
        this.hospital = hospital;
        this.scanner = new Scanner(System.in);
    }

    public void menuInicio() {
        boolean continuarMenu = true;
        while (continuarMenu) {
            System.out.println("\n-- INTERFACE HOSPITAL XYZ -- ");
            System.out.println("\n || BEM-VINDO, UTILIZADOR. MONITORIZAÇÃO DE UCI || ");
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Registar paciente");
            System.out.println("2 - Cálculo de medidas de sumário");
            System.out.println("3 - Classificação de sinais vitais");
            System.out.println("4 - Lista de pacientes por data de nascimento");
            System.out.println("5 - Lista de técnicos");
            System.out.println("6 - Simulação da alteração percentual dos sinais vitais");
            System.out.println("7 - Percentagem de pacientes em estado crítico");
            System.out.println("8 - Gráfico de barras");
            System.out.println("9 - Sair");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                DadosERegisto.registoNovoPaciente(hospital, scanner);
            } else if (opcao == 2) {
                Submenus.medidasSumario(scanner, hospital);
            } else if (opcao == 3) {
                Submenus.menuClassificacaoPacientes(scanner, hospital);
            } else if (opcao == 4) {
                Listas.ordenarPacientesPorData(hospital.getPacientes());
                Listas.mostrarPacientes(hospital.getPacientes());
            } else if (opcao == 5) {
                Listas.ordenarTecnicosPorNome(hospital.getTecnicos());
                Listas.mostrarTecnicos(hospital.getTecnicos());
            } else if (opcao == 6) {
                AlteradorSinaisVitais simulador = new AlteradorSinaisVitais(hospital);
                simulador.iniciarAlteracao(scanner);
            } else if (opcao == 7) {
                Listas.mostrarPercentagemCriticos(scanner, hospital);
            } else if (opcao == 8) {
                Submenus.menuGraficoBarras(scanner, hospital);
            } else if (opcao == 9) {
                System.out.println("A sair...");
                continuarMenu = false;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
