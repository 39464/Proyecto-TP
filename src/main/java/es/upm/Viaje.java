package es.upm;
import java.io.*;

public class Viaje {

    // ---------------------------
    // Constantes de códigos de error
    // ---------------------------
    public static final int EXITO = 0;
    public static final int ERROR_DIA_INVALIDO = 1;
    public static final int ERROR_DIA_COMPLETO = 2;
    public static final int ERROR_SOLAPAMIENTO = 3;

    public Viaje(int numDias, int maxActividades) {
        // Crea un viaje con número de días y máximo de actividades por día
    }

    public int getNumDias() {
        // Devuelve el número total de días del viaje
        return 0; // @todo MODIFICAR PARA DEVOLVER EL NÚMERO DE DÍAS
    }

    public int agregarActividad(int dia, Actividad actividad, String horaInicio) {
        // Agrega una actividad a un día dado si es válido, no está completo y no hay solapamientos
        return 0; // @todo MODIFICAR PARA DEVOLVER CÓDIGOS EXITO/ERROR
    }


    private void ordenarActividadesDia(int dia) {
        // Ordena las actividades de un día por hora de inicio (método de burbuja)
    }

    public boolean eliminarActividad(int dia, String horaInicio) {
        // Elimina la actividad programada en el día y hora indicados
        return true; // @todo MODIFICAR PARA DEVOLVER SI SE HA ELIMINADO
    }

    public Actividad[] obtenerActividadesDia(int dia) {
        // Devuelve las actividades de un día ordenadas por hora
        return null; // @todo MODIFICAR PARA DEVOLVER EL ARRAY DE ACTIVIDADES
    }

    public int getNumActividadesDia(int dia) {
        // Devuelve el número de actividades planificadas para un día
        return 0; // @todo MODIFICAR PARA DEVOLVER EL NÚMERO REAL
    }

    @Override
    public String toString() {
        // Devuelve la representación textual del itinerario
        return null; // @todo MODIFICAR PARA DEVOLVER LA REPRESENTACIÓN TEXTUAL
    }

    public void guardarItinerario(String nombreArchivo) throws IOException {
        // Guarda el itinerario en un archivo de texto (formato compacto)
    }
}
