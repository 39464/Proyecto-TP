package es.upm;
import java.io.*;

/**
 * @author Irene Lombardo Cabrera, bw0038
 * @author Almudena Moyano Londoño, bw0115
 *
 * @version 1.0
 *
 * Gestiona la planificación de un viaje organizando actividades por días
 * y controlando horarios y solapamientos.
 */

public class Viaje {
    // ---------------------------
    // Constantes de códigos de error
    // ---------------------------
    /** Código de éxito en la planificación. */
    public static final int EXITO = 0;
    /** Código de error si el día indicado no existe en el viaje. */
    public static final int ERROR_DIA_INVALIDO = 1;
    /** Código de error si el día ha alcanzado el máximo de actividades. */
    public static final int ERROR_DIA_COMPLETO = 2;
    /** Código de error si la actividad se solapa con otra ya existente. */
    public static final int ERROR_SOLAPAMIENTO = 3;

    private int numDias;
    private int maxActividadesPorDia;

    // Arrays bidimensionales: [dia][indice_actividad]
    private Actividad[][] actividades;
    private int[][] horasInicio; // Almacena la hora de inicio en minutos para cada actividad
    private int[] numActividadesPorDia; // Contador de actividades para cada día

    /**
     * Constructor del viaje.
     *
     * @param numDias Duración del viaje en días.
     * @param maxActividades Máximo de actividades permitidas por día.
     */
    public Viaje(int numDias, int maxActividades) {
        this.numDias = numDias;
        this.maxActividadesPorDia = maxActividades;

        this.actividades = new Actividad[numDias][maxActividades];
        this.horasInicio = new int[numDias][maxActividades];
        this.numActividadesPorDia = new int[numDias];
    }

    /**
     * Devuelve el número de días del viaje.
     * @return Número de días.
     */
    public int getNumDias() {
        return numDias;
    }

    /**
     * Agrega una actividad a un día y hora específicos.
     * Comprueba validez del día, espacio y solapamientos.
     *
     * @param dia Índice del día.
     * @param actividad Actividad a planificar.
     * @param horaInicioStr Hora de inicio en formato hh:mm.
     * @return Entero que indica si la operación ha tenido éxito, mediante los valores de éxito
     * definidos en la clase CatalogoActividades.
     */
    public int agregarActividad(int dia, Actividad actividad, String horaInicioStr) {
        if (dia < 0 || dia >= numDias) {
            return ERROR_DIA_INVALIDO;
        }

        if (numActividadesPorDia[dia] >= maxActividadesPorDia) {
            return ERROR_DIA_COMPLETO;
        }

        int inicioNueva = Utilidades.horaAMinutos(horaInicioStr);
        int finNueva = inicioNueva + actividad.getDuracionMinutos();

        for (int i = 0; i < numActividadesPorDia[dia]; i++) {
            int inicioExistente = horasInicio[dia][i];
            int finExistente = inicioExistente + actividades[dia][i].getDuracionMinutos();

            if (inicioNueva < finExistente && inicioExistente < finNueva) {
                return ERROR_SOLAPAMIENTO;
            }
        }

        int index = numActividadesPorDia[dia];
        actividades[dia][index] = actividad;
        horasInicio[dia][index] = inicioNueva;
        numActividadesPorDia[dia]++;

        ordenarActividadesDia(dia);

        return EXITO;
    }

