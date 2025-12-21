package es.upm;
import java.io.*;

/**
 * @author Irene Lombardo Cabrera
 * @author Almudena Moyano Londoño
 *
 * @version 1.0
 *
 * Gestiona la planificación de un viaje organizando actividades por días
 * y controlando horarios y solapamientos
 */

public class Viaje {
    // ---------------------------
    // Constantes de códigos de error
    // ---------------------------
    public static final int EXITO = 0;
    public static final int ERROR_DIA_INVALIDO = 1;
    public static final int ERROR_DIA_COMPLETO = 2;
    public static final int ERROR_SOLAPAMIENTO = 3;

    // Atributos
    private int numDias;
    private int maxActividadesPorDia;

    // Arrays bidimensionales: [dia][indice_actividad]
    private Actividad[][] actividades;
    private int[][] horasInicio; // Almacena la hora de inicio en minutos para cada actividad
    private int[] numActividadesPorDia; // Contador de actividades para cada día

    public Viaje(int numDias, int maxActividades) {
        this.numDias = numDias;
        this.maxActividadesPorDia = maxActividades;

        // Inicialización de estructuras
        this.actividades = new Actividad[numDias][maxActividades];
        this.horasInicio = new int[numDias][maxActividades];
        this.numActividadesPorDia = new int[numDias];
    }

    public int getNumDias() {
        return numDias;
    }

    public int agregarActividad(int dia, Actividad actividad, String horaInicioStr) {
        // 1. Validar día
        if (dia < 0 || dia >= numDias) {
            return ERROR_DIA_INVALIDO;
        }

        // 2. Validar si el día está lleno
        if (numActividadesPorDia[dia] >= maxActividadesPorDia) {
            return ERROR_DIA_COMPLETO;
        }

        // 3. Calcular inicio y fin de la nueva actividad en minutos
        int inicioNueva = Utilidades.horaAMinutos(horaInicioStr);
        int finNueva = inicioNueva + actividad.getDuracionMinutos();

        // 4. Comprobar solapamientos con actividades existentes en ese día
        for (int i = 0; i < numActividadesPorDia[dia]; i++) {
            int inicioExistente = horasInicio[dia][i];
            int finExistente = inicioExistente + actividades[dia][i].getDuracionMinutos();

            // Condición de solapamiento: (StartA < EndB) y (StartB < EndA)
            if (inicioNueva < finExistente && inicioExistente < finNueva) {
                return ERROR_SOLAPAMIENTO;
            }
        }

        // 5. Si todo es correcto, añadir la actividad
        int index = numActividadesPorDia[dia];
        actividades[dia][index] = actividad;
        horasInicio[dia][index] = inicioNueva;
        numActividadesPorDia[dia]++;

        // 6. Ordenar las actividades del día por hora de inicio
        ordenarActividadesDia(dia);

        return EXITO;
    }

    private void ordenarActividadesDia(int dia) {
        // Método de burbuja para ordenar por hora de inicio
        int n = numActividadesPorDia[dia];
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (horasInicio[dia][j] > horasInicio[dia][j + 1]) {
                    // Intercambiar horas
                    int tempHora = horasInicio[dia][j];
                    horasInicio[dia][j] = horasInicio[dia][j + 1];
                    horasInicio[dia][j + 1] = tempHora;

                    // Intercambiar actividades
                    Actividad tempAct = actividades[dia][j];
                    actividades[dia][j] = actividades[dia][j + 1];
                    actividades[dia][j + 1] = tempAct;
                }
            }
        }
    }

    public boolean eliminarActividad(int dia, String horaInicioStr) {
        if (dia < 0 || dia >= numDias) {
            return false;
        }

        int horaBuscada = Utilidades.horaAMinutos(horaInicioStr);
        int indiceEliminar = -1;

        // Buscar la actividad por su hora de inicio
        for (int i = 0; i < numActividadesPorDia[dia]; i++) {
            if (horasInicio[dia][i] == horaBuscada) {
                indiceEliminar = i;
                break;
            }
        }

        if (indiceEliminar == -1) {
            return false; // No se encontró
        }

        // Desplazar elementos para llenar el hueco
        for (int i = indiceEliminar; i < numActividadesPorDia[dia] - 1; i++) {
            actividades[dia][i] = actividades[dia][i + 1];
            horasInicio[dia][i] = horasInicio[dia][i + 1];
        }

        // Limpiar la última posición
        actividades[dia][numActividadesPorDia[dia] - 1] = null;
        horasInicio[dia][numActividadesPorDia[dia] - 1] = 0;
        numActividadesPorDia[dia]--;

        return true;
    }

    public Actividad[] obtenerActividadesDia(int dia) {
        if (dia < 0 || dia >= numDias) {
            return new Actividad[0];
        }

        // Crear un array del tamaño exacto de las actividades actuales
        Actividad[] resultado = new Actividad[numActividadesPorDia[dia]];
        for (int i = 0; i < numActividadesPorDia[dia]; i++) {
            resultado[i] = actividades[dia][i];
        }
        return resultado;
    }

    public int getNumActividadesDia(int dia) {
        if (dia < 0 || dia >= numDias) {
            return 0;
        }
        return numActividadesPorDia[dia];
    }

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
            sb.append("\n"); // Línea en blanco entre días
        }

        sb.append(separador).append("\n");
        sb.append("Resumen:\n");
        sb.append("- Días: ").append(numDias).append("\n");
        sb.append("- Actividades: ").append(totalActividades).append("\n");
        sb.append("- Precio: ").append(Utilidades.formatearPrecio(precioTotal)).append("\n");

        return sb.toString();
    }

    public void guardarItinerario(String nombreArchivo) throws IOException {
        try (PrintWriter out = new PrintWriter(nombreArchivo)) {
            int totalActividades = 0;
            double precioTotal = 0.0;

            for (int i = 0; i < numDias; i++) {
                out.print("Día " + (i + 1) + ": ");

                if (numActividadesPorDia[i] == 0) {
                    out.println("---");
                } else {
                    for (int j = 0; j < numActividadesPorDia[i]; j++) {
                        Actividad act = actividades[i][j];
                        String horaStr = Utilidades.minutosAHora(horasInicio[i][j]);
                        String durStr = Utilidades.formatearDuracion(act.getDuracionMinutos());
                        String precioStr = Utilidades.formatearPrecio(act.getPrecio());

                        // Formato: 09:00 Museo (dur 1h 30min, 15.00 €)
                        out.print(horaStr + " " + act.getNombre() + " (dur " + durStr + ", " + precioStr + ")");

                        // Añadir punto y coma si no es la última actividad del día
                        if (j < numActividadesPorDia[i] - 1) {
                            out.print("; ");
                        }

                        totalActividades++;
                        precioTotal += act.getPrecio();
                    }
                    out.println(); // Salto de línea al terminar las actividades del día
                }
            }
            // Línea de resumen final
            out.print("Resumen: Días: " + numDias + "; Actividades: " + totalActividades + "; Precio total: " + Utilidades.formatearPrecio(precioTotal) + "\n");
        }
    }
}
