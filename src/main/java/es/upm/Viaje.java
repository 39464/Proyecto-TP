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

    private int numDias;
    private int maxActividadesPorDia;

    private Actividad[][] actividades;
    private int[][] horasInicio;  // Hora de inicio en mins para cada actividad
    private int[] numActividadesPorDia;

    public Viaje(int numDias, int maxActividades) {
        // Crea un viaje con número de días y máximo de actividades por día
        this.numDias = numDias;
        this.maxActividadesPorDia = maxActividades;

        this.actividades = new Actividad[numDias][maxActividades];
        this.horasInicio = new int[numDias][maxActividades];
        this.numActividadesPorDia = new int[numDias];
    }

    public int getNumDias() {
        // Devuelve el número total de días del viaje
        return numDias;
    }

    public int agregarActividad(int dia, Actividad actividad, String horaInicio) {
        // Agrega una actividad a un día dado si es válido, no está completo y no hay solapamientos
        if (dia < 0 || dia >= numDias) {
            return ERROR_DIA_INVALIDO;
        }

        if (numActividadesPorDia[dia] >= maxActividadesPorDia) {
            return ERROR_DIA_COMPLETO;
        }

        // Inicio y fin nueva actividad en mins  CAMBIAR INICIO NUEVA --> NUEVA ACT
        int inicioNueva = Utilidades.horaAMinutos(horaInicio);
        int finNueva = inicioNueva + actividad.getDuracionMinutos();

        for (int i = 0; i < numActividadesPorDia[dia]; i++) {
            int inicioExistente = horasInicio[dia][i];
            int finExistente = inicioExistente + actividades[dia][i].getDuracionMinutos();

            if (inicioNueva < finExistente && inicioExistente < finNueva) {
                return ERROR_SOLAPAMIENTO;
            }
        }

        int indice = numActividadesPorDia[dia];
        actividades[dia][indice] = actividad;
        horasInicio[dia][indice] = inicioNueva;
        numActividadesPorDia[dia]++;

        ordenarActividadesDia(dia); // Ordenar actividades por hora d inicio

        return EXITO;
    }

    // // // //
    // COMPROBAR Q FUNCIONE LO SIGUIENTE
    // // // //

    private void ordenarActividadesDia(int dia) {
        // Ordena las actividades de un día por hora de inicio (método de burbuja)
        //  int n = numActividadesPorDia[dia];

        for (int i = 0; i < numActividadesPorDia[dia] - 1; i++) {
            for (int j = 0; j < numActividadesPorDia[dia] - i - 1; j++) {
                if (horasInicio[dia][j] > horasInicio[dia][j + 1]) {
                    // Intercambiar horas
                    int tempHora = horasInicio[dia][j];
                    horasInicio[dia][j] = horasInicio[dia][j + 1];
                    horasInicio[dia][j + 1] = tempHora;

                    // Intercambiar actividades
                    Actividad tempAct = actividades[dia][j];
                    actividades[dia][j] =  actividades[dia][j + 1];
                    actividades[dia][j + 1] = tempAct;
                }
            }
        }
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
