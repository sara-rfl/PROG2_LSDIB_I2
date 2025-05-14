package interfaces;

import java.time.LocalDate;

/**
 * Interface que define os métodos essenciais para qualquer pessoa,
 * como nome e data de nascimento.
 */
public interface PessoaInterface {

    /**
     * Obtém o nome da pessoa.
     *
     * @return Nome completo da pessoa
     */
    String getNome();

    /**
     * Obtém a data de nascimento da pessoa.
     *
     * @return Data de nascimento em formato {@code LocalDate}
     */
    LocalDate getDataNascimento();
}

