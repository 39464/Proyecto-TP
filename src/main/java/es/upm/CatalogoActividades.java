package es.upm;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;

public class CatalogoActividades {

    public static final int EXITO = 0;
    public static final int ERROR_ACTIVIDAD_NULL = 1;
    public static final int ERROR_DEMASIADOS = 2;

    public CatalogoActividades(int maxActividades) {
        // Constructor del catálogo
    }

    public boolean actividadesCompletas() {
        // Indica si el catálogo está lleno.
        return true; // @todo MODIFICAR PARA DEVOLVER SI LAS ACTIVIDADES ESTÁN COMPLETAS
    }

    public int getNumActividades() {
        // Devuelve el número actual de actividades en el catálogo
        return 0; // @todo MODIFICAR PARA DEVOLVER EL NÚMERO DE ACTIVIDADES
    }

    public int agregarActividad(Actividad actividad) {
        // Agrega una actividad al catálogo si hay espacio disponible
        return 0; // @todo MODIFICAR PARA DEVOLVER EL CÓDIGO DE EXITO/ERROR
    }

    public boolean eliminarActividad(Actividad seleccionada) {
        return true; // @todo MODIFICAR PARA DEVOLVER SI SE HA PODIDO ELIMINAR
    }

    public Actividad[] buscarActividadPorNombre(String texto) {
        // Devuelve actividades cuyo nombre contenga el texto indicado
        return null; // @todo MODIFICAR PARA DEVOLVER LAS ACTIVIDADES QUE COINCIDEN
    }

    public void guardarActividades(String nombreArchivo) throws IOException {
        // Guarda todas las actividades en un archivo de texto usando su representación compacta
    }

    public void cargarActividades(String nombreArchivo, int maxRecursos, int maxComentarios) throws IOException {
        // Carga actividades desde un archivo de texto previamente guardado
    }
}
