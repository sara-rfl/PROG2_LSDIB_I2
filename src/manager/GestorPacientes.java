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

    /**
     * Permite ao utilizador selecionar um paciente com base no seu ID.
     *
     * @param scanner objeto para ler entradas do utilizador
     * @param hospital instância do Hospital que contém os pacientes disponíveis
     * @return paciente selecionado, ou null se não for encontrado
     */
    public static Paciente selecionarPaciente(Scanner scanner, Hospital hospital) {
        List<Paciente> pacientes = hospital.getPacientes();
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

    /**
     * Permite ao utilizador selecionar um grupo de pacientes a partir dos registados no hospital,
     * introduzindo os seus IDs separados por espaço. Apenas os pacientes com IDs correspondentes
     * serão adicionados à lista de selecionados.
     *
     * @param scanner objeto para ler entradas do utilizador
     * @param hospital instância do Hospital que contém os pacientes disponíveis
     * @return lista de pacientes selecionados com base nos IDs fornecidos pelo utilizador
     */
    public static List<Paciente> selecionarGrupoPacientes(Scanner scanner, Hospital hospital) {
        List<Paciente> pacientes = hospital.getPacientes();

        System.out.println("Selecione um grupo de pacientes (IDs separados por espaço):");
        mostrarLista(pacientes);
        System.out.print("Introduza os IDs: ");
        String[] ids = scanner.nextLine().split(" ");

        List<Paciente> selecionados = new ArrayList<>();
        for (String idStr : ids) {
            int id = Integer.parseInt(idStr); // Assume-se que o input é válido
            for (Paciente paciente : pacientes) {
                if (paciente.getId() == id) {
                    selecionados.add(paciente);
                }
            }
        }
        return selecionados;
    }

    /**
     * Exibe uma lista de todos os pacientes com os seus respetivos IDs e nomes.
     *
     * @param pacientes lista de pacientes a ser exibida
     */
    public static void mostrarLista(List<Paciente> pacientes) {
        System.out.println("\nLista de Pacientes:");
        for (Paciente paciente : pacientes) {
            System.out.println("ID " + paciente.getId() + ": " + paciente.getNome());
        }
    }

    /**
     * Imprime as medidas estatísticas para um determinado sinal vital (frequência cardíaca, temperatura, saturação de oxigénio).
     * As estatísticas incluem média, desvio padrão, valor mínimo e valor máximo.
     *
     * @param sinalVital nome do sinal vital a ser analisado
     * @param valores lista de valores do sinal vital
     */
    public static void imprimirMedidas(String sinalVital, List<Double> valores) {
        Estatistica estatistica = new Estatistica(valores);
        System.out.println("\nDados para " + sinalVital + ":");
        System.out.println("Média da " + sinalVital + ": " + String.format("%.2f", estatistica.calcularMedia()));
        System.out.println("Desvio Padrão da " + sinalVital + ": " + String.format("%.2f", estatistica.calcularDesvioPadrao()));
        System.out.println("Mínimo da " + sinalVital + ": " + String.format("%.2f", estatistica.calcularMin()));
        System.out.println("Máximo da " + sinalVital + ": " + String.format("%.2f", estatistica.calcularMax()));
    }

    /**
     * Imprime as medidas para um sinal vital específico selecionado (frequência cardíaca, temperatura ou saturação de oxigénio).
     *
     * @param sinalVital nome do sinal vital a ser analisado
     * @param valores lista de valores do sinal vital
     */
    public static void imprimirMedidasSelecionadas(String sinalVital, List<Double> valores) {
        if (sinalVital.equals("Frequência Cardíaca")) {
            imprimirMedidas("Frequência Cardíaca", valores);
        } else if (sinalVital.equals("Temperatura")) {
            imprimirMedidas("Temperatura", valores);
        } else if (sinalVital.equals("Saturação de Oxigénio")) {
            imprimirMedidas("Saturação de Oxigénio", valores);
        }
    }

}
