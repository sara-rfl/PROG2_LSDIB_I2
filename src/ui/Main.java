package ui;
import model.Hospital;
import service.DadosERegisto;

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital("Hospital XYZ");
        DadosERegisto.exemplo(hospital);
        MenuPrincipal menu = new MenuPrincipal(hospital);
        menu.menuInicio();
    }
}
