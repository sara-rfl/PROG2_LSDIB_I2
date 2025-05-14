package interfaces;

/**
 * Interface que define um contrato para qualquer entidade que possa ser classificada.
 */
public interface Classificavel {

    /**
     * Obtém a classificação associada ao objeto.
     *
     * @return Uma string que representa a classificação (ex: "Normal", "Atenção", "Crítico")
     */
    String getClassificacao();
}
