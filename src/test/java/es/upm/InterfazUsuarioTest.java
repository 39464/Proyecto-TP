package es.upm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Tests de integración de InterfazUsuario")
public class InterfazUsuarioTest {
    @Test
    @DisplayName("Menú principal")
    void menuPrincipal() {
        List<String> salidaEsperada = Arrays.asList(
                ">> --- Menú Principal --- >>",
                "1. Agregar Actividad",
                "2. Consultar/Editar Actividad",
                "3. Guardar Actividades",
                "4. Cargar Actividades",
                "5. Planificar Viaje",
                "6. Guardar Itinerario",
                "7. Salir",
                ">> Elige una opción: >>"
        );

        String entrada = "7\n";

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        CatalogoActividades catalogo = new CatalogoActividades(10);
        Viaje viaje = new Viaje(3, 5);
        InterfazUsuario interfaz = new InterfazUsuario(catalogo, viaje, 3, 3);

        Scanner scanner = new Scanner(System.in);
        interfaz.iniciar(scanner);

        List<String> salidaActual = Arrays.asList(salidaCapturada.toString().split("\\r?\\n"));

        assertLinesMatch(salidaEsperada, salidaActual, "El menú principal no se corresponde con los requisitos");
    }

    @Test
    @DisplayName("Menu: Agregar actividad satisfactoriamente")
    void agregarActividadSatisfactoriamente() {
        String entrada = "1\nMuseo del Prado\nVisita guiada al museo más importante de Madrid\n15.50\n120\nEntrada con descuento\nAudio guía incluida\nfin\nMuy interesante\nGran colección de arte\nfin\n7\n";

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        CatalogoActividades catalogo = new CatalogoActividades(10);
        Viaje viaje = new Viaje(3, 5);
        InterfazUsuario interfaz = new InterfazUsuario(catalogo, viaje, 5, 5);

        Scanner scanner = new Scanner(System.in);
        interfaz.iniciar(scanner);

        assertTrue(salidaCapturada.toString().contains("Nombre de la actividad: "), "Hay que solicitar el nombre de la actividad");
        assertTrue(salidaCapturada.toString().contains("Descripción: "), "Hay que solicitar la descripción de la actividad");
        assertTrue(salidaCapturada.toString().contains("Precio (€): "), "Hay que solicitar el precio de la actividad");
        assertTrue(salidaCapturada.toString().contains("Duración (minutos): "), "Hay que solicitar la duración de la actividad");
        assertTrue(salidaCapturada.toString().contains("Introduce los recursos (una línea por recurso, escribe 'fin' para terminar):"), "Hay que solicitar los recursos de la actividad");
        assertTrue(salidaCapturada.toString().contains("Introduce los comentarios (una línea por comentario, escribe 'fin' para terminar):"), "Hay que solicitar los comentarios de la actividad");
        assertTrue(salidaCapturada.toString().contains("¡Actividad agregada exitosamente!"), "Hay que avisar al usuario de que la actividad se ha agregado correctamente");
    }

    @Test
    @DisplayName("Menu: Agregar actividad fallida")
    void agregarActividadFallida() {
        String entrada = "1\nMuseo del Prado\nVisita guiada\n15.50\n120\nEntrada\nfin\nInteresante\nfin\n7\n";

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        CatalogoActividades catalogo = new CatalogoActividades(0);
        Viaje viaje = new Viaje(3, 5);
        InterfazUsuario interfaz = new InterfazUsuario(catalogo, viaje, 3, 3);

        Scanner scanner = new Scanner(System.in);
        interfaz.iniciar(scanner);

        assertTrue(salidaCapturada.toString().contains("No se pueden añadir más actividades."), "Hay que avisar al usuario de que la actividad no se ha podido agregar");
    }

    @Test
    @DisplayName("Menu: Consultar, seleccionar y editar actividad")
    void consultarActividad() {
        String entrada = """
                1
                Museo del Prado
                Visita guiada al museo
                15.50
                120
                Entrada con descuento
                fin
                Muy interesante
                fin
                2
                museo
                1
                1
                Mapa del museo
                2
                prado
                1
                2
                Excelente experiencia
                2
                prado
                1
                3
                7
                """;

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        CatalogoActividades catalogo = new CatalogoActividades(10);
        Viaje viaje = new Viaje(3, 5);
        InterfazUsuario interfaz = new InterfazUsuario(catalogo, viaje, 5, 5);

        Scanner scanner = new Scanner(System.in);
        interfaz.iniciar(scanner);

        assertTrue(salidaCapturada.toString().contains("Introduce el texto de la actividad a buscar (-FIN- para volver): "), "Hay que solicitar el texto de la actividad a buscar");
        assertTrue(salidaCapturada.toString().contains("Actividades encontradas:"), "Hay que mostrar la cabecera Actividades encontradas:");
        assertTrue(salidaCapturada.toString().contains("1. Museo del Prado"), "Hay que mostrar la actividad encontrada");
        assertTrue(salidaCapturada.toString().contains("1. Añadir recurso"), "Hay que mostrar las opciones de edición de la actividad");
        assertTrue(salidaCapturada.toString().contains("2. Añadir comentario"), "Hay que mostrar las opciones de edición de la actividad");
        assertTrue(salidaCapturada.toString().contains("3. Eliminar actividad"), "Hay que mostrar las opciones de edición de la actividad");
        assertTrue(salidaCapturada.toString().contains("4. Volver"), "Hay que mostrar las opciones de edición de la actividad");
        assertTrue(salidaCapturada.toString().contains("Introduce el recurso a añadir: "), "Hay que solicitar el recurso a añadir");
        assertTrue(salidaCapturada.toString().contains("Introduce el comentario a añadir: "), "Hay que solicitar el comentario a añadir");
        assertTrue(salidaCapturada.toString().contains("Actividad eliminada."), "Hay que avisar al usuario de que la actividad se ha eliminado correctamente");
    }

