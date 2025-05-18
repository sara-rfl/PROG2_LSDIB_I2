package model;

import java.time.LocalDate;

/**
 * A classe TecnicoSaude representa um técnico de saúde, subclasse de Pessoa,
 * contendo a categoria profissional como informação adicional.
 */
public class TecnicoSaude extends Pessoa {

    private String categoriaProfissional;

    /**
     * Construtor para a classe TecnicoSaude.
     *
     * @param nome o nome do técnico de saúde
     * @param dataDeNascimento a data de nascimento do técnico de saúde
     * @param categoriaProfissional a categoria profissional do técnico de saúde
     */
    public TecnicoSaude(String nome, LocalDate dataDeNascimento, String categoriaProfissional) {
        super(nome, dataDeNascimento);
        this.categoriaProfissional = categoriaProfissional;
    }

    public TecnicoSaude(String nome, LocalDate dataNascimento, String categoriaProfissional, int id) {
        super(nome, dataNascimento, id);
        this.categoriaProfissional = categoriaProfissional;
    }


    /**
     * Retorna a categoria profissional do técnico de saúde.
     *
     * @return a categoria profissional
     */
    public String getCategoriaProfissional() {
        return categoriaProfissional;
    }

    /**
     * Define a categoria profissional do técnico de saúde.
     *
     * @param categoriaProfissional a nova categoria
     */
    public void setCategoriaProfissional(String categoriaProfissional) {
        this.categoriaProfissional = categoriaProfissional;
    }

    /**
     * Retorna uma representação em formato de string do técnico de saúde.
     *
     * @return string com nome, ID, data de nascimento e categoria profissional
     */
    @Override
    public String toString() {
        return String.format("Técnico: %s (ID: %d, Nasc.: %s) | Categoria: %s",
                getNome(), getId(), getDataNascimento(), categoriaProfissional);
    }
}
