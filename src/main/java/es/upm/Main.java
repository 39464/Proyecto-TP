package es.upm;

import java.io.IOException;
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
    /** Clase de donde se ejecuta el programa
     *
     * @param args define los distintos enteros necesarios a lo largo del programa: máximo de recursos y comentarios
     *             por actividad, máximo de actividades en catálogo, número de días de viaje, máximo de actividades por
     *             día, y nombre del archivo de donde cargar las actividades (opcional)
     */
    public static void main(String[] args) {
        try {
            if (args.length < 5 || args.length > 6) {
                System.out.println("Número de argumentos incorrecto.\n" +
                        "Uso correcto: <maxRecursosPorActividad>, <maxComentariosPorActividad>, <maxActividadesEnCatalogo>" +
                        ", <numDiasViaje>, <maxActividadesPorDia>, <nombreArchivoActividades (opcional)>");
            }
            Scanner teclado = new Scanner(System.in);
            int maxRecActividad = Integer.parseInt(args[0]);
            int maxComActividad = Integer.parseInt(args[1]);
            int maxActCatalogo = Integer.parseInt(args[2]);
            int numDiasViaje = Integer.parseInt(args[3]);
            int maxActDia = Integer.parseInt(args[4]);
            String nombreArchivo = null;

            CatalogoActividades catalogo = new CatalogoActividades(maxActCatalogo);
            Viaje viaje = new Viaje(numDiasViaje, maxActDia);
            InterfazUsuario interfaz = new InterfazUsuario(catalogo, viaje, maxRecActividad, maxComActividad);

            if (args.length == 6) {
                nombreArchivo = args[5];
            }
            if (nombreArchivo != null) {
                try {
                    catalogo.cargarActividades(nombreArchivo, maxRecActividad, maxComActividad);
                    System.out.println("Actividades cargadas exitosamente desde el archivo");
                } catch (Exception e) {
                    System.out.println("Error al cargar las actividades: " + e.getMessage());
                }
            }
            interfaz.iniciar(teclado);
        } catch (NumberFormatException ex){
            System.out.println("Los números proporcionados como argumento deben ser enteros");
        } catch (Exception e){
            System.out.println("Error al ejecutar: " + e.getMessage());
        }
    }
}
