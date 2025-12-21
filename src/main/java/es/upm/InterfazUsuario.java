package es.upm;

import java.io.*;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * @author Irene Lombardo Cabrera
 * @author Almudena Moyano Londoño
 *
 * @version 1.0
 *
 * Gestiona la interacción con el usuario a través de un menú en consola.
 */

public class InterfazUsuario {
    public CatalogoActividades catalogo;
    public Viaje viaje;
    public int maxRecursos;
    public int maxComentarios;
    public int maxActividades;

    /**
     * Constructor del objeto InterfazUsuario con el catálogo y viaje proporcionados.
     *
     * @param catalogo Objeto CatálogoActividades que recoge todas las actividades.
     * @param viaje Objeto Viaje que recoge el itinerario.
     * @param maxRecursos Número máximo de recursos que puede haber por actividad.
     * @param maxComentarios Número máximo de comentarios que puede haber por actividad.
     */
    public InterfazUsuario(CatalogoActividades catalogo, Viaje viaje, int maxRecursos, int maxComentarios) {
        this.viaje = viaje;
        this.catalogo = catalogo;
        this.maxRecursos = maxRecursos;
        this.maxComentarios = maxComentarios;
    }

    /**
     * Inicia la interfaz de usuario.
     *
     * @param scanner Objeto Scanner que lee la entrada de teclado.
     */
    public void iniciar(Scanner scanner) {
        menuPrincipal(scanner);
    }

    /**
     * Muestra un menú con opciones y permite al usuario elegir una, hasta que el usuario decida salir.
     *
     * @param scanner Objeto Scanner que lee una opción.
     */
    private void menuPrincipal(Scanner scanner) {
        boolean seguir = true;
        do {
            mostrarMenu();
            String input = scanner.nextLine();
            int opcion;
            try {
                opcion = Integer.parseInt(input);
            } catch(NumberFormatException e){
                opcion = -1;
                System.out.println("No es una opción válida.");
            }
            switch(opcion) {
                case 1: agregarActividad(scanner); break;
                case 2: consultarActividad(scanner); break;
                case 3: guardarActividades(scanner); break;
                case 4: cargarActividades(scanner); break;
                case 5: planificarViaje(scanner); break;
                case 6: guardarItinerario(scanner); break;
                case 7: seguir = false; break;
            }
        } while(seguir);
    }

    /**
     * Muestra las opciones del menú principal.
     */
    private void mostrarMenu() {
        System.out.println("\n---Menú principal---\n");
        System.out.println("1. Agregar Actividad\n" +
                "2. Consultar/Editar Actividad\n" +
                "3. Guardar Actividades\n"+
                "4. Cargar Actividades\n" +
                "5. Planificar Viaje\n" +
                "6. Guardar Itinerario\n" +
                "7. Salir\n");
        System.out.println("Elige una opcion: ");
    }

    /**
     * Lee los datos de una nueva actividad y la agrega al catálogo.
     *
     * @param scanner Objeto Scanner que lee la entrada por teclado.
     */
    private void agregarActividad(Scanner scanner) {
        String nombre = Utilidades.leerCadena(scanner,"Nombre de la actividad: ");
        String descripcion = Utilidades.leerCadena(scanner,"Descripción: ");
        double precio = Utilidades.leerDouble(scanner, "Precio (€): ", 0.0, Double.MAX_VALUE);
        int duracion = Utilidades.leerNumero(scanner, "Duración (minutos): ", 0, Integer.MAX_VALUE);

        Actividad nueva = new Actividad(nombre, this.maxRecursos, this.maxComentarios);
        nueva.setDescripcion(descripcion);
        nueva.setPrecio(precio);
        nueva.setDuracionMinutos(duracion);

        scanner.nextLine();
        System.out.print("Introduce los recursos (una línea por recurso, escribe 'fin' para terminar):");
        String resultadoR = "";
        while(!resultadoR.equals("fin")) {
            resultadoR = Utilidades.leerCadena(scanner, "");
            if(!resultadoR.equals("fin")) {
                int recursos = nueva.agregarRecurso(resultadoR);
                if(recursos == Actividad.ERROR_RECURSOS_COMPLETOS){
                    System.out.println("No se pueden añadir más recursos");
                    resultadoR = "fin";
                }else if(recursos == Actividad.ERROR_VALOR_INVALIDO) {
                    System.out.println("El recurso es invalido");
                    resultadoR = "fin";
                }
            }
        }

        System.out.print("Introduce los comentarios (una línea por comentario, escribe 'fin' para terminar):");
        String resultadoC = "";
        while(!resultadoC.equals("fin")) {
            resultadoC = Utilidades.leerCadena(scanner, "");
            if(!resultadoC.equals("fin")) {
                int comentarios = nueva.agregarComentario(resultadoC);
                if(comentarios == Actividad.ERROR_COMENTARIOS_COMPLETOS) {
                    System.out.println("No se pueden añadir más comentarios");
                    resultadoC = "fin";
                } else if(comentarios == Actividad.ERROR_VALOR_INVALIDO) {
                    System.out.println("El comentario es invalido");
                    resultadoC = "fin";
                }
            }
        }

        if(catalogo.agregarActividad(nueva)== CatalogoActividades.EXITO){
            System.out.println("¡Actividad agregada exitosamente!");
        } else {
            System.out.println("No se pueden añadir más actividades.");
        }
    }

