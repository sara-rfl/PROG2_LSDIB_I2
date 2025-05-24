package manager;

import model.Medida;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static util.TipoUtil.normalizar;

public class FiltroSinaisVitais {

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
