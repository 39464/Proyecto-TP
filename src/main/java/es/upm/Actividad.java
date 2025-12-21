package es.upm;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Irene Lombardo Cabrera
 * @author Almudena Moyano Londoño
 *
 * @version 1.0
 *
 * Representa una actividad turística con nombre, descripción, precio, duración,
 * recursos y comentarios.
 */

public class Actividad {

    // ---------------------------
    // Constantes de códigos de error
    // ---------------------------
    public static final int EXITO = 0;
    public static final int ERROR_VALOR_INVALIDO = 1;
    public static final int ERROR_RECURSOS_COMPLETOS = 2;
    public static final int ERROR_COMENTARIOS_COMPLETOS = 3;

    private String nombre;
    private String descripcion;
    private double precio;
    private int duracionMinutos;

    private String[] recursos;
    private String[] comentarios;

    private int numRecursos;
    private int numComentarios;

    public Actividad(String nombre,
                     int maxRecursos,
                     int maxComentarios) {
        // Crea una actividad con límites máximos para recursos y comentarios
        this.nombre = nombre;
        this.recursos = new String[maxRecursos];
        this.comentarios = new String[maxComentarios];
        this.numRecursos = 0;
        this.numComentarios = 0;
        this.descripcion = "";
        this.precio = 0.0;
        this.duracionMinutos = 0;
    }

    public String getNombre() {
        // Devuelve el nombre de la actividad
        return nombre;
    }

    public String getDescripcion() {
        // Devuelve la descripción de la actividad
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        // Setea la descripción de la actividad
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        // Devuelve el precio de la actividad
        return precio;
    }

    public void setPrecio(double precio) {
        // Setea el precio de la actividad
        this.precio = precio;
    }

    public int getDuracionMinutos() {
        // Devuelve la duración de la actividad
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        // Setea la duración de la actividad
        this.duracionMinutos = duracionMinutos;
    }

    public int getMaxRecursos() {
        // Devuelve el máximo de recursos que puede usar la actividad
        return recursos.length;
    }

    public int getMaxComentarios() {
        // Devuelve el máximo de comentarios que puede haber en la actividad
        return comentarios.length;
    }

    public int agregarRecurso(String recurso) {  // SE HA ELIMINADO STATIC DEL ESQUELETO ORIGINAL PARA QUE FUNCIONE CON LOS OBJETOS
        // Agrega un recurso a la actividad si no se ha alcanzado el máximo.
        if (recurso == null || recurso.trim().isEmpty()) {
            return ERROR_VALOR_INVALIDO;
        }
        if (numRecursos >= recursos.length) {
            return ERROR_RECURSOS_COMPLETOS;
        }
        recursos[numRecursos] = recurso;
        numRecursos++;
        return EXITO;
    }

    public int agregarComentario(String comentario) {
        // Agrega un comentario a la actividad si no se ha alcanzado el máximo.
        if (comentario == null || comentario.trim().isEmpty()) {
            return ERROR_VALOR_INVALIDO;
        }
        if (numComentarios >= comentarios.length) {
            return ERROR_COMENTARIOS_COMPLETOS;
        }
        comentarios[numComentarios] = comentario;
        numComentarios++;
        return EXITO;
    }

    public String[] getRecursos() {
        // Devuelve el array interno de recursos (puede estar parcialmente lleno)
        return recursos;
    }

    public String[] getComentarios() {
        // Devuelve el array interno de comentarios (puede estar parcialmente lleno).
        return comentarios;
    }

    public boolean recursosCompletos() {
        // Devuelve si se alcanzó el máximo de recursos
        return numRecursos >= recursos.length;
    }

    public boolean comentariosCompletos() {
        // Devuelve si se alcanzó el máximo de comentarios
        return numComentarios >= comentarios.length;
    }

    public int getNumRecursos() {
        // Devuelve el número actual de recursos almacenados
        return numRecursos;
    }

    public int getNumComentarios() {
        // Devuelve el número actual de comentarios almacenados
        return numComentarios;
    }

    @Override
    public String toString() {
        // Devuelve la representación textual completa de la actividad
        StringBuilder sb = new StringBuilder();
        sb.append("Actividad: ").append(nombre).append("\n");
        sb.append("Descripción: ").append(descripcion).append("\n");
        sb.append("Precio: ").append(Utilidades.formatearPrecio(precio)).append("\n");
        sb.append("Duración: ").append(Utilidades.formatearDuracion(duracionMinutos)).append("\n");

        sb.append("Recursos:\n");
        for (int i = 0; i < numRecursos; i++) {
            sb.append("- ").append(recursos[i]).append("\n");
        }

        sb.append("Comentarios:\n");
        for (int i = 0; i < numComentarios; i++) {
            sb.append((i + 1)).append(". ").append(comentarios[i]).append("\n");
        }
        return sb.toString();
    }

    public String toRawString() {  // SE HA ELIMINADO STATIC DEL ESQUELETO ORIGINAL
        // Devuelve la representación textual compacta para guardado/carga
        StringBuilder sb = new StringBuilder();   // CAMBIAR STRINGBUILDER
        sb.append(nombre).append("\n");
        sb.append(descripcion).append("\n");
        sb.append(precio).append("\n");
        sb.append(duracionMinutos).append("\n");

        for (int i = 0; i < numRecursos; i++) {
            sb.append(recursos[i]).append("\n");
        }

        sb.append("COMENTARIOS").append("\n");

        for (int i = 0; i < numComentarios; i++) {
            sb.append(comentarios[i]).append("\n");
        }

        sb.append("-----").append("\n");
        return sb.toString();
    }

    public static Actividad fromBufferedReader(
            BufferedReader reader,
            int maxRecursos,
            int maxComentarios) throws IOException {
        // Devuelve la actividad leída de un BufferedReader
        String nombre = reader.readLine();
        if (nombre == null) return null; // Fin de archivo o error

        Actividad actividad = new Actividad(nombre, maxRecursos, maxComentarios);

        String descripcion = reader.readLine();
        if (descripcion != null) actividad.setDescripcion(descripcion);

        String precioStr = reader.readLine();
        if (precioStr != null) actividad.setPrecio(Double.parseDouble(precioStr));

        String duracionStr = reader.readLine();
        if (duracionStr != null) actividad.setDuracionMinutos(Integer.parseInt(duracionStr));

        // Leer recursos hasta encontrar "COMENTARIOS"
        String linea;
        boolean leyendoRecursos = true;
        while (leyendoRecursos && (linea = reader.readLine()) != null) {
            if (linea.equals("COMENTARIOS")) {
                leyendoRecursos = false;
            } else {
                // Solo agregamos si hay espacio
                // Si está lleno, leemos la línea pero no la guardamos
                if (!actividad.recursosCompletos()) {
                    actividad.agregarRecurso(linea);
                }
            }
        }

        // Leer comentarios hasta encontrar "-----"
        boolean leyendoComentarios = true;
        while (leyendoComentarios && (linea = reader.readLine()) != null) {
            if (linea.equals("-----")) {
                leyendoComentarios = false;
            } else {
                // Solo agregamos si hay espacio
                if (!actividad.comentariosCompletos()) {
                    actividad.agregarComentario(linea);
                }
            }
        }

        return actividad;
    }
}
