package io;

import model.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Classe responsável por importar medidas de um ficheiro de texto.
 */
public class LeitorFicheiros {

    /**
     * Importa medidas a partir de um ficheiro.
     * Se o paciente ou técnico não existirem, são criados com dados fictícios.
     *
     * @param nomeFicheiro caminho do ficheiro de texto
     * @param hospital instância do hospital onde as medidas serão adicionadas
     */
    public static void importarMedidas(String nomeFicheiro, Hospital hospital) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFicheiro))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                try {
                    processarLinhaMedida(linha, hospital);
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println("Erro na linha: " + linha);
                    System.out.println("Motivo: " + e.getMessage());
                }
            }
            System.out.println("Importação concluída com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao abrir o ficheiro: " + e.getMessage());
        }
    }

    private static void processarLinhaMedida(String linha, Hospital hospital) {
        if (linha.trim().isEmpty()) return;

        String[] partes = linha.split(";");
        if (partes.length != 12) {
            throw new IllegalArgumentException("Linha mal formatada (esperados 12 campos): " + linha);
        }

        try {
            Paciente paciente = obterOuCriarPaciente(partes, hospital);
            TecnicoSaude tecnico = obterOuCriarTecnico(partes, hospital);
            Medida medida = criarMedida(partes, paciente, tecnico);
            hospital.addMedida(medida);

        } catch (Exception e) {
            throw new IllegalArgumentException("Erro a processar linha: " + linha + "\nMotivo: " + e.getMessage());
        }
    }


    private static Paciente obterOuCriarPaciente(String[] partes, Hospital hospital) {
        int id = Integer.parseInt(partes[0]);
        String nome = partes[1];
        LocalDate nascimento = LocalDate.parse(partes[2]);
        double altura = Double.parseDouble(partes[3]);
        double peso = Double.parseDouble(partes[4]);

        Paciente p = hospital.getPacientePorId(id);
        if (p == null) {
            p = new Paciente(nome, nascimento, altura, peso, id);
            hospital.addPaciente(p);
        }
        return p;
    }

    private static TecnicoSaude obterOuCriarTecnico(String[] partes, Hospital hospital) {
        int id = Integer.parseInt(partes[5]);
        String nome = partes[6];
        LocalDate nascimento = LocalDate.parse(partes[7]);
        String categoria = partes[8];

        TecnicoSaude t = hospital.getTecnicoPorId(id);
        if (t == null) {
            t = new TecnicoSaude(nome, nascimento, categoria, id);
            hospital.addTecnico(t);
        }
        return t;
    }

    private static Medida criarMedida(String[] partes, Paciente paciente, TecnicoSaude tecnico) {
        String tipo = normalizarTipo(partes[9].trim());
        double valor = Double.parseDouble(partes[10]);
        LocalDateTime dataHora = LocalDateTime.parse(partes[11]);

        if (tipo.contains("frequencia")) {
            return new FrequenciaCardiaca(valor, dataHora, paciente, tecnico);
        } else if (tipo.contains("temperatura")) {
            return new Temperatura(valor, dataHora, paciente, tecnico);
        } else if (tipo.contains("saturacao")) {
            return new SaturacaoOxigenio(valor, dataHora, paciente, tecnico);
        } else {
            throw new IllegalArgumentException("Tipo de medida desconhecido: " + tipo);
        }
    }

    private static String normalizarTipo(String tipo) {
        return tipo.toLowerCase()
                .replace("ã", "a")
                .replace("ç", "c")
                .replace("é", "e")
                .replace("ê", "e")
                .replace("á", "a")
                .replace("à", "a")
                .replace("ó", "o")
                .replace("õ", "o")
                .replace("í", "i")
                .replace("ú", "u")
                .replace("â", "a")
                .replace("ô", "o");
    }
}
