package es.upm;

import java.io.BufferedReader;
import java.io.IOException;

public class Actividad {

    // ---------------------------
    // Constantes de códigos de error
    // ---------------------------
    public static final int EXITO = 0;
    public static final int ERROR_VALOR_INVALIDO = 1;
    public static final int ERROR_RECURSOS_COMPLETOS = 2;
    public static final int ERROR_COMENTARIOS_COMPLETOS = 3;

    public Actividad(String nombre,
                     int maxRecursos,
                     int maxComentarios) {
        // Crea una actividad con límites máximos para recursos y comentarios
    }
    public String getNombre() {
        // Devuelve el nombre de la actividad
        return null; // @todo MODIFICAR PARA DEVOLVER EL NOMBRE
    }

    public String getDescripcion() {
        // Devuelve la descripción de la actividad
        return null; // @todo MODIFICAR PARA DEVOLVER LA DESCRIPCIÓN
    }
    public void setDescripcion(String descripcion) {
        // Setea la descripción de la actividad
    }

    public double getPrecio() {
        // Devuelve el precio de la actividad
        return 0; // @todo MODIFICAR PARA DEVOLVER EL PRECIO
    }
    public void setPrecio(double precio) {
        // Setea el precio de la actividad
    }

    public int getDuracionMinutos() {
        // Devuelve la duración de la actividad
        return 0; // @todo MODIFICAR PARA DEVOLVER LA DURACIÓN
    }
    public void setDuracionMinutos(int duracionMinutos) {
        // Setea la duración de la actividad
    }

    public int getMaxRecursos() {
        // Devuelve el máximo de recursos que puede usar la actividad
        return 0; // @todo MODIFICAR PARA DEVOLVER EL MÁXIMO DE RECURSOS
    }

    public int getMaxComentarios() {
        // Devuelve el máximo de comentarios que puede haber en la actividad
        return 0; // @todo MODIFICAR PARA DEVOLVER EL MÁXIMO DE COMENTARIOS
    }

    public static int agregarRecurso(String recurso) {
        // Agrega un recurso a la actividad si no se ha alcanzado el máximo.
        return 0; // @todo MODIFICAR PARA DEVOLVER CÓDIGO DE EXITO/ERROR
    }

    public int agregarComentario(String comentario) {
        // Agrega un comentario a la actividad si no se ha alcanzado el máximo.
        return 0; // @todo MODIFICAR PARA DEVOLVER CÓDIGO DE EXITO/ERROR
    }

    public String[] getRecursos() {
        // Devuelve el array interno de recursos (puede estar parcialmente lleno)
        return null; // @todo MODIFICAR PARA DEVOLVER EL ARRAY DE RECURSOS
    }

    public String[] getComentarios() {
        // Devuelve el array interno de comentarios (puede estar parcialmente lleno).
        return null; // @todo MODIFICAR PARA DEVOLVER EL ARRAY DE COMENTARIOS
    }

    public boolean recursosCompletos() {
        // Devuelve si se alcanzó el máximo de recursos
        return true; // @todo MODIFICAR PARA DEVOLVER SI EL ARRAY DE RECURSOS ESTÁ LLENO
    }

    public boolean comentariosCompletos() {
        // Devuelve si se alcanzó el máximo de comentarios
        return true; // @todo MODIFICAR PARA DEVOLVER SI EL ARRAY DE COMENTARIOS ESTÁ LLENO
    }

    public int getNumRecursos() {
        // Devuelve el número actual de recursos almacenados
        return 0; // @todo MODIFICAR PARA DEVOLVER EL NÚMERO DE RECURSOS
    }

    public int getNumComentarios() {
        // Devuelve el número actual de comentarios almacenados
        return 0; // @todo MODIFICAR PARA DEVOLVER EL NÚMERO DE COMENTARIOS
    }

    @Override
    public String toString() {
        // Devuelve la representación textual completa de la actividad
        return null; // @todo MODIFICAR PARA DEVOLVER LA REPRESENTACIÓN TEXTUAL
    }

    public static String toRawString() {
        // Devuelve la representación textual compacta para guardado/carga
        return null; // @todo MODIFICAR PARA DEVOLVER LA REPRESENTACIÓN TEXTUAL COMPACTA
    }

    public static Actividad fromBufferedReader(
            BufferedReader reader,
            int maxRecursos,
            int maxComentarios) throws IOException {
        // Devuelve la actividad leída de un BufferedReader
        return null; // @todo MODIFICAR PARA DEVOLVER LA ACTIVIDAD LEÍDA
    }
}
