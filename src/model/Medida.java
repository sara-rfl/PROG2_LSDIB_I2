package model;

import java.time.LocalDateTime;

public abstract class Medida {
    protected double valor;
    protected LocalDateTime dataHora;
    protected Paciente paciente;
    protected TecnicoSaude tecnico;

    public Medida(double valor, LocalDateTime dataHora, Paciente paciente, TecnicoSaude tecnico) {
        this.valor = valor;
        this.dataHora = dataHora;
        this.paciente = paciente;
        this.tecnico = tecnico;
    }
    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public TecnicoSaude getTecnico() {
        return tecnico;
    }
    public abstract String getTipo();

}

