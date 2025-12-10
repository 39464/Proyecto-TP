package es.upm;

import java.io.*;
import java.text.NumberFormat;
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
        while(seguir){
            mostrarMenu();
            String input = scanner.nextLine();
            int opcion;
            try{
                opcion = Integer.parseInt(input);
            }catch(NumberFormatException e){
                opcion = -1;
            }
            switch(opcion){
                case 1: agregarActividad(scanner); break;
                case 2: consultarActividad(scanner); break;
                case 3: guardarActividades(scanner); break;
                case 4: cargarActividades(scanner); break;
                case 5: planificarViaje(scanner); break;
                case 6: guardarItinerario(scanner); break;
                case 7: seguir = false; break;
            }
        }
    } // Ejecuta el bucle del menú principal hasta que el usuario decida salir

    private void mostrarMenu() {
        System.out.println("---Menú principal---\n");
        System.out.println("1. Agregar Actividad\n" +
                "2. Consultar/Editar Actividad\n" +
                "3. Guardar Actividades\n"+
                "4. Cargar Actividades\n" +
                "5. Planificar Viaje\n" +
                "6. Guardar Itinerario\n" +
                "7. Salir\n");
        System.out.println("Elige una opcion: ");
    } // Muestra las opciones del menú principal

    private void agregarActividad(Scanner scanner) {
        String nombre = Utilidades.leerCadena(scanner,"Nombre de la actividad: ");
        String descripcion = Utilidades.leerCadena(scanner,"Descripción: ");
        double precio = Utilidades.leerDouble(scanner, "Precio (€): ", 0.0, Double.MAX_VALUE);
        int duracion = Utilidades.leerNumero(scanner, "Duración (minutos): ", 0, Integer.MAX_VALUE);

        Actividad nueva = new Actividad(nombre, this.maxRecursos, this.maxComentarios);
        nueva.setDescripcion(descripcion);
        nueva.setPrecio(precio);
        nueva.setDuracionMinutos(duracion);

        System.out.print("Introduce los recursos (una línea por recurso, escribe 'fin' para terminar):");
        String resultadoR = "";
        while(!resultadoR.equals("fin")){
            resultadoR = scanner.nextLine();
            if(!resultadoR.equals("fin")){
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
        while(!resultadoC.equals("fin")){
            resultadoC = Utilidades.leerCadena(scanner, "");
            if(!resultadoC.equals("fin")){
                int comentarios = nueva.agregarComentario(resultadoC);
                if(comentarios == Actividad.ERROR_COMENTARIOS_COMPLETOS) System.out.println("No se pueden añadir más recursos"); resultadoR = "fin";
                if(comentarios == Actividad.ERROR_VALOR_INVALIDO) System.out.println("El recurso es invalido"); resultadoR = "fin";
            }
        }

        if(catalogo.agregarActividad(nueva)== CatalogoActividades.EXITO){
            System.out.println("¡Actividad agregada exitosamente!");
        } else{
            System.out.println("No se pueden añadir más actividades.");
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
        } else {
            editarActividad(scanner, actividadBuscada);
        }
        // Busca una actividad y permite editarla
    }

    private Actividad buscarActividadPorNombre(Scanner scanner) {
        String nombre =  Utilidades.leerCadena(scanner,"Elija el nombre de la actividad: ");
        Actividad[] buscadas = catalogo.buscarActividadPorNombre(nombre);
        return seleccionarActividad(scanner, buscadas); // Busca actividades por nombre y permite seleccionar una
    }

    private Actividad seleccionarActividad(Scanner scanner, Actividad[] actividades) {
        System.out.println("Actividades encontradas:");
        for(int i = 0; i < actividades.length; i++){
            System.out.println((i+1)+". " + actividades[i].getNombre());
        }
        int opcion = Utilidades.leerNumero(scanner, "Introduzca una opcion: ", 0, actividades.length-1);
        return actividades[opcion+1]; // Muestra un listado numerado de actividades y permite elegir una
    }

    private void editarActividad(Scanner scanner, Actividad seleccionada) {
        System.out.println(seleccionada.toString());
        System.out.println("1. Añadir recurso\n2. Añadir comentario\n3. Eliminar actividad\n4. Volver");
        switch(scanner.nextInt()){
            case 1:
                System.out.print("Introduzca el recurso que desea añadir: ");
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
                System.out.print("Introduzca el comentario que desea añadir: ");
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
                else System.out.println("Actividad eliminada exitosamente.");
                break;
            case 4:
                break;
        }
        // Muestra la actividad y permite añadir recursos, comentarios o eliminarla
    }

    private void guardarActividades(Scanner scanner) {
        try {
            String nombreArchivo = Utilidades.leerCadena(scanner, "Archivo donde guardar las actividades: ");
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
        String nombreArchivo = Utilidades.leerCadena(scanner, "Archivo de donde cargar las actividades: ");
        try{
            cargarActividadesDesdeArchivo(nombreArchivo);
        }catch(FileNotFoundException ex){
            System.out.println("No se encontro el archivo.");
        }
        // Lee el nombre del archivo y carga actividades al catálogo
    }

    private void cargarActividadesDesdeArchivo(String nombreArchivo) throws FileNotFoundException {
        BufferedReader in;
        try{
            in = new BufferedReader(new FileReader(nombreArchivo));
            int cont = 0;
            while(cont <= this.maxActividades){
                Actividad nueva = Actividad.fromBufferedReader(in, this.maxRecursos, this.maxComentarios);
                this.catalogo.agregarActividad(nueva);
                cont++;
                if(catalogo.agregarActividad(nueva) == CatalogoActividades.EXITO)
                    System.out.println("Actividades cargadas desde "+nombreArchivo);
            }
        }catch (IOException e){
            System.out.println("Error al cargar las actividades");
        }
    }

    private void planificarViaje(Scanner scanner) {
        // Muestra el itinerario actual y permite agregar actividades a días específicos
    }

    private void guardarItinerario(Scanner scanner) {
        // Lee el nombre del archivo y guarda el itinerario del viaje
    }
}
