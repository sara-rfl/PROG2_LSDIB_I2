package exceptions;

/**
 * Exceção lançada quando o tipo de medida lido do ficheiro
 * não é reconhecido (ex: valor inválido ou não suportado).
 */
public class TipoMedidaDesconhecidoException extends RuntimeException {

  /**
   * Constrói a exceção com o tipo de medida inválido.
   *
   * @param tipo o tipo de medida não reconhecido
   */
  public TipoMedidaDesconhecidoException(String tipo) {
    super("Tipo de medida desconhecido: " + tipo);
  }
}
