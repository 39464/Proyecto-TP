package es.upm;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests para la clase Actividad")
public class ActividadTest {
    @BeforeAll
    static void fixLocale() {
        Locale.setDefault(Locale.US);
    }

    @Test
    @DisplayName("Constructor de Actividad")
    void constructorActividad() {
        Actividad actividad = new Actividad("Museo", 3, 3);
        actividad.setDescripcion("Visita guiada");
        actividad.setPrecio(15.0);
        actividad.setDuracionMinutos(90);

        assertEquals("Museo", actividad.getNombre(), "El nombre de la actividad no es el esperado.");
        assertEquals("Visita guiada", actividad.getDescripcion(), "La descripción de la actividad no es la esperada.");
        assertEquals(15.0, actividad.getPrecio(), 0.0001, "El precio de la actividad no es el esperado.");
        assertEquals(90, actividad.getDuracionMinutos(), "La duración de la actividad no es la esperada.");
        assertEquals(3, actividad.getMaxRecursos(), "El número máximo de recursos no es el esperado.");
        assertEquals(3, actividad.getMaxComentarios(), "El número máximo de comentarios no es el esperado.");
    }

    @Test
    @DisplayName("Añadir recurso a actividad vacía")
    void agregarRecursoAActividadVacia() {
        Actividad actividad = new Actividad("Museo", 3, 3);
        assertEquals(Actividad.EXITO, actividad.agregarRecurso("Entrada"),
                "Debería devolver EXITO al añadir un recurso a una actividad vacía.");
        assertEquals(1, actividad.getNumRecursos(),
                "El número de recursos debería ser 1 tras añadir un recurso a una actividad vacía.");
    }

    @Test
    @DisplayName("Añadir recurso a actividad con recursos")
    void agregarRecursoAActividadConRecursos() {
        Actividad actividad = new Actividad("Museo", 3, 3);
        actividad.agregarRecurso("Entrada");
        assertEquals(Actividad.EXITO, actividad.agregarRecurso("Audioguía"),
                "Debería devolver EXITO al añadir un recurso a una actividad no completa.");
        assertEquals(2, actividad.getNumRecursos(),
                "El número de recursos debería ser 2 tras añadir un recurso a una actividad con un recurso.");
    }

    @Test
    @DisplayName("Añadir recurso a actividad completa")
    void agregarRecursoAActividadCompleta() {
        Actividad actividad = new Actividad("Museo", 1, 3);
        actividad.agregarRecurso("Entrada");
        assertEquals(Actividad.ERROR_RECURSOS_COMPLETOS, actividad.agregarRecurso("Audioguía"),
                "Debería devolver ERROR_RECURSOS_COMPLETOS al intentar añadir un recurso a una actividad completa.");
        assertEquals(1, actividad.getNumRecursos(),
                "El número de recursos no debería cambiar si no se añade el recurso.");
    }

    @Test
    @DisplayName("Añadir recurso inválido (null)")
    void agregarRecursoNull() {
        Actividad actividad = new Actividad("Museo", 3, 3);
        assertEquals(Actividad.ERROR_VALOR_INVALIDO, actividad.agregarRecurso(null),
                "Debería devolver ERROR_VALOR_INVALIDO al intentar añadir un recurso null.");
        assertEquals(0, actividad.getNumRecursos(),
                "El número de recursos no debería cambiar.");
    }

    @Test
    @DisplayName("Añadir recurso inválido (vacío)")
    void agregarRecursoVacio() {
        Actividad actividad = new Actividad("Museo", 3, 3);
        assertEquals(Actividad.ERROR_VALOR_INVALIDO, actividad.agregarRecurso("   "),
                "Debería devolver ERROR_VALOR_INVALIDO al intentar añadir un recurso en blanco.");
        assertEquals(0, actividad.getNumRecursos(),
                "El número de recursos no debería cambiar.");
    }

    @Test
    @DisplayName("Añadir comentario a actividad vacía")
    void agregarComentarioAActividadVacia() {
        Actividad actividad = new Actividad("Museo", 3, 3);
        assertEquals(Actividad.EXITO, actividad.agregarComentario("Muy interesante"),
                "Debería devolver EXITO al añadir un comentario a una actividad vacía.");
        assertEquals(1, actividad.getNumComentarios(),
                "El número de comentarios debería ser 1 tras añadir un comentario a una actividad vacía.");
    }

    @Test
    @DisplayName("Añadir comentario a actividad con comentarios")
    void agregarComentarioAActividadConComentarios() {
        Actividad actividad = new Actividad("Museo", 3, 3);
        actividad.agregarComentario("Muy interesante");
        assertEquals(Actividad.EXITO, actividad.agregarComentario("Guía excelente"),
                "Debería devolver EXITO al añadir un comentario a una actividad no completa.");
        assertEquals(2, actividad.getNumComentarios(),
                "El número de comentarios debería ser 2 tras añadir un comentario a una actividad con un comentario.");
    }

