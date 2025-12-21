package es.upm;

import java.util.Scanner;

/**
 * @author Irene Lombardo Cabrera
 * @author Almudena Moyano Londoño
 *
 * @version 1.0
 *
 * Clase principal de la aplicación que se encarga de arrancar el programa
 */

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        CatalogoActividades catalogo = new CatalogoActividades(Integer.parseInt(args[1]));
        // @TODO: Implementar la lógica principal de la aplicación aquí


    }
}