    @Test
    @DisplayName("Menu: Planificar viaje")
    void planificarViaje() {
        String entrada = """
                1
                Museo del Prado
                Visita guiada al museo
                15.50
                120
                Entrada con descuento
                fin
                Muy interesante
                fin
                5
                1
                09:30
                museo
                1
                7
                """;

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        CatalogoActividades catalogo = new CatalogoActividades(10);
        Viaje viaje = new Viaje(3, 5);
        InterfazUsuario interfaz = new InterfazUsuario(catalogo, viaje, 5, 5);

        Scanner scanner = new Scanner(System.in);
        interfaz.iniciar(scanner);

        assertTrue(salidaCapturada.toString().contains("Planificación del viaje:"), "Hay que mostrar la cabecera de la planificación del viaje");
        assertTrue(salidaCapturada.toString().contains("Introduce el día del viaje (1-3): "), "Hay que solicitar el día del viaje");
        assertTrue(salidaCapturada.toString().contains("Introduce la hora de inicio (HH:MM): "), "Hay que solicitar la hora de inicio");
        assertTrue(salidaCapturada.toString().contains("Actividades encontradas:"), "Hay que solicitar la actividad a planificar");
        assertTrue(salidaCapturada.toString().contains("Actividad planificada para el día 1 a las 09:30"), "Hay que avisar al usuario de que la actividad se ha planificado correctamente");
    }

    @Test
    @DisplayName("Menu: Guardar actividades")
    void guardarActividades() {
        String entrada = """
                3
                actividades.txt
                7
                """;

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        CatalogoActividades catalogo = new CatalogoActividades(10);
        Viaje viaje = new Viaje(3, 5);
        InterfazUsuario interfaz = new InterfazUsuario(catalogo, viaje, 3, 3);

        Scanner scanner = new Scanner(System.in);
        interfaz.iniciar(scanner);

        assertTrue(salidaCapturada.toString().contains("Introduce el nombre del archivo donde guardar las actividades: "), "Hay que solicitar el nombre del archivo donde guardar las actividades");
        assertTrue(salidaCapturada.toString().contains("Actividades guardadas en"), "Hay que avisar al usuario de que las actividades se han guardado correctamente");
    }

    @Test
    @DisplayName("Menu: Cargar actividades")
    void cargarActividades() {
        String entrada = """
                4
                src/test/resources/actividades_predefinidas.txt
                7
                """;

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        CatalogoActividades catalogo = new CatalogoActividades(10);
        Viaje viaje = new Viaje(3, 5);
        InterfazUsuario interfaz = new InterfazUsuario(catalogo, viaje, 3, 3);

        Scanner scanner = new Scanner(System.in);
        interfaz.iniciar(scanner);

        assertTrue(salidaCapturada.toString().contains("Introduce la ruta del archivo de donde cargar las actividades: "), "Hay que solicitar el nombre del archivo donde cargar las actividades");
        assertTrue(salidaCapturada.toString().contains("Actividades cargadas desde"), "Hay que avisar al usuario de que las actividades se han cargado correctamente");
    }

    @Test
    @DisplayName("Menu: Guardar itinerario")
    void guardarItinerario() {
        String entrada = """
                6
                itinerario.txt
                7
                """;

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);

        ByteArrayOutputStream salidaCapturada = new ByteArrayOutputStream();
        PrintStream salidaSimulada = new PrintStream(salidaCapturada);
        System.setOut(salidaSimulada);

        CatalogoActividades catalogo = new CatalogoActividades(10);
        Viaje viaje = new Viaje(3, 5);
        InterfazUsuario interfaz = new InterfazUsuario(catalogo, viaje, 3, 3);

        Scanner scanner = new Scanner(System.in);
        interfaz.iniciar(scanner);

        assertTrue(salidaCapturada.toString().contains("Introduce el nombre del archivo donde guardar el itinerario: "), "Hay que solicitar el nombre del archivo donde guardar el itinerario");
        assertTrue(salidaCapturada.toString().contains("Itinerario guardado en"), "Hay que avisar al usuario de que el itinerario se ha guardado correctamente");
    }
}