    /**
     * Permite buscar una actividad y editarla.
     *
     * @param scanner Objeto Scanner que lee la entrada por teclado.
     */
    private void consultarActividad(Scanner scanner) {
        Actividad actividadBuscada = buscarActividadPorNombre(scanner);
        if(actividadBuscada == null) {
            System.out.println("Actividad no encontrada.");
        } else {
            editarActividad(scanner, actividadBuscada);
        }
    }

    /**
     * Busca actividades por nombre dentro del catálogo, crea un array con actividades (llamando a seleccionarActividad)
     * que coincidan y permite seleccionar una.
     *
     * @param scanner Objeto Scanner que lee la entrada por teclado.
     * @return Actividad cuyo nombre contiene el texto introducido por teclado o null si no se eligió ninguna.
     */
    private Actividad buscarActividadPorNombre(Scanner scanner) {
        String nombre = Utilidades.leerCadena(scanner, "Introduce el texto de la actividad a buscar (-FIN- para volver): ");
        if (nombre.equals("-FIN-")){
            return null;
        }

        Actividad[] buscadas = catalogo.buscarActividadPorNombre(nombre);
        if (buscadas.length == 0){
            return null;
        }

        return seleccionarActividad(scanner, buscadas);
    }

    /**
     * Muestra un listado numerado de actividades y permite elegir una.
     *
     * @param scanner Objeto Scanner que lee la opción.
     * @param actividades Array de objetos Actividad cuyo nombre coincide con el nombre introducido en el
     *                    metodo buscarActividadPorNombre.
     * @return Actividad elegida por el usuario.
     */
    private Actividad seleccionarActividad(Scanner scanner, Actividad[] actividades) {
        System.out.println("Actividades encontradas:");
        for(int i = 0; i < actividades.length; i++) {
            if (actividades[i] != null) {
                System.out.println((i + 1) + ". " + actividades[i].getNombre());
            }
        }
        int opcion = Utilidades.leerNumero(scanner, "Elija una actividad: ", 1, actividades.length);
        return actividades[opcion-1];
    }

    /**
     * Muestra un menú con opciones y permite añadir recursos/comentarios a la actividad o eliminar la actividad.
     *
     * @param scanner Objeto Scanner que lee la opción.
     * @param seleccionada Actividad que se desea modificar.
     */
    private void editarActividad(Scanner scanner, Actividad seleccionada) {
        System.out.println(seleccionada.toString());
        System.out.println("1. Añadir recurso\n2. Añadir comentario\n3. Eliminar actividad\n4. Volver");
        int opcion = Utilidades.leerNumero(scanner, "Elija una opción:\n", 1, 4);
        switch(opcion) {
            case 1:
                System.out.print("Introduce el recurso a añadir: ");
                switch(seleccionada.agregarRecurso(scanner.nextLine())) {
                    case Actividad.ERROR_VALOR_INVALIDO:
                        System.out.println("Valor inválido.");
                        break;
                    case Actividad.ERROR_RECURSOS_COMPLETOS:
                        System.out.println("Error, recursos completos");
                        break;
                    case Actividad.EXITO:
                        System.out.println("Recurso guardado con éxito");
                        break;
                }
                break;
            case 2:
                System.out.print("Introduce el comentario a añadir: ");
                switch(seleccionada.agregarComentario(scanner.nextLine())) {
                    case Actividad.ERROR_VALOR_INVALIDO:
                        System.out.println("Valor inválido.");
                        break;
                    case Actividad.ERROR_COMENTARIOS_COMPLETOS:
                        System.out.println("Error, comentarios completos");
                        break;
                    case Actividad.EXITO:
                        System.out.println("Comentario guardado con éxito");
                        break;
                }
                break;
            case 3:
                if(!this.catalogo.eliminarActividad(seleccionada)) System.out.println("No se pudo eliminar la actividad");
                else System.out.println("Actividad eliminada.");
                break;
            case 4:
                break;
        }
    }

