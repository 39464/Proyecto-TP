package es.upm;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author Irene Lombardo Cabrera, bw0038
 * @author Almudena Moyano Londoño, bw0115
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

    /**
     * Constructor de la clase Actividad.
     *
     * @param nombre Nombre de la actividad.
     * @param maxRecursos Número máximo de recursos permitidos.
     * @param maxComentarios Número máximo de comentarios permitidos.
     */
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

    /**
     * Devuelve el nombre de la actividad
     * @return Nombre de la actividad.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la descripción de la actividad.
     * @return Descripción de la actividad.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la actividad.
     * @param descripcion Nueva descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve el precio de la actividad.
     * @return Precio de la actividad.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio de la actividad.
     * @param precio Nuevo precio.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve la duración de la actividad en minutos.
     * @return Duración de la actividad en minutos.
     */
    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    /**
     * Establece la duración de la actividad en minutos.
     * @param duracionMinutos Nueva duración en minutos.
     */
    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    /**
     * Devuelve el máximo de recursos que puede usar la actividad.
     * @return Capacidad máxima de recursos.
     */
    public int getMaxRecursos() {
        return recursos.length;
    }

    /**
     * Devuelve el máximo de comentarios que puede haber en la actividad.
     * @return Capacidad máxima de comentarios.
     */
    public int getMaxComentarios() {
        return comentarios.length;
    }

    /**
     * Agrega un recurso a la actividad si no se ha alcanzado el máximo.
     *
     * @param recurso Texto del recurso a agregar.
     * @return Entero que indica si la operación ha tenido éxito, mediante los valores de éxito
     * definidos en la clase CatalogoActividades.
     */
    public int agregarRecurso(String recurso) {
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

    /**
     * Agrega un comentario a la actividad si no se ha alcanzado el máximo.
     *
     * @param comentario Texto del comentario a agregar.
     * @return Entero que indica si la operación ha tenido éxito, mediante los valores de éxito
     * definidos en la clase CatalogoActividades.
     */
    public int agregarComentario(String comentario) {
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

    /**
     * Devuelve el array interno de recursos (puede estar parcialmente lleno).
     * @return Array interno de recursos.
     */
    public String[] getRecursos() {
        return recursos;
    }

    /**
     * Devuelve el array interno de comentarios (puede estar parcialmente lleno).
     * @return Array interno de comentarios.
     */
    public String[] getComentarios() {
        return comentarios;
    }

    /**
     * Devuelve si se alcanzó el máximo de recursos.
     * @return true si está lleno, false si no.
     */
    public boolean recursosCompletos() {
        return numRecursos >= recursos.length;
    }

    /**
     * Devuelve si se alcanzó el máximo de comentarios.
     * @return true si está lleno, false si no.
     */
    public boolean comentariosCompletos() {
        return numComentarios >= comentarios.length;
    }

    /**
     * Devuelve el número actual de recursos almacenados.
     * @return Cantidad de recursos almacenados.
     */
    public int getNumRecursos() {
        return numRecursos;
    }

    /**
     * Devuelve el número actual de comentarios almacenados.
     * @return Cantidad de comentarios almacenados.
     */
    public int getNumComentarios() {
        return numComentarios;
    }

    /**
     * Devuelve la representación textual completa de la actividad.
     * Incluye nombre, descripción, precio, duración, lista de recursos
     * y lista de comentarios.
     *
     * @return String con la información completa de la actividad.
     */
    @Override
    public String toString() {
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

    /**
     * Devuelve la representación textual compacta para guardado/carga.
     *
     * @return String con el formato raw de la actividad.
     */
    public String toRawString() {
        //
        StringBuilder sb = new StringBuilder();
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

    /**
     * Devuelve la actividad leída de un BufferedReader.
     * Lee secuencialmente según el formato generado por {@link #toRawString()}.
     *
     * @param reader Flujo de entrada desde el que se lee.
     * @param maxRecursos Máximo de recursos permitidos para la nueva actividad.
     * @param maxComentarios Máximo de comentarios permitidos para la nueva actividad.
     * @return La actividad creada o null si no se pudo leer el nombre (fin de fichero).
     * @throws IOException Si ocurre un error de lectura.
     */
    public static Actividad fromBufferedReader(
            BufferedReader reader,
            int maxRecursos,
            int maxComentarios) throws IOException {
        String nombre = reader.readLine();
        if (nombre == null) return null;

        Actividad actividad = new Actividad(nombre, maxRecursos, maxComentarios);

        String descripcion = reader.readLine();
        if (descripcion != null) actividad.setDescripcion(descripcion);

        String precioStr = reader.readLine();
        if (precioStr != null) actividad.setPrecio(Double.parseDouble(precioStr));

        String duracionStr = reader.readLine();
        if (duracionStr != null) actividad.setDuracionMinutos(Integer.parseInt(duracionStr));

        String linea;
        boolean leyendoRecursos = true;
        while (leyendoRecursos && (linea = reader.readLine()) != null) {
            if (linea.equals("COMENTARIOS")) {
                leyendoRecursos = false;
            } else {
                if (!actividad.recursosCompletos()) {
                    actividad.agregarRecurso(linea);
                }
            }
        }

        boolean leyendoComentarios = true;
        while (leyendoComentarios && (linea = reader.readLine()) != null) {
            if (linea.equals("-----")) {
                leyendoComentarios = false;
            } else {
                if (!actividad.comentariosCompletos()) {
                    actividad.agregarComentario(linea);
                }
            }
        }

        return actividad;
    }
}