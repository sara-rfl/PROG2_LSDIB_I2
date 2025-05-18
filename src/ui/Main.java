package ui;

import model.Hospital;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital("Hospital XYZ");
        MenuPrincipal menu = new MenuPrincipal(hospital);
        MenuPrincipal.MenuInicial menuInicial = menu.new MenuInicial(hospital, new Scanner(System.in));
        menuInicial.menuPrincipal();
    }
}
