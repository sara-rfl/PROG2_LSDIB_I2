package ui;

import model.Hospital;

/**
 * Classe principal responsável por iniciar a aplicação de monitorização de pacientes em UCI.
 * Cria uma instância do hospital e inicia o menu principal da interface.
 */
public class Main {

    /**
     * Metodo principal que dá início à execução do programa.
     *
     * @param args argumentos da linha de comandos
     */
    public static void main(String[] args) {
        Hospital hospital = new Hospital("Hospital XYZ");
        MenuPrincipal menu = new MenuPrincipal(hospital);
        menu.menuPrincipal();
    }
}