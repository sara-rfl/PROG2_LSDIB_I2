package service;

import interfaces.EstatisticaVital;
import java.util.List;

/**
 * Classe responsável pelo cálculo de estatísticas simples (média,
 * desvio padrão, mínimo e máximo) sobre uma lista de valores numéricos.
 *
 * Esta classe implementa a interface {@link EstatisticaVital} e é usada
 * para calcular medidas de sumário associadas a sinais vitais de pacientes.
 */
public class Estatistica implements EstatisticaVital {

    private List<Double> valores;

    /**
     * Construtor que recebe uma lista de valores numéricos.
     *
     * @param valores lista de valores sobre os quais serão calculadas as estatísticas
     */
    public Estatistica(List<Double> valores) {
        this.valores = valores;
    }

    /**
     * Calcula a média dos valores da lista.
     *
     * @return média dos valores, ou 0 se a lista estiver vazia
     */
    @Override
    public double calcularMedia() {
        if (valores.isEmpty()) return 0;
        double soma = 0;
        for (double valor : valores) {
            soma += valor;
        }
        return soma / valores.size();
    }

    /**
     * Calcula o desvio padrão dos valores da lista.
     *
     * @return desvio padrão dos valores, ou 0 se houver menos de 2 elementos
     */
    @Override
    public double calcularDesvioPadrao() {
        if (valores.size() < 2) return 0;
        double media = calcularMedia();
        double somaQuadrados = 0;
        for (double valor : valores) {
            somaQuadrados += Math.pow(valor - media, 2);
        }
        return Math.sqrt(somaQuadrados / valores.size());
    }

    /**
     * Encontra o menor valor na lista.
     *
     * @return valor mínimo, ou 0 se a lista estiver vazia
     */
    @Override
    public double calcularMin() {
        if (valores.isEmpty()) return 0;
        double minimo = valores.get(0);
        for (double valor : valores) {
            if (valor < minimo) {
                minimo = valor;
            }
        }
        return minimo;
    }

    /**
     * Encontra o maior valor na lista.
     *
     * @return valor máximo, ou 0 se a lista estiver vazia
     */
    @Override
    public double calcularMax() {
        if (valores.isEmpty()) return 0;
        double maximo = valores.get(0);
        for (double valor : valores) {
            if (valor > maximo) {
                maximo = valor;
            }
        }
        return maximo;
    }
}