    @Test
    @DisplayName("Añadir comentario a actividad completa")
    void agregarComentarioAActividadCompleta() {
        Actividad actividad = new Actividad("Museo", 3, 1);
        actividad.agregarComentario("Muy interesante");
        assertEquals(Actividad.ERROR_COMENTARIOS_COMPLETOS, actividad.agregarComentario("Guía excelente"),
                "Debería devolver ERROR_COMENTARIOS_COMPLETOS al intentar añadir un comentario a una actividad completa.");
        assertEquals(1, actividad.getNumComentarios(),
                "El número de comentarios no debería cambiar si no se añade el comentario.");
    }

    @Test
    @DisplayName("Añadir comentario inválido (null)")
    void agregarComentarioNull() {
        Actividad actividad = new Actividad("Museo", 3, 3);
        assertEquals(Actividad.ERROR_VALOR_INVALIDO, actividad.agregarComentario(null),
                "Debería devolver ERROR_VALOR_INVALIDO al intentar añadir un comentario null.");
        assertEquals(0, actividad.getNumComentarios(),
                "El número de comentarios no debería cambiar.");
    }

    @Test
    @DisplayName("Añadir comentario inválido (vacío)")
    void agregarComentarioVacio() {
        Actividad actividad = new Actividad("Museo", 3, 3);
        assertEquals(Actividad.ERROR_VALOR_INVALIDO, actividad.agregarComentario("   "),
                "Debería devolver ERROR_VALOR_INVALIDO al intentar añadir un comentario en blanco.");
        assertEquals(0, actividad.getNumComentarios(),
                "El número de comentarios no debería cambiar.");
    }

    @Test
    @DisplayName("Comprobar actividad con recursos completos")
    void recursosCompletos() {
        Actividad actividad = new Actividad("Museo", 1, 3);
        actividad.agregarRecurso("Entrada");
        assertTrue(actividad.recursosCompletos(), "Debería decir que los recursos están completos.");
    }

    @Test
    @DisplayName("Comprobar actividad con recursos no completos")
    void recursosNoCompletos() {
        Actividad actividad = new Actividad("Museo", 2, 3);
        actividad.agregarRecurso("Entrada");
        assertFalse(actividad.recursosCompletos(), "Debería decir que los recursos no están completos.");
    }

    @Test
    @DisplayName("Comprobar actividad con comentarios completos")
    void comentariosCompletos() {
        Actividad actividad = new Actividad("Museo", 3, 1);
        actividad.agregarComentario("Muy interesante");
        assertTrue(actividad.comentariosCompletos(), "Debería decir que los comentarios están completos.");
    }

    @Test
    @DisplayName("Comprobar actividad con comentarios no completos")
    void comentariosNoCompletos() {
        Actividad actividad = new Actividad("Museo", 3, 2);
        actividad.agregarComentario("Muy interesante");
        assertFalse(actividad.comentariosCompletos(), "Debería decir que los comentarios no están completos.");
    }

    @ParameterizedTest
    @CsvSource({
            // nombre,           descripcion,     precio,   precioFMT   ,duracion, duracionFMT,  recurso1, recurso2, comentario1, comentario2
            "Museo,             Visita guiada,   12.5,      12.50 €,    90,       1h 30min        , Entrada, Audioguía, Muy interesante, Guía excelente",
            "Ruta en bici,      Nivel medio,     0,         0.00 €,     45,       45min          , Casco,   Agua,      Bonito recorrido, Buen ritmo",
            "Teatro,            Obra clásica,    18.25,     18.25 €,    120,      2h              , Entrada, Programa,  Gran actuación,  Sonido impecable"
    })
    @DisplayName("Formato textual de la actividad para representar en pantalla (toString)")
    void toStringFormatoCorrecto(String nombre, String descripcion, double precio, String precioFMT, int duracion,
                                 String duracionFMT, String recurso1, String recurso2, String comentario1, String comentario2) {

        Actividad actividad = new Actividad(nombre, 3, 3);
        actividad.setDescripcion(descripcion);
        actividad.setPrecio(precio);
        actividad.setDuracionMinutos(duracion);
        actividad.agregarRecurso(recurso1);
        actividad.agregarRecurso(recurso2);
        actividad.agregarComentario(comentario1);
        actividad.agregarComentario(comentario2);

        String expected = ""
                + "Actividad: " + nombre + "\n"
                + "Descripción: " + descripcion + "\n"
                + "Precio: " + precioFMT + "\n"
                + "Duración: " + duracionFMT + "\n"
                + "Recursos:\n"
                + "- " + recurso1 + "\n"
                + "- " + recurso2 + "\n"
                + "Comentarios:\n"
                + "1. " + comentario1 + "\n"
                + "2. " + comentario2 + "\n";

        assertEquals(expected, actividad.toString(), "El formato devuelto por el método toString() no es el correcto.");
    }