    /**
     * Metodo de burbuja para ordenar las actividades de un día por hora de inicio.
     *
     * @param dia Día a ordenar.
     */
    private void ordenarActividadesDia(int dia) {
        int n = numActividadesPorDia[dia];
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (horasInicio[dia][j] > horasInicio[dia][j + 1]) {
                    int tempHora = horasInicio[dia][j];
                    horasInicio[dia][j] = horasInicio[dia][j + 1];
                    horasInicio[dia][j + 1] = tempHora;

                    Actividad tempAct = actividades[dia][j];
                    actividades[dia][j] = actividades[dia][j + 1];
                    actividades[dia][j + 1] = tempAct;
                }
            }
        }
    }

    /**
     * Elimina una actividad planificada en un día y hora específicos.
     *
     * @param dia Día del cual se quiere eliminar una actividad.
     * @param horaInicioStr Hora de inicio de la actividad a eliminar.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean eliminarActividad(int dia, String horaInicioStr) {
        if (dia < 0 || dia >= numDias) {
            return false;
        }

        int horaBuscada = Utilidades.horaAMinutos(horaInicioStr);
        int indiceEliminar = -1;

        for (int i = 0; i < numActividadesPorDia[dia]; i++) {
            if (horasInicio[dia][i] == horaBuscada) {
                indiceEliminar = i;
                break;
            }
        }

        if (indiceEliminar == -1) {
            return false;
        }

        for (int i = indiceEliminar; i < numActividadesPorDia[dia] - 1; i++) {
            actividades[dia][i] = actividades[dia][i + 1];
            horasInicio[dia][i] = horasInicio[dia][i + 1];
        }

        actividades[dia][numActividadesPorDia[dia] - 1] = null;
        horasInicio[dia][numActividadesPorDia[dia] - 1] = 0;
        numActividadesPorDia[dia]--;

        return true;
    }

    /**
     * Obtiene un array con las actividades de un día específico.
     *
     * @param dia Día consultado.
     * @return Array de actividades en un día específico.
     */
    public Actividad[] obtenerActividadesDia(int dia) {
        if (dia < 0 || dia >= numDias) {
            return new Actividad[0];
        }

        Actividad[] resultado = new Actividad[numActividadesPorDia[dia]];
        for (int i = 0; i < numActividadesPorDia[dia]; i++) {
            resultado[i] = actividades[dia][i];
        }
        return resultado;
    }

    /**
     * Devuelve el número de actividades planificadas para un día específico.
     *
     * @param dia Día consultado.
     * @return Número de actividades en un día específico.
     */
    public int getNumActividadesDia(int dia) {
        if (dia < 0 || dia >= numDias) {
            return 0;
        }
        return numActividadesPorDia[dia];
    }

    /**
     * Genera una representación textual detallada del itinerario del viaje,
     * con desglose por días y resumen final de costes.
     *
     * @return String con el itinerario completo.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String separador = "-------------------------------------------------------------------";
        int totalActividades = 0;
        double precioTotal = 0.0;

        for (int i = 0; i < numDias; i++) {
            sb.append(separador).append("\n");
            sb.append("Día ").append(i + 1).append("\n");
            sb.append(separador).append("\n");

            if (numActividadesPorDia[i] == 0) {
                sb.append("(No hay actividades)\n");
            } else {
                for (int j = 0; j < numActividadesPorDia[i]; j++) {
                    String horaStr = Utilidades.minutosAHora(horasInicio[i][j]);
                    sb.append(horaStr).append(" ").append(actividades[i][j].getNombre()).append("\n");

                    totalActividades++;
                    precioTotal += actividades[i][j].getPrecio();
                }
            }
            sb.append("\n");
        }

        sb.append(separador).append("\n");
        sb.append("Resumen:\n");
        sb.append("- Días: ").append(numDias).append("\n");
        sb.append("- Actividades: ").append(totalActividades).append("\n");
        sb.append("- Precio: ").append(Utilidades.formatearPrecio(precioTotal)).append("\n");

        return sb.toString();
    }

    /**
     * Guarda el itinerario en un archivo de texto con formato compacto.
     *
     * @param nombreArchivo Ruta del archivo de salida.
     * @throws IOException Si ocurre un error de escritura.
     */
    public void guardarItinerario(String nombreArchivo) throws IOException {
        try (PrintWriter out = new PrintWriter(nombreArchivo)) {
            int totalActividades = 0;
            double precioTotal = 0.0;

            for (int i = 0; i < numDias; i++) {
                out.print("Día " + (i + 1) + ": ");

                if (numActividadesPorDia[i] == 0) {
                    out.print("---\n");
                } else {
                    for (int j = 0; j < numActividadesPorDia[i]; j++) {
                        Actividad act = actividades[i][j];
                        String horaStr = Utilidades.minutosAHora(horasInicio[i][j]);
                        String durStr = Utilidades.formatearDuracion(act.getDuracionMinutos());
                        String precioStr = Utilidades.formatearPrecio(act.getPrecio());

                        // Formato: 09:00 Museo (dur 1h 30min, 15.00 €)
                        out.print(horaStr + " " + act.getNombre() + " (dur " + durStr + ", " + precioStr + ")");

                        if (j < numActividadesPorDia[i] - 1) {
                            out.print("; ");
                        }

                        totalActividades++;
                        precioTotal += act.getPrecio();
                    }
                    out.print("\n");
                }
            }

            out.print("Resumen: Días: " + numDias + "; Actividades: " + totalActividades + "; Precio total: " + Utilidades.formatearPrecio(precioTotal) + "\n");
        }
    }
}