    /**
     * Solicita nombre del archivo y guarda las actividades del catálogo en un archivo, llamando a guardarActividadesEnArchivo.
     *
     * @param scanner Objeto Scanner que lee la entrada por teclado.
     */
    private void guardarActividades(Scanner scanner) {
        try {
            String nombreArchivo = Utilidades.leerCadena(scanner, "Archivo donde guardar las actividades: ");
            guardarActividadesEnArchivo(nombreArchivo);
        } catch (FileNotFoundException ex){
            System.out.println("No se encontro el archivo.");
        }
    }

    /**
     * Llama al metodo guardarActividades de la clase CatalogoActividades para guardar el catálogo del objeto en un archivo.
     *
     * @param nombreArchivo String que indica el nombre del archivo.
     * @throws FileNotFoundException
     */
    private void guardarActividadesEnArchivo(String nombreArchivo) throws FileNotFoundException {
        CatalogoActividades catalogo = new CatalogoActividades(2);
        try {
            catalogo.guardarActividades(nombreArchivo);
        } catch (IOException e){
            System.out.println("IOException al escribir: "+ e.getMessage());
        }
    }

    /**
     * Lee el nombre de un archivo y llama a cargarActividadesDesdeArchivo para crear un nuevo catálogo.
     *
     * @param scanner Objeto Scanner que lee la entrada por teclado.
     */
    private void cargarActividades(Scanner scanner) {
        String nombreArchivo = Utilidades.leerCadena(scanner, "Archivo de donde cargar las actividades: ");
        try {
            cargarActividadesDesdeArchivo(nombreArchivo);
            System.out.println("Actividades cargadas desde "+ nombreArchivo);
        } catch(FileNotFoundException ex){
            System.out.println("No se encontró el archivo.");
        }
    }

    /**
     * Carga las actividades dentro de un archivo a un nuevo catálogo, mediante un BufferedReader.
     *
     * @param nombreArchivo String que indica el nombre del archivo.
     * @throws FileNotFoundException
     */
    private void cargarActividadesDesdeArchivo(String nombreArchivo) throws FileNotFoundException {
        BufferedReader in;
        try {
            in = new BufferedReader(new FileReader(nombreArchivo));
            int cont = 0;
            while(cont <= this.maxActividades){
                Actividad nueva = Actividad.fromBufferedReader(in, this.maxRecursos, this.maxComentarios);
                this.catalogo.agregarActividad(nueva);
                cont++;
                if(catalogo.agregarActividad(nueva) == CatalogoActividades.EXITO)
                    System.out.println("Actividades cargadas desde "+nombreArchivo);
            }
        } catch (IOException e){
            System.out.println("Error al cargar las actividades.");
        }
    }

    /**
     * Muestra el itinerario actual y permite agregar actividades a días específicos.
     *
     * @param scanner Objeto Scanner que lee los datos.
     */
    private void planificarViaje(Scanner scanner) {
        System.out.println("Planificación del viaje:");
        System.out.println(viaje.toString());

        int dia = Utilidades.leerNumero(scanner, "Introduce el día del viaje (1-" + viaje.getNumDias() + "): ", 1, viaje.getNumDias());
        String hora = Utilidades.leerHora(scanner, "Introduce la hora de inicio (HH:MM): ");

        Actividad actividad = buscarActividadPorNombre(scanner);

        if (actividad != null) {
            int resultado = viaje.agregarActividad(dia - 1, actividad, hora);
            switch (resultado) {
                case Viaje.EXITO:
                    System.out.println("Actividad planificada para el día " + dia + " a las " + hora);
                    break;
                case Viaje.ERROR_DIA_COMPLETO:
                    System.out.println("No se pueden agregar más actividades a este día.");
                    break;
                case Viaje.ERROR_SOLAPAMIENTO:
                    System.out.println("La actividad se solapa con otra actividad ya planificada.");
                    break;
                case Viaje.ERROR_DIA_INVALIDO:
                    System.out.println("Día inválido.");
                    break;
            }
        }
    }

    /**
     * Lee el nombre del archivo y guarda el itinerario del viaje.
     *
     * @param scanner Objeto Scanner que lee el nombre del archivo.
     */
    private void guardarItinerario(Scanner scanner) {
        String nombreArchivo = Utilidades.leerCadena(scanner, "Archivo donde guardar el itinerario: ");
        try {
            viaje.guardarItinerario(nombreArchivo);
            System.out.println("Itinerario guardado en " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo.");
        }
    }
}
