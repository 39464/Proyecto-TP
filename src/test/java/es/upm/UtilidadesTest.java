package es.upm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

@DisplayName("Tests para la clase Utilidades")
public class UtilidadesTest {

    @ParameterizedTest
    @DisplayName("Leer cadena de texto")
    @ValueSource(strings = {"Hola Mundo", "1234", "Texto con espacios"})
    void leerCadenaDeTexto(String entrada) {
        Scanner teclado = new Scanner(entrada + "\n");
        String resultado = Utilidades.leerCadena(teclado, "Introduce un texto: ");
        assertEquals(entrada, resultado);
    }

    @ParameterizedTest
    @DisplayName("Leer número dentro del rango")
    @ValueSource(ints = {1, 2, 3, 4, 5, 10})
    void leerNumeroDentroDelRango(int entrada) {
        Scanner teclado = new Scanner(entrada + "\n");
        int resultado = Utilidades.leerNumero(teclado, "Introduce un número entre 1 y 10: ", 1, 10);
        assertEquals(entrada, resultado);
    }

    @ParameterizedTest
    @DisplayName("Leer número fuera del rango")
    @CsvSource({"0, 5", "11, 5", "0, 10", "11, 10"})
    void leerNumeroFueraDelRango(int entradaMala, int entradaBuena) {
        Scanner teclado = new Scanner(entradaMala + "\n" + entradaMala + "\n" + entradaBuena + "\n");
        int resultado = Utilidades.leerNumero(teclado, "Introduce un número entre 1 y 10: ", 1, 10);
        assertEquals(entradaBuena, resultado);
    }

    @ParameterizedTest
    @DisplayName("Leer double dentro del rango")
    @ValueSource(doubles = {1.0, 2.5, 5.75, 10.0})
    void leerDoubleDentroDelRango(double entrada) {
        Scanner teclado = new Scanner(entrada + "\n");
        double resultado = Utilidades.leerDouble(teclado, "Introduce un número entre 1.0 y 10.0: ", 1.0, 10.0);
        assertEquals(entrada, resultado, 0.001);
    }

    @ParameterizedTest
    @DisplayName("Leer double fuera del rango")
    @CsvSource({"0.0, 5.0", "11.0, 5.0", "0.5, 10.0", "10.5, 8.5"})
    void leerDoubleFueraDelRango(double entradaMala, double entradaBuena) {
        Scanner teclado = new Scanner(entradaMala + "\n" + entradaMala + "\n" + entradaBuena + "\n");
        double resultado = Utilidades.leerDouble(teclado, "Introduce un número entre 1.0 y 10.0: ", 1.0, 10.0);
        assertEquals(entradaBuena, resultado, 0.001);
    }

    @ParameterizedTest
    @DisplayName("Leer hora válida")
    @ValueSource(strings = {"00:00", "09:30", "12:00", "23:59", "15:45"})
    void leerHoraValida(String entrada) {
        Scanner teclado = new Scanner(entrada + "\n");
        String resultado = Utilidades.leerHora(teclado, "Introduce una hora (HH:MM): ");
        assertEquals(entrada, resultado);
    }

    @ParameterizedTest
    @DisplayName("Leer hora inválida y luego válida")
    @CsvSource({"24:00, 12:00", "09:60, 09:30", "9:30, 09:30", "abc, 10:00"})
    void leerHoraInvalida(String entradaMala, String entradaBuena) {
        Scanner teclado = new Scanner(entradaMala + "\n" + entradaMala + "\n" + entradaBuena + "\n");
        String resultado = Utilidades.leerHora(teclado, "Introduce una hora (HH:MM): ");
        assertEquals(entradaBuena, resultado);
    }

    @Test
    @DisplayName("Convertir hora a minutos")
    void convertirHoraAMinutos() {
        assertEquals(0, Utilidades.horaAMinutos("00:00"));
        assertEquals(60, Utilidades.horaAMinutos("01:00"));
        assertEquals(570, Utilidades.horaAMinutos("09:30"));
        assertEquals(720, Utilidades.horaAMinutos("12:00"));
        assertEquals(1439, Utilidades.horaAMinutos("23:59"));
    }

    @Test
    @DisplayName("Convertir minutos a hora")
    void convertirMinutosAHora() {
        assertEquals("00:00", Utilidades.minutosAHora(0));
        assertEquals("01:00", Utilidades.minutosAHora(60));
        assertEquals("09:30", Utilidades.minutosAHora(570));
        assertEquals("12:00", Utilidades.minutosAHora(720));
        assertEquals("23:59", Utilidades.minutosAHora(1439));
    }

    @Test
    @DisplayName("Formatear duración en minutos")
    void formatearDuracion() {
        assertEquals("30min", Utilidades.formatearDuracion(30));
        assertEquals("1h", Utilidades.formatearDuracion(60));
        assertEquals("1h 30min", Utilidades.formatearDuracion(90));
        assertEquals("2h", Utilidades.formatearDuracion(120));
        assertEquals("2h 15min", Utilidades.formatearDuracion(135));
        assertEquals("3h", Utilidades.formatearDuracion(180));
    }

    @Test
    @DisplayName("Formatear precio con dos decimales")
    void formatearPrecio() {
        assertEquals("0.00 €", Utilidades.formatearPrecio(0.0));
        assertEquals("12.50 €", Utilidades.formatearPrecio(12.5));
        assertEquals("99.99 €", Utilidades.formatearPrecio(99.99));
        assertEquals("100.00 €", Utilidades.formatearPrecio(100));
        assertEquals("5.00 €", Utilidades.formatearPrecio(5));
    }

    @Test
    @DisplayName("Convertir cadena con precio a double")
    void cadenaAPrecio() {
        assertEquals(0.0, Utilidades.cadenaAPrecio("0.00 €"), 0.001);
        assertEquals(12.5, Utilidades.cadenaAPrecio("12.50 €"), 0.001);
        assertEquals(99.99, Utilidades.cadenaAPrecio("99.99 €"), 0.001);
        assertEquals(100.0, Utilidades.cadenaAPrecio("100.00 €"), 0.001);
        assertEquals(5.0, Utilidades.cadenaAPrecio("5.00 €"), 0.001);
    }

}
