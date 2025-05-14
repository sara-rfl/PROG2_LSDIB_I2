package service;

import interfaces.EstatisticaVital;

import java.util.List;

public class Estatistica implements EstatisticaVital {
    private List<Double> valores;

    public Estatistica(List<Double> valores) {
        this.valores = valores;
    }

    @Override
    public double calcularMedia(){
        if (valores.isEmpty()) return 0;
        double soma = 0;
        for(double valor: valores){
            soma += valor;
        }
        return soma/valores.size();
    }

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
