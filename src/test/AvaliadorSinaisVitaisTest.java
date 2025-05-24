package test;

import org.junit.jupiter.api.Test;
import service.AvaliadorSinaisVitais;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@code AvaliadorSinaisVitais}, que classifica sinais vitais
 * como frequência cardíaca, temperatura corporal e saturação de oxigénio em três categorias:
 * "Normal", "Atenção" e "Crítico".
 */
public class AvaliadorSinaisVitaisTest {

    /**
     * Testa se uma frequência cardíaca dentro dos limites normais
     * é corretamente classificada como "Normal".
     */
    @Test
    public void testFrequenciaCardiacaNormal() {
        String r = AvaliadorSinaisVitais.classificarValor("frequencia cardiaca", 75);
        assertEquals("Normal - Frequência Cardíaca", r);
    }

    /**
     * Testa se uma frequência cardíaca ligeiramente elevada é
     * classificada como "Atenção".
     */
    @Test
    public void testFrequenciaCardiacaAtencao() {
        String r = AvaliadorSinaisVitais.classificarValor("frequencia cardiaca", 110);
        assertEquals("Atenção - Frequência Cardíaca", r);
    }

    /**
     * Testa se uma frequência cardíaca muito elevada é
     * classificada como "Crítico".
     */
    @Test
    public void testFrequenciaCardiacaCritico() {
        String r = AvaliadorSinaisVitais.classificarValor("frequencia cardiaca", 130);
        assertEquals("Crítico - Frequência Cardíaca", r);
    }

    /**
     * Testa se uma temperatura corporal normal é
     * corretamente classificada como "Normal".
     */
    @Test
    public void testTemperaturaNormal() {
        String r = AvaliadorSinaisVitais.classificarValor("Temperatura", 36.8);
        assertEquals("Normal - Temperatura", r);
    }

    /**
     * Testa se uma temperatura ligeiramente elevada é
     * classificada como "Atenção".
     */
    @Test
    public void testTemperaturaAtencao() {
        String r = AvaliadorSinaisVitais.classificarValor("Temperatura", 38.0);
        assertEquals("Atenção - Temperatura", r);
    }

    /**
     * Testa se uma temperatura muito elevada é
     * classificada como "Crítico".
     */
    @Test
    public void testTemperaturaCritico() {
        String r = AvaliadorSinaisVitais.classificarValor("Temperatura", 39.0);
        assertEquals("Crítico - Temperatura", r);
    }

    /**
     * Testa se uma saturação de oxigénio normal é
     * classificada corretamente como "Normal".
     */
    @Test
    public void testSaturacaoNormal() {
        String r = AvaliadorSinaisVitais.classificarValor("Saturação de Oxigénio", 97);
        assertEquals("Normal - Saturação de Oxigénio", r);
    }

    /**
     * Testa se uma saturação de oxigénio ligeiramente baixa é
     * classificada como "Atenção".
     */
    @Test
    public void testSaturacaoAtencao() {
        String r = AvaliadorSinaisVitais.classificarValor("Saturação de Oxigénio", 92);
        assertEquals("Atenção - Saturação de Oxigénio", r);
    }

    /**
     * Testa se uma saturação de oxigénio muito baixa é
     * classificada como "Crítico".
     */
    @Test
    public void testSaturacaoCritico() {
        String r = AvaliadorSinaisVitais.classificarValor("Saturação de Oxigénio", 88);
        assertEquals("Crítico - Saturação de Oxigénio", r);
    }

    /**
     * Testa o comportamento do classificador quando o tipo de sinal vital é desconhecido.
     */
    @Test
    public void testTipoDesconhecido() {
        String r = AvaliadorSinaisVitais.classificarValor("Outro", 50);
        assertEquals("Tipo desconhecido", r);
    }
}
