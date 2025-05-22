package test;

import org.junit.jupiter.api.Test;
import service.AvaliadorSinaisVitais;

import static org.junit.jupiter.api.Assertions.*;

public class AvaliadorSinaisVitaisTest {

    @Test
    public void testFrequenciaCardiacaNormal() {
        String r = AvaliadorSinaisVitais.classificarValor("frequencia cardiaca", 75);
        assertEquals("Normal - Frequência Cardíaca", r);
    }

    @Test
    public void testFrequenciaCardiacaAtencao() {
        String r = AvaliadorSinaisVitais.classificarValor("frequencia cardiaca", 110);
        assertEquals("Atenção - Frequência Cardíaca", r);
    }

    @Test
    public void testFrequenciaCardiacaCritico() {
        String r = AvaliadorSinaisVitais.classificarValor("frequencia cardiaca", 130);
        assertEquals("Crítico - Frequência Cardíaca", r);
    }

    @Test
    public void testTemperaturaNormal() {
        String r = AvaliadorSinaisVitais.classificarValor("Temperatura", 36.8);
        assertEquals("Normal - Temperatura", r);
    }

    @Test
    public void testTemperaturaAtencao() {
        String r = AvaliadorSinaisVitais.classificarValor("Temperatura", 38.0);
        assertEquals("Atenção - Temperatura", r);
    }

    @Test
    public void testTemperaturaCritico() {
        String r = AvaliadorSinaisVitais.classificarValor("Temperatura", 39.0);
        assertEquals("Crítico - Temperatura", r);
    }

    @Test
    public void testSaturacaoNormal() {
        String r = AvaliadorSinaisVitais.classificarValor("Saturação de Oxigénio", 97);
        assertEquals("Normal - Saturação de Oxigénio", r);
    }

    @Test
    public void testSaturacaoAtencao() {
        String r = AvaliadorSinaisVitais.classificarValor("Saturação de Oxigénio", 92);
        assertEquals("Atenção - Saturação de Oxigénio", r);
    }

    @Test
    public void testSaturacaoCritico() {
        String r = AvaliadorSinaisVitais.classificarValor("Saturação de Oxigénio", 88);
        assertEquals("Crítico - Saturação de Oxigénio", r);
    }

    // Tipo desconhecido
    @Test
    public void testTipoDesconhecido() {
        String r = AvaliadorSinaisVitais.classificarValor("Outro", 50);
        assertEquals("Tipo desconhecido", r);
    }
}
