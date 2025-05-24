package model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe que representa um hospital com listas de pacientes, técnicos de saúde
 * e medidas registadas. É responsável por armazenar e fornecer acesso aos
 * dados clínicos registados no sistema.
 * Esta classe é serializável, permitindo que os dados sejam guardados.
 */
public class Hospital implements Serializable {

    private String nome;
    private List<Paciente> pacientes;
    private List<TecnicoSaude> tecnicos;
    private List<Medida> medidas;

    /**
     * Construtor do hospital.
     *
     * @param nome Nome do hospital
     */
    public Hospital(String nome) {
        this.nome = nome;
        this.pacientes = new ArrayList<>();
        this.tecnicos = new ArrayList<>();
        this.medidas = new ArrayList<>();
    }

    /**
     * Adiciona um paciente à lista de pacientes do hospital.
     *
     * @param paciente Paciente a adicionar
     */
    public void addPaciente(Paciente paciente) {
        pacientes.add(paciente);
    }

    /**
     * Adiciona um técnico de saúde à lista de técnicos do hospital.
     *
     * @param tecnico Técnico de saúde a adicionar
     */
    public void addTecnico(TecnicoSaude tecnico) {
        tecnicos.add(tecnico);
    }

    /**
     * Adiciona uma medida à lista de medidas registadas no hospital.
     *
     * @param medida Medida a adicionar
     */
    public void addMedida(Medida medida) {
        medidas.add(medida);
    }

    /**
     * Devolve todas as medidas registadas para um determinado paciente.
     *
     * @param paciente Paciente cujas medidas serão procuradas
     * @return Lista de medidas associadas ao paciente
     */
    public List<Medida> getMedidasPorPaciente(Paciente paciente) {
        List<Medida> resultado = new ArrayList<>();
        for (Medida m : medidas) {
            if (m.getPaciente().equals(paciente)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    /**
     * Devolve a lista de pacientes do hospital.
     *
     * @return Lista de pacientes
     */
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    /**
     * Devolve a lista de técnicos de saúde do hospital.
     *
     * @return Lista de técnicos
     */
    public List<TecnicoSaude> getTecnicos() {
        return tecnicos;
    }

    /**
     * Devolve a lista de todas as medidas registadas no hospital.
     *
     * @return Lista de medidas
     */
    public List<Medida> getMedidas() {
        return medidas;
    }

    /**
     * Procura um paciente com um dado ID.
     *
     * @param id Identificador do paciente
     * @return Paciente com o ID correspondente, ou {@code null} se não existir
     */
    public Paciente getPacientePorId(int id) {
        for (Paciente p : pacientes) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Procura um técnico de saúde com um dado ID.
     *
     * @param id Identificador do técnico
     * @return Técnico com o ID correspondente, ou {@code null} se não existir
     */
    public TecnicoSaude getTecnicoPorId(int id) {
        for (TecnicoSaude t : tecnicos) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    /**
     * Obtém a última medida de um tipo específico para um determinado paciente.
     *
     * @param paciente Paciente alvo
     * @param tipo Nome da classe do tipo da medida (ex: "Temperatura", "FrequenciaCardiaca")
     * @return Última medida do tipo indicado, ou {@code null} se não existir
     */
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