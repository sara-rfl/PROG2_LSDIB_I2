package service;

import model.Hospital;

import java.io.*;

public class Serializador {

    public static void guardarHospital(Hospital hospital, String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(hospital);
            System.out.println("Dados guardados com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao guardar dados: " + e.getMessage());
        }
    }

    public static Hospital carregarHospital(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            return (Hospital) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            return null;
        }
    }
}