    @ParameterizedTest
    @DisplayName("Formato textual de la actividad para guardar en archivo (toRawString)")
    @CsvSource({
            // nombre,      descripcion,     precio, duracion, recurso1, recurso2, comentario1, comentario2
            "Museo,         Visita guiada,   12.5,   0,       Entrada, Audioguía, Muy interesante, Guía excelente",
            "Ruta en bici,  Nivel medio,     0,      45,       Casco,   Agua,      Bonito recorrido, Buen ritmo",
            "Teatro,        Obra clásica,    18.25,  120,      Entrada, Programa,  Gran actuación,  Sonido impecable"
    })
    void toRawStringFormatoCorrecto(String nombre, String descripcion, double precio, int duracion,
                                    String recurso1, String recurso2, String comentario1, String comentario2) {
        Actividad actividad = new Actividad(nombre, 3, 3);
        actividad.setDescripcion(descripcion);
        actividad.setPrecio(precio);
        actividad.setDuracionMinutos(duracion);
        actividad.agregarRecurso(recurso1);
        actividad.agregarRecurso(recurso2);
        actividad.agregarComentario(comentario1);
        actividad.agregarComentario(comentario2);

        String expected = String.format("%s\n%s\n%s\n%d\n%s\n%s\nCOMENTARIOS\n%s\n%s\n-----\n",
                nombre, descripcion, precio, duracion, recurso1, recurso2, comentario1, comentario2);

        assertEquals(expected, actividad.toRawString(),
                "El formato devuelto por el método toRawString() no es el correcto.");
    }

    @Test
    @DisplayName("fromBufferedReader - Actividad con recursos y comentarios dentro del límite")
    void fromBufferedReaderDentroDelLimite() throws IOException {
        // Crear un string que simula el contenido del archivo
        String contenido = """
                Museo del Prado
                Visita guiada al museo más importante de Madrid
                15.50
                120
                Entrada con descuento
                Audio guía incluida
                COMENTARIOS
                Muy interesante
                Gran colección de arte
                -----
                """;

        BufferedReader reader = new BufferedReader(new StringReader(contenido));
        Actividad actividad = Actividad.fromBufferedReader(reader, 5, 5);

        assertNotNull(actividad);
        assertEquals("Museo del Prado", actividad.getNombre());
        assertEquals("Visita guiada al museo más importante de Madrid", actividad.getDescripcion());
        assertEquals(15.50, actividad.getPrecio(), 0.01);
        assertEquals(120, actividad.getDuracionMinutos());
        assertEquals(2, actividad.getNumRecursos());
        assertEquals("Entrada con descuento", actividad.getRecursos()[0]);
        assertEquals("Audio guía incluida", actividad.getRecursos()[1]);
        assertEquals(2, actividad.getNumComentarios());
        assertEquals("Muy interesante", actividad.getComentarios()[0]);
        assertEquals("Gran colección de arte", actividad.getComentarios()[1]);
    }

    @Test
    @DisplayName("fromBufferedReader - Actividad con truncamiento de recursos y comentarios")
    void fromBufferedReaderConTruncamiento() throws IOException {
        // Crear un string con más recursos y comentarios de los que se permiten
        String contenido = """
                Parque de Atracciones
                Día completo de diversión y adrenalina
                45.00
                480
                Recurso 1
                Recurso 2
                Recurso 3
                Recurso 4
                Recurso 5
                COMENTARIOS
                Comentario 1
                Comentario 2
                Comentario 3
                Comentario 4
                -----
                """;

        BufferedReader reader = new BufferedReader(new StringReader(contenido));
        // Límite de solo 2 recursos y 2 comentarios
        Actividad actividad = Actividad.fromBufferedReader(reader, 2, 2);

        assertNotNull(actividad);
        assertEquals("Parque de Atracciones", actividad.getNombre());
        assertEquals("Día completo de diversión y adrenalina", actividad.getDescripcion());
        assertEquals(45.00, actividad.getPrecio(), 0.01);
        assertEquals(480, actividad.getDuracionMinutos());

        // Verificar que solo se almacenaron 2 recursos (truncado)
        assertEquals(2, actividad.getNumRecursos());
        assertEquals(2, actividad.getMaxRecursos());
        assertEquals("Recurso 1", actividad.getRecursos()[0]);
        assertEquals("Recurso 2", actividad.getRecursos()[1]);

        // Verificar que solo se almacenaron 2 comentarios (truncado)
        assertEquals(2, actividad.getNumComentarios());
        assertEquals(2, actividad.getMaxComentarios());
        assertEquals("Comentario 1", actividad.getComentarios()[0]);
        assertEquals("Comentario 2", actividad.getComentarios()[1]);
    }
}

