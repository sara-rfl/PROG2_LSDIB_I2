package manager;

import model.Paciente;
import model.TecnicoSaude;

import java.util.Comparator;
import java.util.List;

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
}
