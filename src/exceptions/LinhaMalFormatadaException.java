package exceptions;

/**
 * Exceção lançada quando uma linha do ficheiro de entrada
 * não contém o número esperado de campos.
 */
public class LinhaMalFormatadaException extends RuntimeException {

  /**
   * Constrói a exceção com a linha mal formatada.
   *
   * @param linha a linha do ficheiro que causou o erro
   */
  public LinhaMalFormatadaException(String linha) {
    super("Linha mal formatada (esperados 12 campos): " + linha);
  }
}
