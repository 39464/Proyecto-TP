package es.upm;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args.length < 4 || args.length > 6){
            System.out.println("Número de argumentos incorrecto.\n" +
                    "Uso correcto: <maxRecursosPorActividad>, <maxComentariosPorActividad>, <maxActividadesEnCatalogo>" +
                    ", <numDiasViaje>, <maxActividadesPorDia>, <nombreArchivoActividades (opcional)>");
        }

        Scanner teclado = new Scanner(System.in);
        CatalogoActividades catalogo = new CatalogoActividades(Integer.parseInt(args[2]));
        Viaje viaje = new Viaje();
        // @TODO: Implementar la lógica principal de la aplicación aquí
    }
}
