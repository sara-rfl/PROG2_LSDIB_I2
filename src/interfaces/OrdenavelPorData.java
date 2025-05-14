package interfaces;

import java.time.LocalDate;

/**
 * Interface que define um contrato para objetos que podem ser ordenados por uma data de referência.
 */
public interface OrdenavelPorData {

    /**
     * Retorna a data de referência usada para ordenação.
     *
     * @return Data utilizada como critério de ordenação
     */
    LocalDate getDataReferencia();
}
