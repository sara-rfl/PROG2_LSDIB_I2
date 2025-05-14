package model;

import java.time.LocalDateTime;

public class FrequenciaCardiaca extends Medida {
    public static final double FC_NORMAL_MIN = 60.0;
    public static final double FC_NORMAL_MAX = 100.0;
    public static final double FC_ATENCAO_MAX = 120.0;

    public FrequenciaCardiaca(double valor, LocalDateTime dataHora, Paciente paciente, TecnicoSaude tecnico) {
        super(valor, dataHora, paciente, tecnico);
    }
@Override
public String getTipo() {
    return "Frequencia Cardiaca";
    }

}
