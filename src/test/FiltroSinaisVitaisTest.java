package test;

import manager.FiltroSinaisVitais;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a classe {@link FiltroSinaisVitais}.
 * Testa o correto funcionamento da filtragem de medidas por tipo e por intervalo de tempo.
 */
public class FiltroSinaisVitaisTest {

    private static Paciente paciente;
    private static TecnicoSaude tecnico;
    private static LocalDateTime agora;

    /**
     * Inicializa os objetos comuns a todos os testes: paciente, técnico e instante atual.
     */
    @BeforeAll
    public static void setUp() {
        paciente = new Paciente("Paciente Teste", LocalDate.of(1990, 1, 1), 1.75, 70);
        tecnico = new TecnicoSaude("Tecnico Teste", LocalDate.of(1980, 6, 15), "Enfermeiro");
        agora = LocalDateTime.now();
    }

    /**
     * Testa se o metodo filtra corretamente apenas as medidas do tipo "Temperatura"
     * dentro de um intervalo de tempo válido.
     */
    @Test
    public void testFiltrarPorTipoEPeriodo_Temperatura() {
        List<Medida> medidas = Arrays.asList(
                new Temperatura(37.0, agora.minusDays(3), paciente, tecnico),
                new Temperatura(38.0, agora.minusDays(1), paciente, tecnico),
                new FrequenciaCardiaca(90, agora.minusDays(1), paciente, tecnico)
        );

        LocalDateTime inicio = agora.minusDays(2);
        LocalDateTime fim = agora;

        List<Medida> resultado = FiltroSinaisVitais.filtrarPorTipoEPeriodo(medidas, "Temperatura", inicio, fim);
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0) instanceof Temperatura);
    }

    /**
     * Testa se o metodo retorna uma lista vazia quando não há medidas dentro do intervalo de tempo.
     */
    @Test
    public void testFiltrarPorTipoEPeriodo_SemResultados() {
        List<Medida> medidas = Arrays.asList(
                new SaturacaoOxigenio(96, agora.minusDays(5), paciente, tecnico),
                new FrequenciaCardiaca(80, agora.minusDays(4), paciente, tecnico)
        );

        LocalDateTime inicio = agora.minusDays(2);
        LocalDateTime fim = agora;

        List<Medida> resultado = FiltroSinaisVitais.filtrarPorTipoEPeriodo(medidas, "Saturação de Oxigénio", inicio, fim);
        assertTrue(resultado.isEmpty());
    }

    /**
     * Testa se o metodo considera corretamente uma medida cujo instante de registo está exatamente
     * nos limites inferior e superior do intervalo.
     */
    @Test
    public void testFiltrarPorTipoEPeriodo_FrequenciaCardiacaNoLimite() {
        LocalDateTime exata = agora.minusDays(1);

        List<Medida> medidas = List.of(
                new FrequenciaCardiaca(70, exata, paciente, tecnico)
        );

        List<Medida> resultado = FiltroSinaisVitais.filtrarPorTipoEPeriodo(medidas, "Frequência Cardíaca", exata, exata);
        assertEquals(1, resultado.size());
        assertEquals(70.0, resultado.get(0).getValor(), 0.001);
    }
}
