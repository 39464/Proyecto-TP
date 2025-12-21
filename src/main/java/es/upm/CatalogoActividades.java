package es.upm;
import java.io.*;

/**
 * @author Irene Lombardo Cabrera
 * @author Almudena Moyano Londoño
 *
 * @version 1.0
 *
 * Clase con métodos de gestión de un catálogo de actividades
 */

public class CatalogoActividades {

    public static final int EXITO = 0;
    public static final int ERROR_ACTIVIDAD_NULL = 1;
    public static final int ERROR_DEMASIADOS = 2;

    private int numActividades;
    private int maxActividades;
    private Actividad[] catalogo;

    /** Constructor del catálogo
     *
     * @param maxActividades : máximo de actividades que puede albergar el array del catálogo
     */
    public CatalogoActividades(int maxActividades) {
        this.numActividades = 0;
        this.maxActividades = maxActividades;
        this.catalogo = new Actividad[maxActividades]; }

    /** Comprueba si el catálogo de actividades está lleno
     *
     * @return : true si el catálogo ha alcanzado su capacidad máxima
     */
    public boolean actividadesCompletas() {
        boolean resultado = false;
        int i = 0;
        while (i < catalogo.length && catalogo[i] != null) {
            i++;
        }
        if (i == catalogo.length) {
            resultado= true;
        }
        return resultado;
    }

    /** Getter de numActividades
     *
     * @return : número actual de actividades en el catálogo
     */
    public int getNumActividades() { return numActividades; }

    /** Agrega una actividad al catálogo si hay espacio disponible
     *
     * @param actividad : actividad a agregar en el catalogo
     * @return : entero que indica si la operación ha tenido éxito, mediante los valores de éxito definidos en la clase Catálogo
     */
    public int agregarActividad(Actividad actividad) {
        int resultado = EXITO;
        if (actividad == null) resultado = ERROR_ACTIVIDAD_NULL;
        else if (actividadesCompletas()) resultado = ERROR_DEMASIADOS;
        else {
            catalogo[numActividades] = actividad;
            numActividades++;
        }
        return resultado;
    }

    /** Elimina la actividad cuyo nombre sea el seleccionado
     *
     * @param seleccionada : actividad para buscar y eliminar
     * @return : entero que indica si la operación ha tenido éxito, mediante los valores de éxito definidos en la clase Catálogo
     */
    public boolean eliminarActividad(Actividad seleccionada) {
        boolean resultado = false;
        int i = 0;
        while (!resultado && i < numActividades) {
            if (catalogo[i] == seleccionada) {
                for (int j = i; j < numActividades-1; j++) {
                    catalogo[j]= catalogo[j+1];
                }
                catalogo[numActividades-1] = null;
                numActividades--;
                resultado = true;
            }
            i++;
        }
        if(i == 0) resultado = false;
        return resultado;
    }

    /** Busca actividades cuyo nombre contenga un texto
     *
     * @param texto : texto a buscar entre los nombres de las actividades del catálogo
     * @return : Array de actividades cuyo nombre contenga el texto recibido como parámetro
     */
    public Actividad[] buscarActividadPorNombre(String texto) {
        Actividad[] coinciden = new Actividad[maxActividades];
        Actividad[] resultado = new Actividad[0];
        String textoBuscado = texto.toLowerCase();

        for(int i = 0; i < numActividades; i++){
            if((catalogo[i].getNombre().toLowerCase()).contains(textoBuscado)){
                int j = 0;
                while(coinciden[j] != null){
                    j++;
                }
                coinciden[j]= catalogo[i];
                resultado = coinciden;
            }
        }
        
        return resultado;
    }

    /** Guarda todas las actividades en un archivo de texto, utilizando su representación compacta
     *
     * @param nombreArchivo : nombre del archivo que se desea crear para contener las actividades del catálogo
     * @throws IOException : error al escribir el archivo
     */
    public void guardarActividades(String nombreArchivo) throws IOException {
        PrintWriter out = null;
        try{
            out = new PrintWriter(new FileWriter(nombreArchivo));
            for (int i = 0; i < this.numActividades; i++) {
                out.print(this.catalogo[i].toRawString());
            }
            System.out.println("Actividades guardadas en"+ nombreArchivo);
        } finally{
            try {
                if (out != null) out.close();
            }catch(Exception ex){
                System.out.println("Error al cerrar el archivo.");
            }
        }
    }

    /** Carga actividades desde un archivo de texto previamente guardado
     *
     * @param nombreArchivo : String que indique el nombre del archivo del cual extraer las actividades
     * @param maxRecursos : número máximo de recursos que se pueden cargar
     * @param maxComentarios : número máximo de comentarios que se pueden cargar
     * @throws IOException : error al escribir el archivo
     */
    public void cargarActividades(String nombreArchivo, int maxRecursos, int maxComentarios) throws IOException {
        BufferedReader in = null;
        try{
            in = new BufferedReader(new FileReader(nombreArchivo));
            boolean fin = false;
            while (!actividadesCompletas() && !fin) {
                Actividad actividad = Actividad.fromBufferedReader(in, maxRecursos, maxComentarios);
                if (actividad != null) {
                    agregarActividad(actividad);
                } else {
                    fin = true;
                }
            }
        }finally{
            try{
                if(in != null) in.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar el archivo.");
            }
        }
    }
}
