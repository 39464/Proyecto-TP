package es.upm;
import java.io.IOException;

public class CatalogoActividades {

    public static final int EXITO = 0;
    public static final int ERROR_ACTIVIDAD_NULL = 1;
    public static final int ERROR_DEMASIADOS = 2;

    private Actividad[] actividades;
    private int maxActividades;

    public CatalogoActividades(int maxActividades) {
        this.maxActividades = maxActividades;
        actividades = new Actividad[maxActividades]; }

    public boolean actividadesCompletas() { return (actividades.length == maxActividades); }// Devuelve si las actividades están completas

    public int getNumActividades() { return actividades.length; } // Devuelve el número actual de actividades en el catálogo

    public int agregarActividad(Actividad actividad) {
        int resultado;
        if (!actividadesCompletas()) {
            actividades[actividades.length-1] = actividad;
            resultado = EXITO;
        }else{
            resultado = ERROR_DEMASIADOS;
        }
        return resultado; // Agrega una actividad al catálogo si hay espacio disponible
    }

    public boolean eliminarActividad(Actividad seleccionada) {
        boolean resultado = false;
        int i = 0;
        while (!resultado && i < actividades.length) {
            if (actividades[i].getNombre().equals(seleccionada)) {
                actividades[i] = null;
                for (int j = i+1; j < actividades.length; j++) {
                    actividades[j-1]= actividades[j];
                }
                resultado = true;
            }
            i++;
        }
        return resultado; // Elimina la actividad cuyo nombre sea el seleccionado
    }

    public Actividad[] buscarActividadPorNombre(String texto) {
        Actividad[] actividadesCoinciden = new Actividad[maxActividades];
        for(int i = 0; i < actividades.length; i++){
            if(actividades[i].getNombre().contains(texto)){
                actividadesCoinciden[actividadesCoinciden.length-1]= actividades[i];
            }
        }
        return actividadesCoinciden; // Devuelve actividades cuyo nombre contenga el texto indicado
    }

    public void guardarActividades(String nombreArchivo) throws IOException {
        // Guarda todas las actividades en un archivo de texto usando su representación compacta
    }

    public void cargarActividades(String nombreArchivo, int maxRecursos, int maxComentarios) throws IOException {
        // Carga actividades desde un archivo de texto previamente guardado
    }

    /*se deben lanzar las excepciones para que sean
    manejadas en el metodo que llama a guardarActividades y
    cargarActividades.*/
}
