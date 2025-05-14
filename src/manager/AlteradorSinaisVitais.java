package manager;

import model.*;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class AlteradorSinaisVitais {

    private Hospital hospital;
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public AlteradorSinaisVitais(Hospital hospital) {
        this.hospital = hospital;
    }

    public void iniciarAlteracao(Scanner scanner) {
        String resposta;
        do {
            System.out.println("Introduza a percentagem de alteração (ex: 10 ou -15): ");
            double percentagem = scanner.nextDouble();
            scanner.nextLine();

            if (percentagem < -100) {
                System.out.println("Percentagem inválida: não pode reduzir sinais vitais a valores negativos.\n");
            } else {
                imprimirResumoSimulado(percentagem);
            }

            System.out.println("\nDeseja fazer outra simulação? (s/n): ");
            resposta = scanner.nextLine();
        } while (resposta.equalsIgnoreCase("s"));
    }

    public void imprimirResumoSimulado(double percentagem) {
        System.out.println("\n--- Simulação da alteração dos sinais vitais (" + percentagem + "%) ---\n");

        for (Paciente paciente : hospital.getPacientes()) {
            System.out.println("Paciente ID " + paciente.getId() + " - " + paciente.getNome());
            List<Medida> medidas = hospital.getMedidasPorPaciente(paciente);

            imprimirSimulados("Frequência Cardíaca", medidas, percentagem, FrequenciaCardiaca.FC_NORMAL_MIN, FrequenciaCardiaca.FC_ATENCAO_MAX);
            imprimirSimulados("Temperatura", medidas, percentagem, Temperatura.TEMP_NORMAL_MIN, Temperatura.TEMP_ATENCAO_MAX);
            imprimirSimulados("Saturação de Oxigénio", medidas, percentagem, SaturacaoOxigenio.SAT_ATENCAO_MIN, SaturacaoOxigenio.SAT_NORMAL_MIN);

            System.out.println();
        }
    }

    private void imprimirSimulados(String tipo, List<Medida> medidas, double percentagem, double limiteMin, double limiteMax) {
        System.out.println("  " + tipo + ":");

        boolean encontrou = false;

        for (Medida m : medidas) {
            boolean tipoCorresponde =
                    (tipo.equals("Frequência Cardíaca") && m instanceof FrequenciaCardiaca) ||
                            (tipo.equals("Temperatura") && m instanceof Temperatura) ||
                            (tipo.equals("Saturação de Oxigénio") && m instanceof SaturacaoOxigenio);

            if (tipoCorresponde) {
                encontrou = true;
                double valorSimulado = m.getValor() * (1 + percentagem / 100.0);
                LocalDateTime data = m.getDataHora();
                String formatada = data.format(FORMATADOR);

                System.out.print("    [" + formatada + "] " + String.format("%.2f", valorSimulado));

                if (valorSimulado < limiteMin || valorSimulado > limiteMax) {
                    System.out.print(" || PERIGO: alteração fora dos limites fisiológicos");
                }

                System.out.println();
            }
        }

        if (!encontrou) {
            System.out.println("    Sem registos.");
        }
    }
}
