package es.upm;

import java.util.Scanner;

/**
 * Clase con métodos de utilidad para la entrada de datos por teclado y conversión de formatos.
 */
public class Utilidades {
// =========================================================================
    // Métodos de entrada por teclado
    // =========================================================================

    public static String leerCadena(Scanner teclado, String s) {
        System.out.println(s);
        return teclado.nextLine();
    }

    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int resultado = 0;
        boolean esValido = false;
        do{
            System.out.println(mensaje);
            if (teclado.hasNextInt()) {
                resultado = teclado.nextInt();
                if  (resultado > minimo || resultado < maximo) {
                    esValido = true;
                } else {
                    System.out.println("El número debe estar entre "+minimo+" y "+maximo);
                }
            } else {
                resultado = maximo/2;
                System.out.println("Por favor, introduce un número válido");
                teclado.nextLine();
            }
        } while (!esValido);
        return resultado; // lee un número entero
    }

    public static double leerDouble(Scanner teclado, String mensaje, double minimo, double maximo) {
        double resultado = 0.0;
        boolean esValido = false;
        do {
            try {
                System.out.println(mensaje);
                if (teclado.hasNextDouble()) {
                    resultado = teclado.nextDouble();
                    if (resultado > minimo || resultado < maximo) {
                        esValido = true;
                    } else {
                        System.out.println("El número debe estar entre " + minimo + " y " + maximo);
                        teclado.nextLine();
                    }
                } else {
                    System.out.println("Por favor, introduce un número válido.");
                    teclado.nextLine();
                }
            }catch (NumberFormatException e){
                System.out.println("Por favor, introduce un número válido.");
            }
        } while (!esValido);
        return resultado; //lee un número decimal en el rango [minimo, maximo]
    }

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
                            } else{
                                System.out.println("Los minutos deben estar entre 00 y 59");
                            }
                        } else{
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
        return resultado; // Muestra un mensaje y lee una hora en formato "HH:MM"
    }

    // =========================================================================
    // Métodos de conversión de formatos
    // =========================================================================

    public static int horaAMinutos(String hora) {
        String[] partes = hora.split(":");
        return (Integer.parseInt(partes[0])*60) + Integer.parseInt(partes[1]); // Convierte una hora en formato "HH:MM" a minutos desde medianoche
    }

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
        return resultado; // Convierte minutos desde medianoche a formato "HH:MM"
    }

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
    }// Formatea una duración en minutos a formato legible (ej: 90 -> "1h 30min")

    public static String formatearPrecio(double precio) {
        String[] decimales= (Double.toString(precio)).split("\\.");
        String resultado = "";
        if (decimales[1].length() < 2) {
            resultado = decimales[0] + "."+decimales[1]+"0 €";
        }else{
            resultado = precio + " €";
        }
        return resultado;
    }

    // Formatea un precio a formato legible (ej: 12.50 -> "12.50 €")

    public static double cadenaAPrecio(String precioStr) {
        String[] partes = precioStr.split(" ");
        return Double.parseDouble(partes[0]); // Convierte una cadena con precio (ej: "12.50 €") a double
    }

}
