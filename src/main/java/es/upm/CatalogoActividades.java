package es.upm;
import java.io.*;

public class CatalogoActividades {

    public static final int EXITO = 0;
    public static final int ERROR_ACTIVIDAD_NULL = 1;
    public static final int ERROR_DEMASIADOS = 2;

    private int numActividades;
    private int maxActividades;
    private Actividad[] catalogo;

    public CatalogoActividades(int maxActividades) {
        this.numActividades = 0;
        this.maxActividades = maxActividades;
        this.catalogo = new Actividad[maxActividades]; }

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
    }// Devuelve si las actividades están completas

    public int getNumActividades() { return numActividades; } // Devuelve el número actual de actividades en el catálogo

    public int agregarActividad(Actividad actividad) {
        int resultado = EXITO;
        if (actividad == null) resultado = ERROR_ACTIVIDAD_NULL;
        else if (actividadesCompletas()) resultado = ERROR_DEMASIADOS;
        else {
            catalogo[numActividades] = actividad;
            numActividades++;
        }
        return resultado; // Agrega una actividad al catálogo si hay espacio disponible
    }

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
        return resultado; // Elimina la actividad cuyo nombre sea el seleccionado
    }

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
        
        return resultado; // Devuelve actividades cuyo nombre contenga el texto indicado
    }

    public void guardarActividades(String nombreArchivo) throws IOException {
        PrintWriter out = null;
        try{
            out = new PrintWriter(new FileWriter(nombreArchivo));
            for (int i = 0; i < this.numActividades; i++) {
                out.print(this.catalogo[i].toRawString());
            }
            System.out.println("Actividad guardada correctamente.");
        } finally{
            try {
                if (out != null) out.close();
            }catch(Exception ex){
                System.out.println("Error al cerrar el archivo.");
            }
        }
        // Guarda todas las actividades en un archivo de texto usando su representación compacta
    }

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
        // Carga actividades desde un archivo de texto previamente guardado
    }

    /*se deben lanzar las excepciones para que sean
    manejadas en el metodo que llama a guardarActividades y
    cargarActividades.*/
}
