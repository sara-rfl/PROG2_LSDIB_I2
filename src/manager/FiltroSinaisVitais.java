package manager;

import model.Medida;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FiltroSinaisVitais {

    public static List<Medida> filtrarPorTipoEPeriodo(List<Medida> medidas, String tipo,
                                                      LocalDateTime inicio, LocalDateTime fim) {



        List<Medida> filtradas = new ArrayList<>();

        for (Medida m : medidas) {

            String tipoMedida = m.getTipo();
            LocalDateTime dataHora = m.getDataHora();

            if (tipoMedida.trim().equalsIgnoreCase(tipo.trim()) &&
                    (dataHora.isEqual(inicio) || dataHora.isAfter(inicio)) &&
                    (dataHora.isEqual(fim) || dataHora.isBefore(fim))) {

                filtradas.add(m);
            }
        }

        return filtradas;
    }
}
