package service;

import manager.FiltroSinaisVitais;
import manager.GestorPacientes;
import manager.PeriodoAnalise;
import model.Hospital;
import model.Medida;
import model.Paciente;
import java.util.Arrays;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class GraficoTexto {

    public static void mostrarBarra(String titulo, double valor, double escala) {
        int comprimento = (int) (valor / escala);
        StringBuilder barra = new StringBuilder();
        for (int i = 0; i < comprimento; i++) {
            barra.append("*");
        }
        System.out.printf("%s: %.1f  %s\n", titulo, valor, barra.toString());
    }


    public static void mostrarGraficoMediasPaciente(Scanner scanner, Hospital hospital) {
        Paciente paciente = GestorPacientes.selecionarPaciente(scanner, hospital.getPacientes());
        LocalDate[] datas = PeriodoAnalise.selecionarPeriodoDeAnalisePaciente(scanner, hospital, paciente);

        LocalDateTime inicio = datas[0].atStartOfDay();
        LocalDateTime fim = datas[1].atTime(datas[1].isEqual(LocalDate.now()) ? LocalTime.now() : LocalTime.of(23, 59));


        String[] tipos = {"Frequência Cardíaca", "Temperatura", "Saturação de Oxigénio"};
        double[] escalas = {
                10.0, //escala para a Frequência Cardíaca --> FC/10
                2.0, // escala para a Temperatura --> TEMP/2.0
                2.0 // ESCALA PARA A SP02 --> SP02/2.0
        };

        String[] nomes = {"Frequência cardíaca (bpm)", "Temperatura corporal (ºC)", "Saturação de oxigénio (%)"};

        for (int i = 0; i < tipos.length; i++) {
            List<Medida> medidas = FiltroSinaisVitais.filtrarPorTipoEPeriodo(
                    hospital.getMedidasPorPaciente(paciente), tipos[i], inicio, fim);

            List<Double> valores = medidas.stream().map(Medida::getValor).toList();


            if (valores.isEmpty()) {
                System.out.printf("%s: Sem registos no intervalo\n", nomes[i]);
            } else {
                Estatistica estat = new Estatistica(valores);
                double media = estat.calcularMedia();
                GraficoTexto.mostrarBarra(nomes[i], media, escalas[i]);
            }


        }
    }

    public static void mostrarGraficoUltimasMedidas(Paciente paciente, Hospital hospital) {
        System.out.println("\nResumo gráfico dos sinais vitais registados:");

        String[] tipos = {"Frequência Cardíaca", "Temperatura", "Saturação de Oxigénio"};

        String[] nomes = {
                "Frequência cardíaca (bpm)",
                "Temperatura corporal (ºC)",
                "Saturação de oxigénio (%)"
        };
        double[] escalas = {10.0, 2.0, 2.0};

        for (int i = 0; i < tipos.length; i++) {
            List<Medida> medidas = hospital.getMedidasPorPaciente(paciente);

            Medida ultima = null;
            for (Medida m : medidas) {
                if (m.getTipo().equalsIgnoreCase(tipos[i])) {
                    if (ultima == null || m.getDataHora().isAfter(ultima.getDataHora())) {
                        ultima = m;
                    }
                }
            }

            if (medidas.isEmpty()) {
                System.out.printf("%s: Nenhum registo\n", nomes[i]);
                System.out.printf("%s: -\n", nomes[i]);

            } else {

                double valor = medidas.get(i).getValor();
                GraficoTexto.mostrarBarra(nomes[i], valor, escalas[i]);
            }
        }
    }

    public static void mostrarGruposValoresReais(Scanner scanner, Hospital hospital) {
        Paciente paciente = GestorPacientes.selecionarPaciente(scanner, hospital.getPacientes());
        List<Medida> todas = hospital.getMedidasPorPaciente(paciente);

        List<Medida> fc = filtrarTipo(todas, "frequencia cardiaca");
        List<Medida> temp = filtrarTipo(todas, "temperatura");
        List<Medida> spo2 = filtrarTipo(todas, "saturação de oxigénio");

        int grupos = Math.max(fc.size(), Math.max(temp.size(), spo2.size()));

        for (int i = 0; i < grupos; i++) {
            Medida[] grupo = obterGrupo(fc, temp, spo2, i);
            mostrarGrupo(grupo, i + 1);
        }
    }

    private static List<Medida> filtrarTipo(List<Medida> medidas, String tipoProcurado) {
        return medidas.stream()
                .filter(m -> m.getTipo().trim().equalsIgnoreCase(tipoProcurado)
                        || tipoProcurado.equals("saturação de oxigénio") && m.getTipo().trim().equalsIgnoreCase("saturacao de oxigenio"))
                .toList();
    }

    private static Medida[] obterGrupo(List<Medida> fc, List<Medida> temp, List<Medida> spo2, int i) {
        Medida mFc = i < fc.size() ? fc.get(i) : null;
        Medida mTemp = i < temp.size() ? temp.get(i) : null;
        Medida mSpo2 = i < spo2.size() ? spo2.get(i) : null;
        return new Medida[]{mFc, mTemp, mSpo2};
    }

    private static void mostrarGrupo(Medida[] grupo, int numero) {
        String[] nomes = {
                "Frequência cardíaca (bpm)",
                "Temperatura corporal (ºC)",
                "Saturação de oxigénio (%)"
        };
        double[] escalas = {5.0, 0.2, 2.0};

        // Calcular intervalo
        List<LocalDateTime> datas = Arrays.stream(grupo)
                .filter(Objects::nonNull)
                .map(Medida::getDataHora)
                .toList();

        if (!datas.isEmpty()) {
            LocalDate inicio = datas.stream().min(LocalDateTime::compareTo).get().toLocalDate();
            LocalDate fim = datas.stream().max(LocalDateTime::compareTo).get().toLocalDate();
            System.out.printf("%nGrupo %d: %s a %s%n", numero,
                    inicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    fim.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            );
        } else {
            System.out.printf("%nGrupo %d: Sem intervalo válido%n", numero);
        }

        // Mostrar barras ou ausência
        for (int i = 0; i < grupo.length; i++) {
            if (grupo[i] != null) {
                GraficoTexto.mostrarBarra(nomes[i], grupo[i].getValor(), escalas[i]);
            } else {
                System.out.printf("%s: Nenhuma nova medição encontrada%n", nomes[i]);
                System.out.printf("%s: -%n", nomes[i]);
            }
        }

        System.out.println("---");
    }




}
