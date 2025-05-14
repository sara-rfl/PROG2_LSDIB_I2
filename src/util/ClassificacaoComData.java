package util;


import java.time.LocalDate;

/**
 * Classe auxiliar que representa o resultado da classificação
 * de um sinal vital numa determinada data.
 *
 * Serve para combinar a classificação com a data real do registo
 * (por exemplo: "Normal" em 10/03/2024).
 */
public class ClassificacaoComData {

    // Texto da classificação (ex: "Normal", "Crítico", "Nenhum valor registado")
    private final String classificacao;

    // Data em que o valor foi registado
    private final LocalDate data;

    /**
     * Construtor para criar uma instância com classificação e data.
     *
     * @param classificacao Resultado da análise (ex: "Atenção", "Crítico", etc.)
     * @param data          Data real do registo associado à classificação
     */
    public ClassificacaoComData(String classificacao, LocalDate data) {
        this.classificacao = classificacao;
        this.data = data;
    }

    /**
     * Devolve a classificação associada ao sinal vital.
     *
     * @return String com a classificação
     */
    public String getClassificacao() {
        return classificacao;
    }

    /**
     * Devolve a data do registo do sinal vital.
     *
     * @return Data do registo
     */
    public LocalDate getData() {
        return data;
    }
}

