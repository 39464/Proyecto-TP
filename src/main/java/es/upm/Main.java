package es.upm;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        System.out.println("---Menú principal---\n");
        System.out.println("1. Agregar Actividad\n " +
                "2. Consultar/Editar Actividad\n" +
                "3. Guardar Actividades\n"+
                "4. Cargar Actividades\n" +
                "5. Planificar Viaje\n" +
                "6. Guardar Itinerario\n" +
                "7. Salir\n");
        int opcion = teclado.nextInt();
        // @TODO: Implementar la lógica principal de la aplicación aquí
    }
}
