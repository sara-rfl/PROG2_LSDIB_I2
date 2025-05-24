package manager;

import model.Medida;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static util.TipoUtil.normalizar;

/**
 * Classe utilitária responsável por filtrar sinais vitais com base
 * no tipo e num intervalo de tempo definido.
 */
public class FiltroSinaisVitais {

    /**
     * Filtra uma lista de medidas, retornando apenas aquelas que correspondem
     * ao tipo especificado (após normalização) e que estejam dentro do intervalo
     * de tempo definido.
     *
     * @param medidas lista completa de medidas a filtrar.
     * @param tipo tipo de sinal vital (ex: "Temperatura", "Frequência Cardíaca").
     * @param inicio instante inicial do intervalo de análise (inclusive).
     * @param fim instante final do intervalo de análise (inclusive).
     * @return lista de medidas que correspondem ao tipo e ao período especificado.
     */
    public static List<Medida> filtrarPorTipoEPeriodo(List<Medida> medidas, String tipo,
                                                      LocalDateTime inicio, LocalDateTime fim) {
        List<Medida> resultado = new ArrayList<>();

        for (Medida m : medidas) {
            String tipoMedida = normalizar(m.getTipo());
            if (tipoMedida.equals(normalizar(tipo)) &&
                    !m.getDataHora().isBefore(inicio) &&
                    !m.getDataHora().isAfter(fim)) {
                resultado.add(m);
            }
        }

        return resultado;
    }
}
