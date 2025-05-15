package service;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class DadosERegisto {

    public static void exemplo(Hospital hospital) {
        Paciente p1 = criarPaciente("João Rodrigues", 2009, 6, 25, 1.78, 69.0);
        Paciente p2 = criarPaciente("Pablo Caetano", 2001, 6, 5, 1.89, 90.0);
        Paciente p3 = criarPaciente("Julio César", 1980, 2, 2, 1.94, 95.0);

        TecnicoSaude t1 = criarTecnico("Enfermeira Marta Alves", 1980, 2, 15, "Enfermeira");
        TecnicoSaude t2 = criarTecnico("Dr. Pedro Rocha", 1975, 6, 30, "Médico");

        hospital.addPaciente(p1);
        hospital.addPaciente(p2);
        hospital.addPaciente(p3);

        hospital.addTecnico(t1);
        hospital.addTecnico(t2);

        registarMedidasPaciente1(hospital, p1, t1, t2);
        registarMedidasPaciente2(hospital, p2, t2);
        registarMedidasPaciente3(hospital, p3, t1, t2);
    }

    private static Paciente criarPaciente(String nome, int ano, int mes, int dia, double altura, double peso) {
        return new Paciente(nome, LocalDate.of(ano, mes, dia), altura, peso);
    }

    private static TecnicoSaude criarTecnico(String nome, int ano, int mes, int dia, String categoria) {
        return new TecnicoSaude(nome, LocalDate.of(ano, mes, dia), categoria);
    }

    private static void registarMedidasPaciente1(Hospital h, Paciente p, TecnicoSaude t1, TecnicoSaude t2) {
        h.addMedida(new FrequenciaCardiaca(72, LocalDateTime.of(2024, 3, 10, 14, 30), p, t1));
        h.addMedida(new Temperatura(37.5, LocalDateTime.of(2024, 3, 10, 14, 45), p, t1));
        h.addMedida(new SaturacaoOxigenio(98, LocalDateTime.of(2024, 3, 9, 18, 20), p, t1));

        h.addMedida(new FrequenciaCardiaca(89, LocalDateTime.of(2024, 3, 11, 9, 15), p, t2));
        h.addMedida(new Temperatura(37.0, LocalDateTime.of(2024, 3, 9, 18, 20), p, t2));
        h.addMedida(new SaturacaoOxigenio(95, LocalDateTime.of(2025, 5, 13, 15, 30), p, t2));

        h.addMedida(new FrequenciaCardiaca(88, LocalDateTime.of(2025, 5, 14, 8, 45), p, t1));
        h.addMedida(new Temperatura(36.9, LocalDateTime.of(2025, 5, 14, 8, 45), p, t1));
        h.addMedida(new SaturacaoOxigenio(96, LocalDateTime.of(2025, 5, 14, 8, 45), p, t1));
    }

    private static void registarMedidasPaciente2(Hospital h, Paciente p, TecnicoSaude t) {
        h.addMedida(new FrequenciaCardiaca(79, LocalDateTime.of(2024, 3, 8, 12, 10), p, t));
        h.addMedida(new Temperatura(37.9, LocalDateTime.of(2024, 3, 8, 12, 30), p, t));
        h.addMedida(new SaturacaoOxigenio(97, LocalDateTime.of(2024, 3, 9, 17, 10), p, t));

        h.addMedida(new FrequenciaCardiaca(99, LocalDateTime.of(2024, 3, 9, 16, 45), p, t));
        h.addMedida(new Temperatura(37.0, LocalDateTime.of(2024, 3, 9, 17, 10), p, t));
        h.addMedida(new SaturacaoOxigenio(92, LocalDateTime.of(2025, 5, 14, 12, 30), p, t));
    }

    private static void registarMedidasPaciente3(Hospital h, Paciente p, TecnicoSaude t1, TecnicoSaude t2) {
        h.addMedida(new FrequenciaCardiaca(115, LocalDateTime.of(2024, 3, 15, 12, 10), p, t1));
        h.addMedida(new Temperatura(38.0, LocalDateTime.of(2025, 3, 15, 12, 30), p, t1));
        h.addMedida(new SaturacaoOxigenio(89, LocalDateTime.of(2024, 3, 12, 20, 0), p, t1));

        h.addMedida(new FrequenciaCardiaca(98, LocalDateTime.of(2024, 3, 16, 16, 45), p, t2));
        h.addMedida(new Temperatura(37.5, LocalDateTime.of(2024, 3, 16, 17, 10), p, t2));
        h.addMedida(new SaturacaoOxigenio(89, LocalDateTime.of(2025, 5, 14, 9, 0), p, t2));
    }

    public static void registoNovoPaciente(Hospital hospital, Scanner scanner) {
        System.out.println("\n || REGISTO DE NOVOS PACIENTES ||");
        boolean continuar = true;

        while (continuar) {
            System.out.println("Adicionar novo paciente? (s/n) ");
            String opcao = scanner.next().toLowerCase();

            if (opcao.equals("s")) {
                Paciente paciente = registarPaciente(scanner);
                hospital.addPaciente(paciente); // Adiciona ao hospital

                System.out.println("Introduza os sinais vitais para este paciente:");
                TecnicoSaude tecnico = selecionarTecnico(scanner, hospital.getTecnicos());

                if (tecnico != null) {
                    inserirSinaisVitais(scanner, hospital, paciente, tecnico);
                    System.out.println("Paciente " + paciente.getId() + " registado com sucesso pelo técnico de saúde " + tecnico.getNome() + "!");
                } else {
                    System.out.println("Técnico inválido. Registo cancelado.");
                }

            } else {
                continuar = false;
            }
        }
    }


    /**
     * Solicita os dados pessoais do paciente ao utilizador e cria um novo objeto {@code entidades.Paciente}.
     *
     * @param scanner Objeto {@code Scanner} para entrada de dados.
     * @return Um novo objeto {@code entidades.Paciente} com os dados fornecidos.
     */
    public static Paciente registarPaciente(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Introduza os dados do paciente: ");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Data de Nascimento (dd/mm/aaaa): ");
        String dataStr = scanner.nextLine();
        String[] partes = dataStr.split("/");
        LocalDate dataNascimento = LocalDate.of(
                Integer.parseInt(partes[2]),
                Integer.parseInt(partes[1]),
                Integer.parseInt(partes[0])
        );
        System.out.print("Altura (em metros): ");
        double altura = scanner.nextDouble();
        System.out.print("Peso (kg): ");
        double peso = scanner.nextDouble();
        return new Paciente(nome, dataNascimento, altura, peso);
    }

    public static TecnicoSaude selecionarTecnico(Scanner scanner, List<TecnicoSaude> tecnicos) {
        System.out.println("- Técnicos disponíveis: ");
        for (TecnicoSaude t : tecnicos) {
            System.out.println("ID: " + t.getId() + " | Nome: " + t.getNome());
        }

        System.out.print("Introduza o ID do responsável pelas medições: ");
        int idTecnico = scanner.nextInt();
        scanner.nextLine();

        for (TecnicoSaude t : tecnicos) {
            if (t.getId() == idTecnico) {
                return t;
            }
        }
        return null;
    }

    public static void inserirSinaisVitais(Scanner scanner, Hospital hospital, Paciente paciente, TecnicoSaude tecnico) {
        System.out.println("Frequência cardíaca (0 para terminar): ");
        double valor;
        do {
            valor = scanner.nextDouble();
            if (valor > 0) {
                hospital.addMedida(new FrequenciaCardiaca(valor, LocalDateTime.now(), paciente, tecnico));
            }
        } while (valor > 0);

        System.out.println("Temperatura (0 para terminar): ");
        do {
            valor = scanner.nextDouble();
            if (valor > 0) {
                hospital.addMedida(new Temperatura(valor, LocalDateTime.now(), paciente, tecnico));
            }
        } while (valor > 0);

        System.out.println("Saturação de oxigénio (0 para terminar): ");
        do {
            valor = scanner.nextDouble();
            if (valor > 0) {
                hospital.addMedida(new SaturacaoOxigenio(valor, LocalDateTime.now(), paciente, tecnico));
            }
        } while (valor > 0);
    }
}

