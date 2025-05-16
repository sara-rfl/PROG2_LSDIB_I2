package util;

import java.time.LocalDate;

public class ClassificacaoComData {
    private final String classificacao;
    private final LocalDate data;

    public ClassificacaoComData(String classificacao, LocalDate data) {
        this.classificacao = classificacao;
        this.data = data;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public String toString() {
        return data + ": " + classificacao;
    }
}
