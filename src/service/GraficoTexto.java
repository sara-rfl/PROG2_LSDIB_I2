package service;

import manager.FiltroSinaisVitais;
import manager.GestorPacientes;
import manager.PeriodoAnalise;
import model.Hospital;
import model.Medida;
import model.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

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


        String[] tipos = {"Frequencia Cardiaca", "Temperatura", "Saturação de Oxigénio"};
        double[] escalas = {
                10.0, //escala para a Frequência Cardíaca --> FC/10
                1.0, // escala para a Temperatura --> TEMP/1.0
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

}
