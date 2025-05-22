package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ClassificadorPaciente;
import model.Medida;
import model.Paciente;
import model.TecnicoSaude;
import util.ClassificacaoComData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para {@link ClassificadorPaciente}.
 * Verifica se a classificação de sinais vitais por tipo e intervalo de tempo
 * está a funcionar correctamente. Utiliza objetos fictícios de Paciente, Técnico
 * de Saúde e medições de temperatura.
 */
public class ClassificadorPacienteTest {

    @BeforeAll
    public static void setUp() {
        System.out.println("Iniciando testes...");
    }

    /**
     * Testa se o metodo {@code classificarSinaisVitais} devolve corretamente
     * classificações para medições dentro do intervalo de tempo especificado.
     */
    @Test
    public void testClassificarSinaisVitaisDentroDoPeriodo() {
        LocalDateTime agora = LocalDateTime.now();
        Paciente paciente = new Paciente("Paciente Teste", LocalDate.of(2000, 1, 1), 1.70, 65.0);
        TecnicoSaude tecnico = new TecnicoSaude("Tecnico Teste", LocalDate.of(1990, 5, 5), "Enfermeiro");

        List<Medida> medidas = Arrays.asList(
                new model.Temperatura(38.5, agora.minusHours(1), paciente, tecnico),
                new model.Temperatura(36.7, agora.minusHours(2), paciente, tecnico)
        );

        List<ClassificacaoComData> resultado = ClassificadorPaciente.classificarSinaisVitais(
                medidas, "Temperatura", agora.minusHours(3), agora
        );

        assertEquals(2, resultado.size());
        assertEquals(LocalDate.from(agora.minusHours(1)), resultado.get(0).getData());
        assertNotNull(resultado.get(0).getClassificacao());
    }

    /**
     * Testa se o metodo {@code classificarSinaisVitais} ignora corretamente
     * medições que estejam fora do intervalo de tempo especificado.
     */
    @Test
    public void testClassificarSinaisVitaisForaDoPeriodo() {
        LocalDateTime agora = LocalDateTime.now();
        Paciente paciente = new Paciente("Paciente Teste", LocalDate.of(2000, 1, 1), 1.70, 65.0);
        TecnicoSaude tecnico = new TecnicoSaude("Tecnico Teste", LocalDate.of(1990, 5, 5), "Enfermeiro");

        List<Medida> medidas = Arrays.asList(
                new model.Temperatura(38.5, agora.minusDays(2), paciente, tecnico),
                new model.Temperatura(36.7, agora.minusDays(3), paciente, tecnico)
        );

        List<ClassificacaoComData> resultado = ClassificadorPaciente.classificarSinaisVitais(
                medidas, "Temperatura", agora.minusHours(5), agora
        );

        assertEquals(0, resultado.size());
    }

    /**
     * Finaliza os testes da classe ClassificadorPacienteTest.
     * Pode ser usado para libertar recursos ou apresentar mensagens.
     */
    @AfterAll
    public static void tearDown() {
        System.out.println("Finalizando testes...");;
    }
}
