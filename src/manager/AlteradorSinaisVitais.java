package manager;

import model.*;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
/**
 * Classe responsável por simular alterações percentuais nos sinais vitais dos pacientes
 * de um hospital e apresentar os resultados com destaque para valores fora dos limites fisiológicos.
 */
public class AlteradorSinaisVitais {

    private Hospital hospital;
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    /**
     * Construtor da classe AlteradorSinaisVitais.
     *
     * @param hospital instância do hospital contendo os pacientes e respetivas medições.
     */
    public AlteradorSinaisVitais(Hospital hospital) {
        this.hospital = hospital;
    }

    /**
     * Inicia o processo interativo com o utilizador para introduzir percentagens de alteração
     * e simular os efeitos nos sinais vitais.
     *
     * @param scanner objeto Scanner para leitura da entrada do utilizador.
     */
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

    /**
     * Imprime, para cada paciente, os valores simulados dos seus sinais vitais
     * após a aplicação da percentagem fornecida.
     *
     * @param percentagem percentagem de alteração (positiva ou negativa) a aplicar aos sinais vitais.
     */
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

    /**
     * Imprime os valores simulados para um tipo específico de sinal vital,
     * destacando os que ultrapassam os limites fisiológicos.
     *
     * @param tipo           nome do tipo de sinal vital (ex: "Temperatura").
     * @param medidas        lista de medidas do paciente.
     * @param percentagem    percentagem a aplicar aos valores originais.
     * @param limiteMin      valor mínimo fisiológico aceitável.
     * @param limiteMax      valor máximo fisiológico aceitável.
     */
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
