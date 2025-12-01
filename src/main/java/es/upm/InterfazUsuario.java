package es.upm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class InterfazUsuario {

    public InterfazUsuario(CatalogoActividades catalogo, Viaje viaje, int maxRecursos, int maxComentarios) {
        Scanner scanner = new Scanner(System.in);
        // Crea la interfaz de usuario con el catálogo y viaje proporcionados
    }

    public void iniciar(Scanner scanner) {
        menuPrincipal(scanner);
    }

    private void menuPrincipal(Scanner scanner) {
        boolean seguir = true;
        do{
            mostrarMenu();
            int opcion = scanner.nextInt();
            switch(opcion){
                case 1: agregarActividad(scanner); break;
                case 2: consultarActividad(scanner); break;
                case 3: guardarActividades(scanner); break;
                case 4: cargarActividades(scanner); break;
                case 5: planificarViaje(scanner); break;
                case 6: guardarItinerario(scanner); break;
                case 7: seguir = false; break;
            }
        }while(seguir);
        // Ejecuta el bucle del menú principal hasta que el usuario decida salir
    }

    private void mostrarMenu() {
        System.out.println("---Menú principal---\n");
        System.out.println("1. Agregar Actividad\n " +
                "2. Consultar/Editar Actividad\n" +
                "3. Guardar Actividades\n"+
                "4. Cargar Actividades\n" +
                "5. Planificar Viaje\n" +
                "6. Guardar Itinerario\n" +
                "7. Salir\n");
        System.out.print("Elige una opcion: ");
        // Muestra las opciones del menú principal
    }

    private void agregarActividad(Scanner scanner) {
        System.out.print("Nombre de la actividad: ");
        String nombre = scanner.next();
        System.out.print("Descripcion: ");
        String descripcion = scanner.next();
        System.out.print("Precio (€): ");
        double precio = scanner.nextDouble();
        System.out.print("Duración de la actividad (en minutos): ");
        int duracion = scanner.nextInt();
        System.out.print("Introduzca los recursos (una línea por recurso, 'fin' para terminar): ");
        String recurso;
        do{
            recurso = scanner.next();
        }while(!recurso.equals("fin"));

        System.out.print("Introduzca los comentarios (una línea por comentario, 'fin' para terminar: ");
        String comentario;
        do{
            comentario = scanner.next();
        }while(!comentario.equals("fin"));

        Actividad actividad = new Actividad(nombre, 1, 2);
        if(CatalogoActividades.agregarActividad(actividad)== CatalogoActividades.EXITO){
            System.out.println("Actividad agregada exitosamente");
        } else{

        }
        // Lee los datos de una nueva actividad y la agrega al catálogo
        /* Fíjate en que el usuario debe introducir los recursos y comentarios de la actividad, una línea por cada uno, y escribir
            fin para indicar que ha terminado de introducir los datos. En
            el caso de que la actividad no se haya podido añadir al catálogo,
            se debe mostrar el error No se pueden añadir más
            actividades. */
    }

    private void consultarActividad(Scanner scanner) {
        Actividad actividadBuscada = buscarActividadPorNombre(scanner);
        if(actividadBuscada == null){
            System.out.println("Actividad no encontrada.");
        }
        // Busca una actividad y permite editarla
    }

    private Actividad buscarActividadPorNombre(Scanner scanner) {
        System.out.println("Introduzca el nombre de la actividad: ");
        String nombre = scanner.next();
        // Busca actividades por nombre y permite seleccionar una
        return null; // @todo MODIFICAR PARA DEVOLVER LA ACTIVIDAD SELECCIONADA
    }

    private Actividad seleccionarActividad(Scanner scanner, Actividad[] actividades) {
        // Muestra un listado numerado de actividades y permite elegir una
        return null; // @todo MODIFICAR PARA DEVOLVER LA ACTIVIDAD SELECCIONADA
    }

    private void editarActividad(Scanner scanner, Actividad seleccionada) {
        // Muestra la actividad y permite añadir recursos, comentarios o eliminarla
    }

    private void guardarActividades(Scanner scanner) {
        try {
            System.out.println("Inserte el nombre del archivo donde guardar la actividad: ");
            String nombreArchivo = scanner.next();
            guardarActividadesEnArchivo(nombreArchivo);
        }catch (FileNotFoundException ex){
            System.out.println("No se encontro el archivo.");
        }
        // Lee el nombre del archivo y guarda las actividades del catálogo
    }
    private void guardarActividadesEnArchivo(String nombreArchivo) throws FileNotFoundException {
        PrintWriter out = null;
        try{
            out = new PrintWriter(nombreArchivo);
            CatalogoActividades.guardarActividades(nombreArchivo);
        }catch (IOException e){
            System.out.println("IOException al escribir: "+ e.getMessage());
        }finally{
            if(out != null){
                out.close();
            }
        }
    }

    private void cargarActividades(Scanner scanner) {
        // Lee el nombre del archivo y carga actividades al catálogo
    }

    private void planificarViaje(Scanner scanner) {
        // Muestra el itinerario actual y permite agregar actividades a días específicos
    }

    private void guardarItinerario(Scanner scanner) {
        // Lee el nombre del archivo y guarda el itinerario del viaje
    }
}
