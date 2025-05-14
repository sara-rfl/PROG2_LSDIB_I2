package model;

import java.time.LocalDateTime;

public class Temperatura extends Medida {
    public static final double TEMP_NORMAL_MIN = 36.0;
    public static final double TEMP_NORMAL_MAX = 37.5;
    public static final double TEMP_ATENCAO_MAX = 38.5;

    public Temperatura(double valor, LocalDateTime dataHora, Paciente paciente, TecnicoSaude tecnico) {
        super(valor, dataHora, paciente, tecnico);
    }
    @Override
    public String getTipo() {
        return "Temperatura";
    }

}
