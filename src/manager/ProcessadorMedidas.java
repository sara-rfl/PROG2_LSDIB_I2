package manager;

import model.Hospital;
import model.Paciente;
import ui.Submenus;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ProcessadorMedidas {
/**
 * Processa as medidas de sinais vitais de um paciente específico em um período determinado.
 *
 * O metodo solicita ao usuário a seleção de um paciente, o período de análise e chama o submenu responsável por exibir ou recolher sinais vitais do paciente no período informado
 *
 * @param scanner usado para entrada do usuário no terminal.
 * @param hospital que contém a lista de pacientes disponíveis.
 */
public static void processarMedidasPaciente(Scanner scanner, Hospital hospital) {
    Paciente paciente = GestorPacientes.selecionarPaciente(scanner, hospital.getPacientes());
    if (paciente != null) {
        LocalDate[] periodo = PeriodoAnalise.selecionarPeriodoDeAnalisePaciente(scanner, hospital, paciente);
        Submenus.sinaisVitais(scanner, hospital, List.of(paciente), periodo[0], periodo[1]);
    }
}

/**
 * Processa as medidas de sinais vitais de um grupo de pacientes selecionado pelo utilizador,
 * num intervalo de tempo definido.
 * Solicita a seleção do grupo e o período de análise, e depois apresenta ou recolhe os sinais vitais.
 *
 * @param scanner  objeto Scanner usado para entrada de dados do utilizador
 * @param hospital instância do hospital que contém os pacientes disponíveis
 */
public static void processarMedidasGrupo(Scanner scanner, Hospital hospital) {
    List<Paciente> grupo = GestorPacientes.selecionarGrupoPacientes(scanner, hospital.getPacientes());
    if (!grupo.isEmpty()) {
        LocalDate[] periodo = PeriodoAnalise.selecionarPeriodoDeAnaliseGrupo(scanner, hospital, grupo);
        Submenus.sinaisVitais(scanner, hospital, grupo, periodo[0], periodo[1]);
    }
}

/**
 * Processa as medidas de sinais vitais de todos os pacientes do hospital,
 * num intervalo de tempo definido.
 * Solicita ao utilizador o período de análise e aplica a análise a todos os pacientes registados.
 *
 * @param scanner  objeto Scanner usado para entrada de dados do utilizador
 * @param hospital instância do hospital que contém todos os pacientes
 */
public static void processarMedidasTodos(Scanner scanner, Hospital hospital) {
    LocalDate[] periodo = PeriodoAnalise.selecionarPeriodoDeAnaliseGrupo(scanner, hospital, hospital.getPacientes());
    Submenus.sinaisVitais(scanner, hospital, hospital.getPacientes(), periodo[0], periodo[1]);
}
}