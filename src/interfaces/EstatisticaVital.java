package interfaces;
/**
 * Interface que define os métodos para cálculo de estatísticas sobre valores vitais.
 */
public interface EstatisticaVital {
    /**
     * Calcula a média dos valores.
     *
     * @return Valor médio ou 0 se não houver dados
     */
    double calcularMedia();
    /**
     * Calcula o desvio padrão dos valores.
     *
     * @return Desvio padrão ou 0 se houver menos de dois dados
     */
    double calcularDesvioPadrao();
    /**
     * Obtém o valor mínimo da amostra.
     *
     * @return Valor mínimo ou 0 se a lista estiver vazia
     */
    double calcularMin();
    /**
     * Obtém o valor máximo da amostra.
     *
     * @return Valor máximo ou 0 se a lista estiver vazia
     */
    double calcularMax();
}
