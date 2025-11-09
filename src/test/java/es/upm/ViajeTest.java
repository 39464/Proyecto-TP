package es.upm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@DisplayName("Tests para la clase Viaje")
public class ViajeTest {

    @ParameterizedTest
    @DisplayName("Agregar actividad a un día específico del viaje")
    @ValueSource(strings = {"Visita al museo", "Tour gastronómico", "Excursión al monte"})
    void agregarActividadADiaEspecifico(String nombreActividad) {
        Viaje planificador = new Viaje(10, 5);
        Actividad actividad = new Actividad(nombreActividad, 5, 5);
        actividad.setDescripcion("Descripción genérica");
        actividad.setPrecio(50.0);
        actividad.setDuracionMinutos(120);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, actividad, "09:00"),
                "Debería devolver EXITO al agregar actividad exitosamente");
        assertTrue(planificador.toString().contains(nombreActividad));
    }

    @ParameterizedTest
    @DisplayName("Agregar actividades a varios días")
    @CsvSource({
            "Visita al museo, Tour gastronómico, Excursión al monte",
            "Paseo en barco, Cena típica, Observación de estrellas",
            "Clase de surf, Taller de cerámica, Espectáculo de danza"
    })
    void agregarActividadAVariosDias(String act1, String act2, String act3) {
        Viaje planificador = new Viaje(7, 5);

        Actividad a1 = new Actividad(act1, 5, 5);
        a1.setDescripcion("");
        a1.setPrecio(30.0);
        a1.setDuracionMinutos(60);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a1, "09:00"));

        Actividad a2 = new Actividad(act2, 5, 5);
        a2.setDescripcion("");
        a2.setPrecio(40.0);
        a2.setDuracionMinutos(90);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(1, a2, "10:00"));

        Actividad a3 = new Actividad(act3, 5, 5);
        a3.setDescripcion("");
        a3.setPrecio(25.0);
        a3.setDuracionMinutos(45);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(2, a3, "11:00"));

        String plan = planificador.toString();
        assertTrue(plan.contains(act1));
        assertTrue(plan.contains(act2));
        assertTrue(plan.contains(act3));
    }

    @ParameterizedTest
    @DisplayName("Agregar actividad con solapamiento en un día")
    @CsvSource({
            "Visita al museo, Tour gastronómico",
            "Paseo en barco, Cena típica",
            "Clase de surf, Taller de cerámica"
    })
    void agregarActividadADiaOcupado(String act1, String act2) {
        Viaje planificador = new Viaje(7, 5);

        Actividad a1 = new Actividad(act1, 5, 5);
        a1.setDescripcion("");
        a1.setPrecio(20.0);
        a1.setDuracionMinutos(60);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(2, a1, "09:00"));

        Actividad a2 = new Actividad(act2, 5, 5);
        a2.setDescripcion("");
        a2.setPrecio(35.0);
        a2.setDuracionMinutos(90);
        assertEquals(Viaje.ERROR_SOLAPAMIENTO, planificador.agregarActividad(2, a2, "09:00"),
                "Debería devolver ERROR_SOLAPAMIENTO cuando hay solapamiento de horarios");

        String plan = planificador.toString();
        // La segunda actividad se solapa con la primera (misma hora), por lo que no debería agregarse
        assertTrue(plan.contains(act1));
        assertFalse(plan.contains(act2));
    }

    @Test
    @DisplayName("Agregar actividad a día inválido (negativo)")
    void agregarActividadADiaInvalidoNegativo() {
        Viaje planificador = new Viaje(7, 5);
        Actividad actividad = new Actividad("Test", 5, 5);
        actividad.setDescripcion("");
        actividad.setPrecio(10.0);
        actividad.setDuracionMinutos(60);

        assertEquals(Viaje.ERROR_DIA_INVALIDO, planificador.agregarActividad(-1, actividad, "09:00"),
                "Debería devolver ERROR_DIA_INVALIDO para día negativo");
    }

    @Test
    @DisplayName("Agregar actividad a día inválido (mayor que numDias)")
    void agregarActividadADiaInvalidoMayor() {
        Viaje planificador = new Viaje(7, 5);
        Actividad actividad = new Actividad("Test", 5, 5);
        actividad.setDescripcion("");
        actividad.setPrecio(10.0);
        actividad.setDuracionMinutos(60);

        assertEquals(Viaje.ERROR_DIA_INVALIDO, planificador.agregarActividad(7, actividad, "09:00"),
                "Debería devolver ERROR_DIA_INVALIDO para día >= numDias");
    }

    @Test
    @DisplayName("Agregar actividad a día completo")
    void agregarActividadADiaCompleto() {
        Viaje planificador = new Viaje(7, 2); // Solo 2 actividades por día

        Actividad a1 = new Actividad("Actividad 1", 5, 5);
        a1.setDescripcion("");
        a1.setPrecio(10.0);
        a1.setDuracionMinutos(60);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a1, "09:00"));

        Actividad a2 = new Actividad("Actividad 2", 5, 5);
        a2.setDescripcion("");
        a2.setPrecio(10.0);
        a2.setDuracionMinutos(60);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a2, "11:00"));

        Actividad a3 = new Actividad("Actividad 3", 5, 5);
        a3.setDescripcion("");
        a3.setPrecio(10.0);
        a3.setDuracionMinutos(60);
        assertEquals(Viaje.ERROR_DIA_COMPLETO, planificador.agregarActividad(0, a3, "13:00"),
                "Debería devolver ERROR_DIA_COMPLETO cuando el día está completo");
    }

    @Test
    @DisplayName("Representación textual del planificador sin actividades")
    void ViajeSinActividades() {
        String esperado = """
                -------------------------------------------------------------------
                Día 1
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Día 2
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Día 3
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Día 4
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Día 5
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Día 6
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Día 7
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Resumen:
                - Días: 7
                - Actividades: 0
                - Precio: 0.00 €
                """;
        Viaje planificador = new Viaje(7, 5);
        String plan = planificador.toString();
        assertEquals(esperado, plan);
    }

    @Test
    @DisplayName("Representación textual del planificador con algunas actividades")
    void ViajeConActividades() {
        String esperado = """
                -------------------------------------------------------------------
                Día 1
                -------------------------------------------------------------------
                09:00 Visita al museo

                -------------------------------------------------------------------
                Día 2
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Día 3
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Día 4
                -------------------------------------------------------------------
                10:00 Tour gastronómico

                -------------------------------------------------------------------
                Día 5
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Día 6
                -------------------------------------------------------------------
                (No hay actividades)

                -------------------------------------------------------------------
                Día 7
                -------------------------------------------------------------------
                11:00 Espectáculo nocturno

                -------------------------------------------------------------------
                Resumen:
                - Días: 7
                - Actividades: 3
                - Precio: 120.00 €
                """;
        Viaje planificador = new Viaje(7, 5);

        Actividad a1 = new Actividad("Visita al museo", 5, 5);
        a1.setDescripcion("");
        a1.setPrecio(30.0);
        a1.setDuracionMinutos(120);
        planificador.agregarActividad(0, a1, "09:00");

        Actividad a2 = new Actividad("Tour gastronómico", 5, 5);
        a2.setDescripcion("");
        a2.setPrecio(50.0);
        a2.setDuracionMinutos(180);
        planificador.agregarActividad(3, a2, "10:00");

        Actividad a3 = new Actividad("Espectáculo nocturno", 5, 5);
        a3.setDescripcion("");
        a3.setPrecio(40.0);
        a3.setDuracionMinutos(90);
        planificador.agregarActividad(6, a3, "11:00");

        String plan = planificador.toString();
        assertEquals(esperado, plan);
    }

    @Test
    @DisplayName("Guardar plan de viaje en archivo")
    void guardarPlanEnArchivo() throws IOException {
        Viaje planificador = new Viaje(7, 5);

        Actividad a1 = new Actividad("Visita al museo", 5, 5);
        a1.setDescripcion("");
        a1.setPrecio(30.0);
        a1.setDuracionMinutos(120);
        planificador.agregarActividad(0, a1, "09:00");

        Actividad a2 = new Actividad("Tour gastronómico", 5, 5);
        a2.setDescripcion("");
        a2.setPrecio(50.0);
        a2.setDuracionMinutos(180);
        planificador.agregarActividad(3, a2, "10:00");

        Actividad a3 = new Actividad("Espectáculo nocturno", 5, 5);
        a3.setDescripcion("");
        a3.setPrecio(40.0);
        a3.setDuracionMinutos(90);
        planificador.agregarActividad(6, a3, "11:00");

        Path ficheroTemporal = Files.createTempFile("viaje", "tmp");
        planificador.guardarItinerario(ficheroTemporal.toString());

        assertTrue(Files.exists(ficheroTemporal));
        assertTrue(Files.size(ficheroTemporal) > 0);

        String esperado = """
                Día 1: 09:00 Visita al museo (dur 2h, 30.00 €)
                Día 2: ---
                Día 3: ---
                Día 4: 10:00 Tour gastronómico (dur 3h, 50.00 €)
                Día 5: ---
                Día 6: ---
                Día 7: 11:00 Espectáculo nocturno (dur 1h 30min, 40.00 €)
                Resumen: Días: 7; Actividades: 3; Precio total: 120.00 €
                """;
        String contenido = Files.readString(ficheroTemporal);
        assertEquals(esperado, contenido);
        Files.deleteIfExists(ficheroTemporal);
    }

    // ========== NUEVOS TESTS PARA SOLAPAMIENTO ==========

    @Test
    @DisplayName("Solapamiento: actividad que comienza durante otra actividad")
    void solapamientoComenzandoDuranteOtraActividad() {
        Viaje planificador = new Viaje(5, 5);

        // Primera actividad: 09:00 - 11:00 (120 minutos)
        Actividad a1 = new Actividad("Desayuno", 5, 5);
        a1.setDuracionMinutos(120);
        a1.setPrecio(15.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a1, "09:00"));

        // Intenta agregar: 10:00 - 11:30 (90 minutos) - se solapa
        Actividad a2 = new Actividad("Reunión", 5, 5);
        a2.setDuracionMinutos(90);
        a2.setPrecio(0.0);
        assertEquals(Viaje.ERROR_SOLAPAMIENTO, planificador.agregarActividad(0, a2, "10:00"));

        // Verifica que solo existe la primera actividad
        assertEquals(1, planificador.getNumActividadesDia(0));
    }

    @Test
    @DisplayName("Solapamiento: actividad que termina durante otra actividad")
    void solapamientoTerminandoDuranteOtraActividad() {
        Viaje planificador = new Viaje(5, 5);

        // Primera actividad: 10:00 - 12:00 (120 minutos)
        Actividad a1 = new Actividad("Clase", 5, 5);
        a1.setDuracionMinutos(120);
        a1.setPrecio(30.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a1, "10:00"));

        // Intenta agregar: 09:00 - 10:30 (90 minutos) - se solapa
        Actividad a2 = new Actividad("Gimnasio", 5, 5);
        a2.setDuracionMinutos(90);
        a2.setPrecio(20.0);
        assertEquals(Viaje.ERROR_SOLAPAMIENTO, planificador.agregarActividad(0, a2, "09:00"));

        assertEquals(1, planificador.getNumActividadesDia(0));
    }

    @Test
    @DisplayName("Solapamiento: actividad que contiene completamente a otra")
    void solapamientoActividadContieneOtra() {
        Viaje planificador = new Viaje(5, 5);

        // Primera actividad: 10:00 - 11:00 (60 minutos)
        Actividad a1 = new Actividad("Café", 5, 5);
        a1.setDuracionMinutos(60);
        a1.setPrecio(5.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a1, "10:00"));

        // Intenta agregar: 09:00 - 12:00 (180 minutos) - contiene a la primera
        Actividad a2 = new Actividad("Excursión", 5, 5);
        a2.setDuracionMinutos(180);
        a2.setPrecio(50.0);
        assertEquals(Viaje.ERROR_SOLAPAMIENTO, planificador.agregarActividad(0, a2, "09:00"));

        assertEquals(1, planificador.getNumActividadesDia(0));
    }

    @Test
    @DisplayName("Solapamiento: actividad contenida dentro de otra")
    void solapamientoActividadContenidaDentroDeOtra() {
        Viaje planificador = new Viaje(5, 5);

        // Primera actividad: 09:00 - 13:00 (240 minutos)
        Actividad a1 = new Actividad("Seminario", 5, 5);
        a1.setDuracionMinutos(240);
        a1.setPrecio(80.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a1, "09:00"));

        // Intenta agregar: 10:00 - 11:00 (60 minutos) - contenida en la primera
        Actividad a2 = new Actividad("Pausa", 5, 5);
        a2.setDuracionMinutos(60);
        a2.setPrecio(0.0);
        assertEquals(Viaje.ERROR_SOLAPAMIENTO, planificador.agregarActividad(0, a2, "10:00"));

        assertEquals(1, planificador.getNumActividadesDia(0));
    }

    @Test
    @DisplayName("No hay solapamiento: actividades consecutivas (fin de una = inicio de otra)")
    void noSolapamientoActividadesConsecutivas() {
        Viaje planificador = new Viaje(5, 5);

        // Primera actividad: 09:00 - 10:00 (60 minutos)
        Actividad a1 = new Actividad("Desayuno", 5, 5);
        a1.setDuracionMinutos(60);
        a1.setPrecio(10.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a1, "09:00"));

        // Segunda actividad: 10:00 - 11:00 (60 minutos) - NO se solapa
        Actividad a2 = new Actividad("Reunión", 5, 5);
        a2.setDuracionMinutos(60);
        a2.setPrecio(0.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a2, "10:00"));

        assertEquals(2, planificador.getNumActividadesDia(0));
    }

    @Test
    @DisplayName("No hay solapamiento: actividades con huecos")
    void noSolapamientoActividadesConHuecos() {
        Viaje planificador = new Viaje(5, 5);

        // Primera actividad: 09:00 - 10:00 (60 minutos)
        Actividad a1 = new Actividad("Desayuno", 5, 5);
        a1.setDuracionMinutos(60);
        a1.setPrecio(10.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a1, "09:00"));

        // Segunda actividad: 12:00 - 13:00 (60 minutos) - NO se solapa
        Actividad a2 = new Actividad("Almuerzo", 5, 5);
        a2.setDuracionMinutos(60);
        a2.setPrecio(15.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a2, "12:00"));

        // Tercera actividad: 10:30 - 11:30 (60 minutos) - llena el hueco
        Actividad a3 = new Actividad("Café", 5, 5);
        a3.setDuracionMinutos(60);
        a3.setPrecio(5.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a3, "10:30"));

        assertEquals(3, planificador.getNumActividadesDia(0));
    }

    @Test
    @DisplayName("Solapamiento: múltiples actividades con un minuto de diferencia")
    void solapamientoUnMinutoDeDiferencia() {
        Viaje planificador = new Viaje(5, 5);

        // Primera actividad: 09:00 - 10:30 (90 minutos)
        Actividad a1 = new Actividad("Taller", 5, 5);
        a1.setDuracionMinutos(90);
        a1.setPrecio(25.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a1, "09:00"));

        // Intenta agregar: 10:29 - 11:29 (60 minutos) - se solapa por 1 minuto
        Actividad a2 = new Actividad("Conferencia", 5, 5);
        a2.setDuracionMinutos(60);
        a2.setPrecio(30.0);
        assertEquals(Viaje.ERROR_SOLAPAMIENTO, planificador.agregarActividad(0, a2, "10:29"));

        assertEquals(1, planificador.getNumActividadesDia(0));
    }

    @Test
    @DisplayName("Validación: día inválido negativo")
    void diaInvalidoNegativo() {
        Viaje planificador = new Viaje(7, 5);

        Actividad a1 = new Actividad("Actividad", 5, 5);
        a1.setDuracionMinutos(60);
        a1.setPrecio(10.0);
        assertEquals(Viaje.ERROR_DIA_INVALIDO, planificador.agregarActividad(-1, a1, "09:00"));
    }

    @Test
    @DisplayName("Validación: día inválido mayor que numDias")
    void diaInvalidoFueraDeRango() {
        Viaje planificador = new Viaje(7, 5);

        Actividad a1 = new Actividad("Actividad", 5, 5);
        a1.setDuracionMinutos(60);
        a1.setPrecio(10.0);
        assertEquals(Viaje.ERROR_DIA_INVALIDO, planificador.agregarActividad(7, a1, "09:00"));
        assertEquals(Viaje.ERROR_DIA_INVALIDO, planificador.agregarActividad(10, a1, "09:00"));
    }

    @Test
    @DisplayName("Múltiples actividades en orden no cronológico se ordenan correctamente")
    void actividadesSeOrdenanCorrectamente() {
        Viaje planificador = new Viaje(5, 5);

        // Agregar en desorden
        Actividad a1 = new Actividad("Cena", 5, 5);
        a1.setDuracionMinutos(90);
        a1.setPrecio(20.0);
        planificador.agregarActividad(0, a1, "19:00");

        Actividad a2 = new Actividad("Desayuno", 5, 5);
        a2.setDuracionMinutos(60);
        a2.setPrecio(10.0);
        planificador.agregarActividad(0, a2, "08:00");

        Actividad a3 = new Actividad("Almuerzo", 5, 5);
        a3.setDuracionMinutos(60);
        a3.setPrecio(15.0);
        planificador.agregarActividad(0, a3, "13:00");

        // Verificar orden
        Actividad[] actividades = planificador.obtenerActividadesDia(0);
        assertEquals("Desayuno", actividades[0].getNombre());
        assertEquals("Almuerzo", actividades[1].getNombre());
        assertEquals("Cena", actividades[2].getNombre());
    }

    @Test
    @DisplayName("Eliminar actividad existente")
    void eliminarActividadExistente() {
        Viaje planificador = new Viaje(5, 5);

        Actividad a1 = new Actividad("Desayuno", 5, 5);
        a1.setDuracionMinutos(60);
        a1.setPrecio(10.0);
        planificador.agregarActividad(0, a1, "09:00");

        assertEquals(1, planificador.getNumActividadesDia(0));
        assertTrue(planificador.eliminarActividad(0, "09:00"));
        assertEquals(0, planificador.getNumActividadesDia(0));
    }

    @Test
    @DisplayName("Eliminar actividad inexistente")
    void eliminarActividadInexistente() {
        Viaje planificador = new Viaje(5, 5);

        Actividad a1 = new Actividad("Desayuno", 5, 5);
        a1.setDuracionMinutos(60);
        a1.setPrecio(10.0);
        planificador.agregarActividad(0, a1, "09:00");

        assertFalse(planificador.eliminarActividad(0, "10:00"));
        assertEquals(1, planificador.getNumActividadesDia(0));
    }

    @Test
    @DisplayName("Máximo de actividades por día")
    void maximoActividadesPorDia() {
        Viaje planificador = new Viaje(5, 3); // Máximo 3 actividades por día

        Actividad a1 = new Actividad("Act1", 5, 5);
        a1.setDuracionMinutos(60);
        a1.setPrecio(10.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a1, "09:00"));

        Actividad a2 = new Actividad("Act2", 5, 5);
        a2.setDuracionMinutos(60);
        a2.setPrecio(10.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a2, "10:00"));

        Actividad a3 = new Actividad("Act3", 5, 5);
        a3.setDuracionMinutos(60);
        a3.setPrecio(10.0);
        assertEquals(Viaje.EXITO, planificador.agregarActividad(0, a3, "11:00"));

        // Intenta agregar una cuarta
        Actividad a4 = new Actividad("Act4", 5, 5);
        a4.setDuracionMinutos(60);
        a4.setPrecio(10.0);
        assertEquals(Viaje.ERROR_DIA_COMPLETO, planificador.agregarActividad(0, a4, "12:00"),
                "Debería devolver ERROR_DIA_COMPLETO cuando el día está completo");

        assertEquals(3, planificador.getNumActividadesDia(0));
    }

    @Test
    @DisplayName("toString con formato vertical correcto")
    void toStringFormatoVertical() {
        Viaje planificador = new Viaje(3, 5);

        Actividad a1 = new Actividad("Museo", 5, 5);
        a1.setDuracionMinutos(120);
        a1.setPrecio(15.0);
        planificador.agregarActividad(0, a1, "09:30");

        Actividad a2 = new Actividad("Almuerzo", 5, 5);
        a2.setDuracionMinutos(60);
        a2.setPrecio(12.50);
        planificador.agregarActividad(0, a2, "12:30");

        String resultado = planificador.toString();

        // Verificar que contiene elementos clave del formato vertical
        assertTrue(resultado.contains("Día 1"));
        assertTrue(resultado.contains("Día 2"));
        assertTrue(resultado.contains("Día 3"));
        assertTrue(resultado.contains("09:30 Museo"));
        assertTrue(resultado.contains("12:30 Almuerzo"));
        assertTrue(resultado.contains("(No hay actividades)"));
        assertTrue(resultado.contains("Resumen:"));
        assertTrue(resultado.contains("- Días: 3"));
        assertTrue(resultado.contains("- Actividades: 2"));
        assertTrue(resultado.contains("- Precio: 27.50 €"));
    }

    @Test
    @DisplayName("Guardar archivo con formato compacto correcto")
    void guardarArchivoFormatoCompacto() throws IOException {
        Viaje planificador = new Viaje(3, 5);

        Actividad a1 = new Actividad("Museo", 5, 5);
        a1.setDuracionMinutos(90);
        a1.setPrecio(15.0);
        planificador.agregarActividad(0, a1, "09:30");

        Actividad a2 = new Actividad("Kayak", 5, 5);
        a2.setDuracionMinutos(120);
        a2.setPrecio(35.0);
        planificador.agregarActividad(1, a2, "12:00");

        Path ficheroTemporal = Files.createTempFile("viaje_test", ".txt");
        planificador.guardarItinerario(ficheroTemporal.toString());

        String contenido = Files.readString(ficheroTemporal);

        // Verificar formato compacto
        assertTrue(contenido.contains("Día 1: 09:30 Museo (dur 1h 30min, 15.00 €)"));
        assertTrue(contenido.contains("Día 2: 12:00 Kayak (dur 2h, 35.00 €)"));
        assertTrue(contenido.contains("Día 3: ---"));
        assertTrue(contenido.contains("Resumen: Días: 3; Actividades: 2; Precio total: 50.00 €"));

        Files.deleteIfExists(ficheroTemporal);
    }
}
