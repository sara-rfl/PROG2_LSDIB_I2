package io;

import model.Hospital;

import java.io.*;

/**
 * Classe utilitária responsável pela serialização e desserialização de objetos do tipo {@code Hospital}.
 * Permite guardar o estado do hospital num ficheiro e carregá-lo posteriormente,
 * garantindo persistência dos dados entre execuções do programa.
 *
 * Esta classe trabalha com ficheiros binários através de {@code ObjectOutputStream} e {@code ObjectInputStream}.
 */
public class Serializador {

    /**
     * Guarda um objeto {@code Hospital} num ficheiro no caminho especificado.
     *
     * @param hospital o objeto a ser guardado
     * @param caminho o caminho do ficheiro onde os dados serão guardados (ex: "hospital.dat")
     */
    public static void guardarHospital(Hospital hospital, String caminho) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(caminho))) {
            oos.writeObject(hospital);
        } catch (IOException e) {
            System.out.println("Erro ao guardar dados: " + e.getMessage());
        }
    }

    /**
     * Carrega um objeto {@code Hospital} de um ficheiro no caminho especificado.
     *
     * @param caminho o caminho do ficheiro a ser lido (ex: "hospital.dat")
     * @return o objeto {@code Hospital} carregado, ou {@code null} se ocorrer um erro
     */
    public static Hospital carregarHospital(String caminho) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(caminho))) {
            return (Hospital) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
            return null;
        }
    }
}
