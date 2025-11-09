package es.upm;

import java.util.Scanner;

public class InterfazUsuario {

    public InterfazUsuario(CatalogoActividades catalogo, Viaje viaje, int maxRecursos, int maxComentarios) {
        // Crea la interfaz de usuario con el catálogo y viaje proporcionados
    }

    public void iniciar(Scanner scanner) {
        // Inicia el bucle del menú principal
    }

    private void menuPrincipal(Scanner scanner) {
        // Ejecuta el bucle del menú principal hasta que el usuario decida salir
    }

    private void mostrarMenu() {
        // Muestra las opciones del menú principal
    }

    private void agregarActividad(Scanner scanner) {
        // Lee los datos de una nueva actividad y la agrega al catálogo
    }

    private void consultarActividad(Scanner scanner) {
        // Busca una actividad y permite editarla
    }

    private Actividad buscarActividadPorNombre(Scanner scanner) {
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
        // Lee el nombre del archivo y guarda las actividades del catálogo
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
