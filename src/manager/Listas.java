package manager;

import model.Hospital;
import model.Paciente;
import model.TecnicoSaude;
import service.ClassificadorPaciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Classe utilitária para ordenar e mostrar listas de pacientes e técnicos de saúde.
 */
public class Listas {

    /**
     * Ordena a lista de pacientes por data de nascimento (mais velho primeiro).
     *
     * @param pacientes Lista de pacientes.
     */
    public static void ordenarPacientesPorData(List<Paciente> pacientes) {
        pacientes.sort(Comparator.comparing(Paciente::getDataNascimento));
    }

    /**
     * Mostra a lista de pacientes (um por linha).
     *
     * @param pacientes Lista de pacientes a exibir.
     */
    public static void mostrarPacientes(List<Paciente> pacientes) {
        System.out.println("\n--- Lista de Pacientes ---");
        for (Paciente p : pacientes) {
            System.out.println(p);
        }
    }

    /**
     * Ordena a lista de técnicos de saúde por nome em ordem alfabética.
     *
     * @param tecnicos Lista de técnicos.
     */
    public static void ordenarTecnicosPorNome(List<TecnicoSaude> tecnicos) {
        tecnicos.sort(Comparator.comparing(TecnicoSaude::getNome));
    }

    /**
     * Mostra a lista de técnicos de saúde (um por linha).
     *
     * @param tecnicos Lista de técnicos a exibir.
     */
    public static void mostrarTecnicos(List<TecnicoSaude> tecnicos) {
        System.out.println("\n--- Lista de Técnicos de Saúde ---");
        for (TecnicoSaude t : tecnicos) {
            System.out.println(t); // usa o toString() da classe TecnicoSaude
        }
    }
    public static void mostrarPercentagemCriticos(Scanner scanner, Hospital hospital) {

        System.out.println("\n|| Registos disponíveis para os pacientes registados ||");
        String intervalo = PeriodoAnalise.obterIntervaloDeRegistos(hospital, hospital.getPacientes());
        System.out.println("Intervalo disponível: " + intervalo + "\n");

        LocalDate[] periodo = PeriodoAnalise.selecionarPeriodoDeAnaliseGrupo(scanner, hospital, hospital.getPacientes());

        LocalDate hoje = LocalDate.now();
        LocalDateTime fimReal;

        if (periodo[1].isEqual(hoje)) {
            fimReal = LocalDateTime.now();
        } else {
            fimReal = periodo[1].atTime(23, 59);
        }

        double percentagem = ClassificadorPaciente.calcularPercentagemCriticos(
                hospital,
                hospital.getPacientes(),
                periodo[0].atStartOfDay(),
                fimReal
        );

        System.out.printf("Percentagem de pacientes em estado crítico: %.2f%%\n", percentagem);
    }




}
