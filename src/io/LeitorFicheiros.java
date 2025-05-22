package io;

import exceptions.*;
import model.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Classe responsável por importar medidas a partir de um ficheiro de texto.
 * Caso pacientes ou técnicos não existam no hospital, são automaticamente criados.
 * As medidas válidas são adicionadas à instância do hospital.
 */
public class LeitorFicheiros {

    /**
     * Lê todas as linhas do ficheiro e tenta importar as medidas.
     * Cada linha mal formatada ou com erro será ignorada e reportada.
     *
     * @param nomeFicheiro nome do ficheiro com as medidas a importar
     * @param hospital instância do hospital onde as medidas serão armazenadas
     */
    public static void importarMedidas(String nomeFicheiro, Hospital hospital) {
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFicheiro))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (!linha.trim().isEmpty()) {
                    processarLinhaMedida(linha, hospital);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao abrir o ficheiro: " + e.getMessage());
        }
    }

    /**
     * Processa uma linha do ficheiro, criando os objetos correspondentes.
     *
     * @param linha linha de texto do ficheiro
     * @param hospital instância onde os dados serão armazenados
     * @throws LinhaMalFormatadaException se a linha não tiver 12 campos
     * @throws ErroProcessarLinhaException se algum campo for inválido
     */
    private static void processarLinhaMedida(String linha, Hospital hospital) {
        String[] partes = linha.split(";");
        if (partes.length != 12) {
            throw new LinhaMalFormatadaException(linha);
        }

        try {
            Paciente paciente = obterOuCriarPaciente(partes, hospital);
            TecnicoSaude tecnico = obterOuCriarTecnico(partes, hospital);
            Medida medida = criarMedida(partes, paciente, tecnico);
            hospital.addMedida(medida);
        } catch (Exception e) {
            throw new ErroProcessarLinhaException(linha, e.getMessage());
        }
    }

    /**
     * Obtém um paciente pelo ID ou cria um novo com os dados fornecidos.
     */
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

    /**
     * Obtém um técnico pelo ID ou cria um novo com os dados fornecidos.
     */
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

    /**
     * Cria a medida correspondente (frequência, temperatura ou saturação).
     *
     * @throws TipoMedidaDesconhecidoException se o tipo for inválido
     */
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
            throw new TipoMedidaDesconhecidoException(tipo);
        }
    }

    /**
     * Remove acentos e normaliza o tipo de medida para facilitar a correspondência.
     *
     * @param tipo nome do tipo vindo do ficheiro (ex: "Frequência")
     * @return tipo sem acentos, em minúsculas
     */
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
