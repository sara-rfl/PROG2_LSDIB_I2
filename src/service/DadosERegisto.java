package service;

import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class DadosERegisto {

    /**
     * Preenche o hospital com dados de exemplo, incluindo três pacientes, dois técnicos de saúde,
     * e um conjunto de medições para cada paciente.
     *
     * @param hospital instância do hospital onde os dados serão inseridos
     */
    public static void exemplo(Hospital hospital) {
        Paciente p1 = criarPaciente("João Rodrigues", 2009, 6, 25, 1.78, 69.0);
        Paciente p2 = criarPaciente("Pablo Caetano", 2001, 6, 5, 1.89, 90.0);
        Paciente p3 = criarPaciente("Julio César", 1980, 2, 2, 1.94, 95.0);

        TecnicoSaude t1 = criarTecnico("Marta Alves", 1980, 2, 15, "Enfermeira");
        TecnicoSaude t2 = criarTecnico("Pedro Rocha", 1975, 6, 30, "Médico");

        hospital.addPaciente(p1);
        hospital.addPaciente(p2);
        hospital.addPaciente(p3);

        hospital.addTecnico(t1);
        hospital.addTecnico(t2);

        registarMedidasPaciente1(hospital, p1, t1, t2);
        registarMedidasPaciente2(hospital, p2, t2);
        registarMedidasPaciente3(hospital, p3, t1, t2);
    }

    /**
     * Cria um novo paciente com os dados fornecidos.
     *
     * @param nome   nome do paciente
     * @param ano    ano de nascimento
     * @param mes    mês de nascimento
     * @param dia    dia de nascimento
     * @param altura altura em metros
     * @param peso   peso em quilogramas
     * @return novo objeto Paciente criado com os dados fornecidos
     */
    private static Paciente criarPaciente(String nome, int ano, int mes, int dia, double altura, double peso) {
        return new Paciente(nome, LocalDate.of(ano, mes, dia), altura, peso);
    }

    /**
     * Cria um novo técnico de saúde com os dados fornecidos.
     *
     * @param nome      nome do técnico
     * @param ano       ano de nascimento
     * @param mes       mês de nascimento
     * @param dia       dia de nascimento
     * @param categoria categoria profissional
     * @return novo objeto TecnicoSaude criado com os dados fornecidos
     */
    private static TecnicoSaude criarTecnico(String nome, int ano, int mes, int dia, String categoria) {
        return new TecnicoSaude(nome, LocalDate.of(ano, mes, dia), categoria);
    }

    /**
     * Regista um conjunto de medições (frequência cardíaca, temperatura e saturação de oxigénio)
     * para o primeiro paciente de exemplo.
     *
     * @param h  instância do hospital onde as medições serão adicionadas
     * @param p  paciente a quem pertencem as medições
     * @param t1 técnico de saúde responsável por algumas medições
     * @param t2 técnico de saúde responsável por outras medições
     */
    private static void registarMedidasPaciente1(Hospital h, Paciente p, TecnicoSaude t1, TecnicoSaude t2) {
        h.addMedida(new FrequenciaCardiaca(72, LocalDateTime.of(2024, 3, 10, 14, 30), p, t1));
        h.addMedida(new Temperatura(37.5, LocalDateTime.of(2024, 3, 10, 14, 45), p, t1));
        h.addMedida(new SaturacaoOxigenio(98, LocalDateTime.of(2024, 3, 9, 18, 20), p, t1));

        h.addMedida(new FrequenciaCardiaca(89, LocalDateTime.of(2024, 3, 11, 9, 15), p, t2));
        h.addMedida(new Temperatura(37.0, LocalDateTime.of(2024, 3, 9, 18, 20), p, t2));
        h.addMedida(new SaturacaoOxigenio(95, LocalDateTime.of(2024, 5, 13, 15, 30), p, t2));

        h.addMedida(new FrequenciaCardiaca(88, LocalDateTime.of(2024, 5, 14, 8, 45), p, t1));
        h.addMedida(new Temperatura(36.9, LocalDateTime.of(2024, 5, 14, 8, 45), p, t1));
        h.addMedida(new SaturacaoOxigenio(96, LocalDateTime.of(2024, 5, 14, 8, 45), p, t1));
    }

    /**
     * Regista um conjunto de medições para o segundo paciente de exemplo.
     *
     * @param h instância do hospital onde as medições serão adicionadas
     * @param p paciente a quem pertencem as medições
     * @param t técnico de saúde responsável pelas medições
     */
    private static void registarMedidasPaciente2(Hospital h, Paciente p, TecnicoSaude t) {
        h.addMedida(new FrequenciaCardiaca(79, LocalDateTime.of(2024, 3, 8, 12, 10), p, t));
        h.addMedida(new Temperatura(37.9, LocalDateTime.of(2024, 3, 8, 12, 30), p, t));
        h.addMedida(new SaturacaoOxigenio(97, LocalDateTime.of(2024, 3, 9, 17, 10), p, t));

        h.addMedida(new FrequenciaCardiaca(99, LocalDateTime.of(2024, 3, 9, 16, 45), p, t));
        h.addMedida(new Temperatura(37.0, LocalDateTime.of(2024, 3, 9, 17, 10), p, t));
        h.addMedida(new SaturacaoOxigenio(92, LocalDateTime.of(2024, 5, 14, 12, 30), p, t));
    }

    /**
     * Regista um conjunto de medições para o terceiro paciente de exemplo.
     *
     * @param h  instância do hospital onde as medições serão adicionadas
     * @param p  paciente a quem pertencem as medições
     * @param t1 técnico de saúde responsável por algumas medições
     * @param t2 técnico de saúde responsável por outras medições
     */
    private static void registarMedidasPaciente3(Hospital h, Paciente p, TecnicoSaude t1, TecnicoSaude t2) {
        h.addMedida(new FrequenciaCardiaca(115, LocalDateTime.of(2024, 3, 15, 12, 10), p, t1));
        h.addMedida(new Temperatura(38.0, LocalDateTime.of(2024, 3, 15, 12, 30), p, t1));
        h.addMedida(new SaturacaoOxigenio(89, LocalDateTime.of(2024, 3, 12, 20, 0), p, t1));

        h.addMedida(new FrequenciaCardiaca(98, LocalDateTime.of(2024, 3, 16, 16, 45), p, t2));
        h.addMedida(new Temperatura(37.5, LocalDateTime.of(2024, 3, 16, 17, 10), p, t2));
        h.addMedida(new SaturacaoOxigenio(89, LocalDateTime.of(2024, 5, 14, 9, 0), p, t2));
    }

    /**
     * Permite o registo de novos pacientes no hospital através da interação com o utilizador.
     * Solicita os dados pessoais e regista sinais vitais associados, se for selecionado um técnico válido.
     *
     * @param hospital instância do hospital onde os pacientes serão registados
     * @param scanner  objeto Scanner utilizado para recolher a entrada do utilizador
     */
    public static void registoNovoPaciente(Hospital hospital, Scanner scanner) {
        System.out.println("\n || REGISTO DE NOVOS PACIENTES ||");
        boolean continuar = true;

        while (continuar) {
            System.out.println("Adicionar novo paciente? (s/n) ");
            String opcao = scanner.next().toLowerCase();

            if (opcao.equals("s")) {
                Paciente paciente = registarPaciente(scanner);
                hospital.addPaciente(paciente);
                TecnicoSaude tecnico = selecionarTecnico(scanner, hospital.getTecnicos());

                System.out.println("Introduza os sinais vitais para este paciente:");

                if (tecnico != null) {
                    inserirSinaisVitais(scanner, hospital, paciente, tecnico);
                    System.out.println("Paciente " + paciente.getId() + " registado com sucesso pelo técnico de saúde " + tecnico.getNome() + "!");

                } else {
                    System.out.println("Técnico inválido. Registo cancelado.");
                }
                GraficoTexto.mostrarGraficoUltimasMedidas(paciente, hospital);
            } else {
                continuar = false;
            }
        }
    }

    /**
     * Solicita os dados pessoais do paciente ao utilizador e cria um novo objeto {@code Paciente}.
     *
     * @param scanner Objeto {@code Scanner} utilizado para recolher a entrada do utilizador.
     * @return Um novo objeto {@code Paciente} com dados validados.
     */
    public static Paciente registarPaciente(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Introduza os dados do paciente: ");

        String nome = pedirNome(scanner);
        LocalDate nascimento = pedirDataDeNascimento(scanner);
        double altura = pedirAltura(scanner);
        double peso = pedirPeso(scanner);

        return new Paciente(nome, nascimento, altura, peso);
    }

    /**
     * Solicita ao utilizador o nome do paciente.
     *
     * @param scanner Scanner utilizado para ler a entrada do utilizador.
     * @return Nome introduzido pelo utilizador.
     */
    private static String pedirNome(Scanner scanner) {
        System.out.print("Nome: ");
        return scanner.nextLine();
    }

    /**
     * Solicita e valida a data de nascimento do paciente no formato dd/mm/aaaa.
     * A data deve ser válida, não pode ser futura e o ano deve ser posterior a 1900.
     *
     * @param scanner Scanner utilizado para ler a entrada do utilizador.
     * @return Objeto {@code LocalDate} correspondente à data de nascimento válida.
     */
    private static LocalDate pedirDataDeNascimento(Scanner scanner) {
        LocalDate dataNascimento = null;
        boolean dataValida = false;

        while (!dataValida) {
            System.out.print("Data de Nascimento (dd/mm/aaaa): ");
            String[] partes = scanner.nextLine().split("/");
            if (partes.length == 3) {
                int dia = Integer.parseInt(partes[0]);
                int mes = Integer.parseInt(partes[1]);
                int ano = Integer.parseInt(partes[2]);

                if (dia > 0 && dia <= 31 && mes > 0 && mes <= 12 && ano > 1900) {
                    LocalDate data = LocalDate.of(ano, mes, dia);
                    if (!data.isAfter(LocalDate.now())) {
                        dataNascimento = data;
                        dataValida = true;
                    }
                }
            }
            if (!dataValida) {
                System.out.println("Data inválida. Tente novamente.");
            }
        }
        return dataNascimento;
    }

    /**
     * Solicita ao utilizador a altura do paciente e valida se está dentro dos limites definidos na classe {@code Paciente}.
     *
     * @param scanner Scanner utilizado para ler a entrada do utilizador.
     * @return Altura válida (em metros).
     */
    private static double pedirAltura(Scanner scanner) {
        double altura = -1;
        while (!(altura >= Paciente.ALTURA_MIN && altura <= Paciente.ALTURA_MAX)) {
            System.out.print("Altura (em metros): ");
            altura = scanner.nextDouble();
            if (!(altura >= Paciente.ALTURA_MIN && altura <= Paciente.ALTURA_MAX)) {
                System.out.println("Altura inválida. Deve estar entre " +
                        Paciente.ALTURA_MIN + " e " + Paciente.ALTURA_MAX + " metros.");
            }
        }
        return altura;
    }

    /**
     * Solicita ao utilizador o peso do paciente e valida se está dentro dos limites definidos na classe {@code Paciente}.
     *
     * @param scanner Scanner utilizado para ler a entrada do utilizador.
     * @return Peso válido (em quilogramas).
     */
    private static double pedirPeso(Scanner scanner) {
        double peso = -1;
        while (!(peso >= Paciente.PESO_MIN && peso <= Paciente.PESO_MAX)) {
            System.out.print("Peso (kg): ");
            peso = scanner.nextDouble();
            if (!(peso >= Paciente.PESO_MIN && peso <= Paciente.PESO_MAX)) {
                System.out.println("Peso inválido. Deve estar entre " +
                        Paciente.PESO_MIN + "kg e " + Paciente.PESO_MAX + "kg.");
            }
        }
        return peso;
    }


    /**
     * Apresenta a lista de técnicos disponíveis e permite ao utilizador selecionar um com base no ID.
     *
     * @param scanner  objeto Scanner utilizado para a entrada do utilizador
     * @param tecnicos lista de técnicos de saúde disponíveis
     * @return o técnico de saúde selecionado, ou null se o ID não for encontrado
     */
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

    /**
     * Permite ao utilizador introduzir sinais vitais (frequência cardíaca, temperatura e saturação de oxigénio)
     * para um determinado paciente, registados pelo técnico de saúde responsável.
     * A inserção termina ao introduzir o valor 0 em cada tipo de sinal.
     *
     * @param scanner  objeto Scanner utilizado para recolher os dados
     * @param hospital instância do hospital onde as medições serão registadas
     * @param paciente paciente ao qual pertencem os dados
     * @param tecnico  técnico de saúde responsável pelo registo das medições
     */
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

