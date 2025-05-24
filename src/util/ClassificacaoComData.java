package util;

import java.time.LocalDate;
/**
 * Representa a classificação de um sinal vital associada a uma data específica.
 * Utilizada para registar a evolução dos estados clínicos ao longo do tempo.
 */
public class ClassificacaoComData {
    private final String classificacao;
    private final LocalDate data;

    /**
     * Construtor da classe.
     *
     * @param classificacao classificação atribuída (ex: "Normal", "Crítico - Temperatura").
     * @param data data da medição correspondente à classificação.
     */
    public ClassificacaoComData(String classificacao, LocalDate data) {
        this.classificacao = classificacao;
        this.data = data;
    }

    /**
     * Obtém a classificação atribuída.
     *
     * @return a string com a classificação.
     */
    public String getClassificacao() {
        return classificacao;
    }

    /**
     * Obtém a data da medição classificada.
     *
     * @return objeto {@link LocalDate} representando a data.
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Representação textual da classificação com a data.
     *
     * @return string no formato "data: classificação".
     */
    @Override
    public String toString() {
        return data + ": " + classificacao;
    }
}
