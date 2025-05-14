package model;

import java.time.LocalDateTime;

public class SaturacaoOxigenio extends Medida {

    public static final double SAT_NORMAL_MIN = 95.0;
    public static final double SAT_ATENCAO_MIN = 90.0;

    public SaturacaoOxigenio(double valor, LocalDateTime dataHora, Paciente paciente, TecnicoSaude tecnico) {
        super(valor, dataHora, paciente, tecnico);
    }
    @Override
    public String getTipo() {
        return "Saturação de Oxigénio";
    }

}