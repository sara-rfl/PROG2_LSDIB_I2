package service;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class DadosERegisto {

    public static void exemplo(Hospital hospital) {
        Paciente p1 = criarPaciente("Ana Costa", 1990, 4, 10, 1.65, 58.0);
        Paciente p2 = criarPaciente("Bruno Silva", 1985, 12, 5, 1.80, 75.0);
        Paciente p3 = criarPaciente("Carla Ferreira", 2002, 8, 22, 1.70, 62.5);

        TecnicoSaude t1 = criarTecnico("Dra. Marta Alves", 1980, 2, 15, "Enfermeira");
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
        h.addMedida(new FrequenciaCardiaca(85, LocalDateTime.of(2025, 5, 12, 9, 0), p, t1));
        h.addMedida(new Temperatura(36.7, LocalDateTime.of(2025, 5, 12, 9, 0), p, t1));
        h.addMedida(new SaturacaoOxigenio(97, LocalDateTime.of(2025, 5, 12, 9, 0), p, t1));

        h.addMedida(new FrequenciaCardiaca(90, LocalDateTime.of(2025, 5, 13, 15, 30), p, t2));
        h.addMedida(new Temperatura(37.2, LocalDateTime.of(2025, 5, 13, 15, 30), p, t2));
        h.addMedida(new SaturacaoOxigenio(95, LocalDateTime.of(2025, 5, 13, 15, 30), p, t2));

        h.addMedida(new FrequenciaCardiaca(88, LocalDateTime.of(2025, 5, 14, 8, 45), p, t1));
        h.addMedida(new Temperatura(36.9, LocalDateTime.of(2025, 5, 14, 8, 45), p, t1));
        h.addMedida(new SaturacaoOxigenio(96, LocalDateTime.of(2025, 5, 14, 8, 45), p, t1));
    }

    private static void registarMedidasPaciente2(Hospital h, Paciente p, TecnicoSaude t) {
        h.addMedida(new FrequenciaCardiaca(110, LocalDateTime.of(2025, 5, 13, 10, 0), p, t));
        h.addMedida(new Temperatura(38.3, LocalDateTime.of(2025, 5, 13, 10, 0), p, t));
        h.addMedida(new SaturacaoOxigenio(91, LocalDateTime.of(2025, 5, 13, 10, 0), p, t));

        h.addMedida(new FrequenciaCardiaca(108, LocalDateTime.of(2025, 5, 14, 12, 30), p, t));
        h.addMedida(new Temperatura(38.0, LocalDateTime.of(2025, 5, 14, 12, 30), p, t));
        h.addMedida(new SaturacaoOxigenio(92, LocalDateTime.of(2025, 5, 14, 12, 30), p, t));
    }

    private static void registarMedidasPaciente3(Hospital h, Paciente p, TecnicoSaude t1, TecnicoSaude t2) {
        h.addMedida(new FrequenciaCardiaca(125, LocalDateTime.of(2025, 5, 12, 17, 15), p, t1));
        h.addMedida(new Temperatura(39.0, LocalDateTime.of(2025, 5, 12, 17, 15), p, t1));
        h.addMedida(new SaturacaoOxigenio(88, LocalDateTime.of(2025, 5, 12, 17, 15), p, t1));

        h.addMedida(new FrequenciaCardiaca(120, LocalDateTime.of(2025, 5, 14, 9, 0), p, t2));
        h.addMedida(new Temperatura(38.5, LocalDateTime.of(2025, 5, 14, 9, 0), p, t2));
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

