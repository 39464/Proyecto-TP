package es.upm;

import java.util.Locale;
import java.util.Scanner;

/**
 * @author Irene Lombardo Cabrera
 * @author Almudena Moyano Londoño
 *
 * @version 1.0
 *
 * Clase con métodos de utilidad para la entrada de datos por teclado y conversión de formatos.
 */

public class Utilidades {

    /** Lee un String por entrada de teclado.
     *
     * @param teclado Scanner que lee la entrada del teclado.
     * @param s Mensaje escrito en pantalla que pregunta al usuario por un String.
     * @return String escrito por teclado.
     */
    public static String leerCadena(Scanner teclado, String s) {
        System.out.println(s);
        return teclado.nextLine();
    }

    /** Lee un número entero por entrada de teclado y comprueba que está dentro de un intervalo [minimo, maximo].
     *
     * @param teclado Scanner que lee la entrada del teclado.
     * @param mensaje Mensaje escrito en pantalla, pregunta al usuario por un entero.
     * @param minimo Valor mínimo que puede tomar la entrada.
     * @param maximo Valor máximo que puede tomar la entrada.
     * @return Número escrito por teclado, que esté dentro del intervalo.
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int resultado = 0;
        boolean esValido = false;
        do {
            System.out.println(mensaje);
            if (teclado.hasNextInt()) {
                resultado = teclado.nextInt();
                if  (resultado >= minimo && resultado <= maximo) {
                    esValido = true;
                } else {
                    System.out.println("El número debe estar entre "+minimo+" y "+maximo);
                    teclado.nextLine();
                }
            } else {
                System.out.println("Por favor, introduce un número válido");
                teclado.nextLine();
            }
        } while (!esValido);
        return resultado;
    }

    /** Lee un número decimal por entrada de teclado y comprueba que está dentro de un intervalo [minimo, maximo].
     *
     * @param teclado Scanner que lee la entrada del teclado.
     * @param mensaje Mensaje escrito en pantalla, pregunta al usuario por un double.
     * @param minimo Valor mínimo que puede tomar la entrada.
     * @param maximo Valor máximo que puede tomar la entrada.
     * @return Número escrito por teclado, que esté dentro del intervalo.
     */
    public static double leerDouble(Scanner teclado, String mensaje, double minimo, double maximo) {
        teclado = teclado.useLocale(Locale.US);
        double resultado = 0.0;
        boolean esValido = false;
        do {
            System.out.println(mensaje);
            if(teclado.hasNextDouble()) {
                resultado = teclado.nextDouble();
                if (resultado >= minimo && resultado <= maximo) {
                    esValido = true;
                } else {
                    System.out.println("El número debe estar entre " + minimo + " y " + maximo);
                    teclado.nextLine();
                }
            }else {
            System.out.println("Por favor, introduce un número válido.");
            teclado.nextLine();
            }
        } while (!esValido);
        return resultado;
    }

    /** Lee una hora por entrada de teclado, comprueba que esté en el formato hh:mm.
     *
     * @param teclado Scanner que lee la entrada de teclado.
     * @param mensaje Mensaje para imprimir por pantalla, pide una hora en formato hh:mm.
     * @return Hora en el formato correcto.
     */
    public static String leerHora(Scanner teclado, String mensaje) {
        boolean esValido = false;
        String resultado = "";
        try {
            do {
                String horaEntrada = leerCadena(teclado, mensaje);
                if (horaEntrada.contains(":")) {
                    String[] partes = horaEntrada.split(":");
                    int hora = Integer.parseInt(partes[0]);
                    int minuto = Integer.parseInt(partes[1]);
                    if ((partes[0].length() == 2) && (partes[1].length() == 2)) {
                        if (hora < 24 && hora >= 0){
                            if (minuto < 60 && minuto >= 0){
                                esValido = true;
                                resultado = horaEntrada;
                            } else {
                                System.out.println("Los minutos deben estar entre 00 y 59");
                            }
                        } else {
                            System.out.println("Las horas deben estar entre 00 y 23");
                        }
                    }
                } else {
                    System.out.println("Debe introducir una hora en el formato indicado.");
                }
            }while (!esValido);
        }catch (NumberFormatException e) {
            System.out.println("Debe introducir una hora en el formato indicado.");
        }
        return resultado;
    }

    // =========================================================================
    // Métodos de conversión de formatos
    // =========================================================================

    /** Calcula los minutos que han pasado desde medianoche hasta la hora recibida como parámetro.
     *
     * @param hora Hora en formato hh:mm.
     * @return Minutos que han pasado desde medianoche hasta la hora recibida como parámetro.
     */
    public static int horaAMinutos(String hora) {
        String[] partes = hora.split(":");
        return (Integer.parseInt(partes[0])*60) + Integer.parseInt(partes[1]);
    }

    /** Pasa los minutos recibidos como parámetro a horas, transcurridas desde medianoche, en formato hh:mm.
     *
     * @param minutos Minutos que se desean convertir a horas.
     * @return Minutos pasados a hora en formato hh:mm.
     */
    public static String minutosAHora(int minutos) {
        int hora = minutos/60;
        int mins = minutos%60;
        String resultado = hora + ":" + mins;
        if (hora >= 0 && hora < 10) {
            if (mins >= 0 && mins < 10) {
                resultado = "0" + hora + ":0" + mins;
            }else{
            resultado = "0" + hora + ":" + mins;
            }
        }else{
            if (mins >= 0 && mins < 10) {
                resultado = hora + ":0" + mins;
            }
        }
        return resultado;
    }

    /** Formatea una duración en minutos a un formato legible (ej. 1h 30min).
     *
     * @param duracionMinutos Minutos que se desean convertir.
     * @return Minutos formateados.
     */
    public static String formatearDuracion(int duracionMinutos) {
        String resultado = "";
        String cadena = minutosAHora(duracionMinutos);
        String[] partes = cadena.split(":");
        if (Integer.parseInt(partes[0]) == 0) {
            resultado = Integer.parseInt(partes[1]) + "min";
        } else if (Integer.parseInt(partes[1]) == 0) {
            resultado = Integer.parseInt(partes[0]) + "h";
        } else {
            resultado = Integer.parseInt(partes[0])+"h "+Integer.parseInt(partes[1])+"min";
        }
        return resultado;
    }

    /** Añade "€" a un decimal para expresar un precio.
     *
     * @param precio Número decimal para formatear.
     * @return Precio formateado.
     */
    public static String formatearPrecio(double precio) {
        String[] decimales = (Double.toString(precio)).split("\\.");
        String resultado = "";
        if (decimales[1].length() < 2) {
            resultado = decimales[0] + "."+decimales[1]+"0 €";
        }else {
            resultado = precio + " €";
        }
        return resultado;
    }

    /** Convierte una cadena con precio (ej: "12.50 €") a double
     *
     * @param precioStr String que representa un precio
     * @return double que representa un precio
     */
    public static double cadenaAPrecio(String precioStr) {
        String[] partes = precioStr.split(" ");
        return Double.parseDouble(partes[0]);
    }
}
