package test;

import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.Estatistica;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link Estatistica}, que realiza cálculos
 * de média, mínimo e máximo sobre listas de valores de sinais vitais de pacientes.
 *
 * Cada teste simula um conjunto de registos de medidas (temperatura, frequência cardíaca,
 * saturação de oxigénio) e verifica se os cálculos estatísticos estão corretos.
 */
public class EstatisticaTest {

    private static Paciente paciente;
    private static TecnicoSaude tecnico;
    private static LocalDateTime agora;

    /**
     * Configura dados fictícios de paciente e técnico de saúde antes da execução dos testes.
     */
    @BeforeAll
    public static void setUp() {
        paciente = new Paciente("Paciente Teste", LocalDate.of(2000, 1, 1), 1.75, 70);
        tecnico = new TecnicoSaude("Tecnico", LocalDate.of(1985, 5, 10), "Enfermeiro");
        agora = LocalDateTime.now();
    }

    /**
     * Testa o cálculo da média, valor mínimo e máximo da temperatura
     * a partir de três registos.
     */
    @Test
    public void testMediaTemperaturaPaciente() {
        List<Medida> medidas = Arrays.asList(
                new Temperatura(36.5, agora.minusHours(3), paciente, tecnico),
                new Temperatura(38.0, agora.minusHours(2), paciente, tecnico),
                new Temperatura(39.0, agora.minusHours(1), paciente, tecnico)
        );

        List<Double> valores = medidas.stream().map(Medida::getValor).toList();
        Estatistica stat = new Estatistica(valores);

        assertEquals(37.83, stat.calcularMedia(), 0.01); // média ≈ 37.83
        assertEquals(36.5, stat.calcularMin(), 0.001);
        assertEquals(39.0, stat.calcularMax(), 0.001);
    }

    /**
     * Testa o cálculo da média, valor mínimo e máximo da frequência cardíaca
     * com três registos simulados.
     */
    @Test
    public void testMediaFrequenciaPaciente() {
        List<Medida> medidas = Arrays.asList(
                new FrequenciaCardiaca(70, agora.minusMinutes(60), paciente, tecnico),
                new FrequenciaCardiaca(95, agora.minusMinutes(30), paciente, tecnico),
                new FrequenciaCardiaca(120, agora, paciente, tecnico)
        );

        List<Double> valores = medidas.stream().map(Medida::getValor).toList();
        Estatistica estat = new Estatistica(valores);

        assertEquals(95.0, estat.calcularMedia(), 0.01);
        assertEquals(70.0, estat.calcularMin(), 0.001);
        assertEquals(120.0, estat.calcularMax(), 0.001);
    }

    /**
     * Testa o cálculo da média, valor mínimo e máximo da saturação de oxigénio
     * com três valores típicos registados.
     */
    @Test
    public void testMediaSaturacaoPaciente() {
        List<Medida> medidas = Arrays.asList(
                new SaturacaoOxigenio(96.0, agora.minusHours(2), paciente, tecnico),
                new SaturacaoOxigenio(92.0, agora.minusHours(1), paciente, tecnico),
                new SaturacaoOxigenio(98.0, agora, paciente, tecnico)
        );

        List<Double> valores = medidas.stream().map(Medida::getValor).toList();
        Estatistica estat = new Estatistica(valores);

        assertEquals(95.33, estat.calcularMedia(), 0.01);
        assertEquals(92.0, estat.calcularMin(), 0.001);
        assertEquals(98.0, estat.calcularMax(), 0.001);
    }
}
