package util;

public class TipoUtil {

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

