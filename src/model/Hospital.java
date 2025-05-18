package model;

import java.util.List;
import java.util.ArrayList;

public class Hospital {
    private String nome;
    private List <Paciente> pacientes;
    private List <TecnicoSaude> tecnicos;
    private List<Medida> medidas;

    public Hospital(String nome) {
        this.nome = nome;
        this.pacientes = new ArrayList<>();
        this.tecnicos = new ArrayList<>();
        this.medidas = new ArrayList<>();
    }
    public void addPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    public void addTecnico(TecnicoSaude tecnico) {
        tecnicos.add(tecnico);
    }

    public void addMedida(Medida medida) {
        medidas.add(medida);
    }

    public List<Medida> getMedidasPorPaciente(Paciente paciente) {
        List<Medida> resultado = new ArrayList<>();
        for (Medida m : medidas) {
            if (m.getPaciente().equals(paciente)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<TecnicoSaude> getTecnicos() {
        return tecnicos;
    }

    public List<Medida> getMedidas() {
        return medidas;
    }

    public Paciente getPacientePorId(int id) {
        for (Paciente p : pacientes) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public TecnicoSaude getTecnicoPorId(int id) {
        for (TecnicoSaude t : tecnicos) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public Medida getUltimaMedidaDoTipo(Paciente paciente, String tipo) {
        Medida ultima = null;

        for (Medida m : this.getMedidas()) {
            if (m.getPaciente().equals(paciente) && m.getClass().getSimpleName().equals(tipo)) {
                if (ultima == null || m.getDataHora().isAfter(ultima.getDataHora())) {
                    ultima = m;
                }
            }
        }

        return ultima;
    }

}
