package exceptions;

/**
 * Exceção lançada quando ocorre um erro ao tentar processar
 * uma linha do ficheiro, como parsing incorreto de valores.
 */
public class ErroProcessarLinhaException extends RuntimeException {

  /**
   * Constrói a exceção com a linha em erro e a respetiva causa.
   *
   * @param linha  a linha do ficheiro que causou o erro
   * @param causa  descrição do erro ocorrido
   */
  public ErroProcessarLinhaException(String linha, String causa) {
    super("Erro a processar linha: " + linha + "\nMotivo: " + causa);
  }
}
