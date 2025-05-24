package util;
/**
 * Classe utilitária para normalização de strings que representam tipos de sinais vitais.
 * Remove acentos e caracteres especiais para facilitar comparações insensíveis a grafia.
 */
public class TipoUtil {

    /**
     * Normaliza um tipo de sinal vital convertendo para minúsculas,
     * removendo acentos e cedilhas.
     *
     * Exemplo: "Frequência Cardíaca" → "frequencia cardiaca"
     *
     * @param tipo string com o tipo original.
     * @return versão normalizada da string.
     */
    public static String normalizar(String tipo) {
        return tipo.trim().toLowerCase()
                .replace("á", "a")
                .replace("à", "a")
                .replace("â", "a")
                .replace("ã", "a")
                .replace("ä", "a")
                .replace("é", "e")
                .replace("ê", "e")
                .replace("ë", "e")
                .replace("í", "i")
                .replace("î", "i")
                .replace("ó", "o")
                .replace("ô", "o")
                .replace("õ", "o")
                .replace("ú", "u")
                .replace("ü", "u")
                .replace("ç", "c");
    }
}

