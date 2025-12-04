package es.upm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class InterfazUsuario {
    public CatalogoActividades catalogo;
    public Viaje viaje;
    public int maxRecursos;
    public int maxComentarios;
    public int maxActividades;

    public InterfazUsuario(CatalogoActividades catalogo, Viaje viaje, int maxRecursos, int maxComentarios) {
        this.viaje = viaje;
        this.catalogo = catalogo;
        this.maxRecursos = maxRecursos;
        this.maxComentarios = maxComentarios;
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
        String nombre = Utilidades.leerCadena(scanner,"Nombre de la actividad: ");
        String descripcion = Utilidades.leerCadena(scanner,"Descripcion: ");
        double precio = Utilidades.leerDouble(scanner, "Precio (€): ", 0.0, Double.MAX_VALUE);
        int duracion = Utilidades.leerNumero(scanner, "Duración de la actividad (en minutos): ", 0, Integer.MAX_VALUE);

        Actividad nueva = new Actividad(nombre, this.maxRecursos, this.maxComentarios);
        nueva.setDescripcion(descripcion);
        nueva.setPrecio(precio);
        nueva.setDuracionMinutos(duracion);
        System.out.print("Introduzca los recursos (una línea por recurso, 'fin' para terminar): ");
        String[] recursos = new String[this.maxRecursos];
        for(int i = 0; i <= this.maxRecursos && !(scanner.nextLine().equals("fin")); i++){
            recursos[i] = scanner.nextLine();
        }

        System.out.print("Introduzca los comentarios (una línea por comentario, 'fin' para terminar: ");
        String [] comentarios = new String[this.maxComentarios];
        for(int i = 0; i <= this.maxRecursos && !(scanner.nextLine().equals("fin")); i++){
            comentarios[i] = scanner.nextLine();
        }

        CatalogoActividades catalogo = new CatalogoActividades(maxActividades);
        if(catalogo.agregarActividad(nueva)== CatalogoActividades.EXITO){
            System.out.println("Actividad agregada exitosamente");
        } else{
            System.out.println("No se pudo agregar la actividad");
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
        String nombre =  Utilidades.leerCadena(scanner,"Nombre de la actividad: ");
        Actividad[] buscadas = catalogo.buscarActividadPorNombre(nombre);
        System.out.println("Actividades encontradas:");
        for(int i = 0; i < buscadas.length; i++){
            System.out.println(i+". " + buscadas[i].getNombre());
        }
        int opcion = Utilidades.leerNumero(scanner, "Introduzca una opcion: ", 0, buscadas.length-1);

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
            String nombreArchivo = Utilidades.leerCadena(scanner, "Nombre del archivo donde guardar la actividad: ");
            guardarActividadesEnArchivo(nombreArchivo);
        }catch (FileNotFoundException ex){
            System.out.println("No se encontro el archivo.");
        }
        // Lee el nombre del archivo y guarda las actividades del catálogo
    }

    private void guardarActividadesEnArchivo(String nombreArchivo) throws FileNotFoundException {
        CatalogoActividades catalogo = new CatalogoActividades(2);
        try{
            catalogo.guardarActividades(nombreArchivo);
        }catch (IOException e){
            System.out.println("IOException al escribir: "+ e.getMessage());
        }
    }

    private void cargarActividades(Scanner scanner) {
        String nombreArchivo = Utilidades.leerCadena(scanner, "Nombre del archivo del que cargar la actividad: ");

        // Lee el nombre del archivo y carga actividades al catálogo
    }

    private void planificarViaje(Scanner scanner) {
        // Muestra el itinerario actual y permite agregar actividades a días específicos
    }

    private void guardarItinerario(Scanner scanner) {
        // Lee el nombre del archivo y guarda el itinerario del viaje
    }
}
