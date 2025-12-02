package es.upm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

@DisplayName("Tests para la clase CatalogoActividades")
public class CatalogoActividadesTest {
    @BeforeAll
    static void fixLocale() {
        Locale.setDefault(Locale.US);
    }

    @Test
    @DisplayName("Agregar actividad a catálogo vacío")
    void agregarActividadACatalogoVacio() {
        CatalogoActividades catalogo = new CatalogoActividades(3);
        Actividad actividad = new Actividad("Museo", 3, 3);
        actividad.setDescripcion("Visita al museo");
        actividad.setPrecio(10.0);
        actividad.setDuracionMinutos(60);
        assertEquals(CatalogoActividades.EXITO, catalogo.agregarActividad(actividad),
                "Debería devolver ADD_OK al agregar actividad a catálogo vacío");
        assertEquals(1, catalogo.getNumActividades());
    }

    @Test
    @DisplayName("Agregar actividad a catálogo con actividades")
    void agregarActividadACatalogoConActividades() {
        CatalogoActividades catalogo = new CatalogoActividades(3);
        Actividad a1 = new Actividad("Museo", 3, 3);
        a1.setDescripcion("Visita al museo");
        a1.setPrecio(10.0);
        a1.setDuracionMinutos(60);
        Actividad a2 = new Actividad("Parque", 3, 3);
        a2.setDescripcion("Paseo por el parque");
        a2.setPrecio(0.0);
        a2.setDuracionMinutos(45);
        catalogo.agregarActividad(a1);
        assertEquals(CatalogoActividades.EXITO, catalogo.agregarActividad(a2),
                "Debería devolver ADD_OK al agregar actividad a catálogo no completo");
        assertEquals(2, catalogo.getNumActividades());
    }

    @Test
    @DisplayName("Agregar actividad a catálogo completo")
    void agregarActividadACatalogoCompleto() {
        CatalogoActividades catalogo = new CatalogoActividades(1);
        Actividad a1 = new Actividad("Museo", 3, 3);
        a1.setDescripcion("Visita al museo");
        a1.setPrecio(10.0);
        a1.setDuracionMinutos(60);
        Actividad a2 = new Actividad("Parque", 3, 3);
        a2.setDescripcion("Paseo por el parque");
        a2.setPrecio(0.0);
        a2.setDuracionMinutos(45);
        catalogo.agregarActividad(a1);
        assertEquals(CatalogoActividades.ERROR_DEMASIADOS, catalogo.agregarActividad(a2),
                "Debería devolver ADD_DEMASIADOS al intentar agregar actividad a catálogo completo");
        assertEquals(1, catalogo.getNumActividades());
    }

    @Test
    @DisplayName("Agregar actividad nula")
    void agregarActividadNula() {
        CatalogoActividades catalogo = new CatalogoActividades(3);
        assertEquals(CatalogoActividades.ERROR_ACTIVIDAD_NULL, catalogo.agregarActividad(null),
                "Debería devolver ADD_ACTIVIDAD_NULL al intentar agregar null");
        assertEquals(0, catalogo.getNumActividades());
    }

    @ParameterizedTest
    @DisplayName("Buscar actividad por nombre (existente, una)")
    @ValueSource(strings = {"Museo", "museo", "MUSEO", "use", "seo"})
    void buscarActividadPorNombreExistenteUna(String texto) {
        CatalogoActividades catalogo = new CatalogoActividades(3);
        Actividad a1 = new Actividad("Museo", 3, 3);
        a1.setDescripcion("Visita al museo");
        a1.setPrecio(10.0);
        a1.setDuracionMinutos(60);
        Actividad a2 = new Actividad("Parque", 3, 3);
        a2.setDescripcion("Paseo por el parque");
        a2.setPrecio(0.0);
        a2.setDuracionMinutos(45);
        catalogo.agregarActividad(a1);
        catalogo.agregarActividad(a2);
        Actividad[] resultados = catalogo.buscarActividadPorNombre(texto);
        assertEquals("Museo", resultados[0].getNombre());
    }

    @ParameterizedTest
    @DisplayName("Buscar actividad por nombre (existente, varias)")
    @ValueSource(strings = {"Museo", "museo", "MUSEO", "use", "seo"})
    void buscarActividadPorNombreExistenteVarias(String texto) {
        CatalogoActividades catalogo = new CatalogoActividades(3);
        Actividad a1 = new Actividad("Museo", 3, 3);
        a1.setDescripcion("Visita al museo");
        a1.setPrecio(10.0);
        a1.setDuracionMinutos(60);
        Actividad a2 = new Actividad("Museo de arte", 3, 3);
        a2.setDescripcion("Arte moderno");
        a2.setPrecio(15.0);
        a2.setDuracionMinutos(90);
        Actividad a3 = new Actividad("Parque", 3, 3);
        a3.setDescripcion("Paseo por el parque");
        a3.setPrecio(0.0);
        a3.setDuracionMinutos(45);
        catalogo.agregarActividad(a1);
        catalogo.agregarActividad(a2);
        catalogo.agregarActividad(a3);
        Actividad[] resultados = catalogo.buscarActividadPorNombre(texto);
        assertEquals("Museo", resultados[0].getNombre());
        assertEquals("Museo de arte", resultados[1].getNombre());
    }

    @ParameterizedTest
    @DisplayName("Buscar actividad por nombre (no existente)")
    @ValueSource(strings = {"Cine", "cine", "CINE", "película", "film"})
    void buscarActividadPorNombreNoExistente(String texto) {
        CatalogoActividades catalogo = new CatalogoActividades(3);
        Actividad a1 = new Actividad("Museo", 3, 3);
        a1.setDescripcion("Visita al museo");
        a1.setPrecio(10.0);
        a1.setDuracionMinutos(60);
        Actividad a2 = new Actividad("Parque", 3, 3);
        a2.setDescripcion("Paseo por el parque");
        a2.setPrecio(0.0);
        a2.setDuracionMinutos(45);
        catalogo.agregarActividad(a1);
        catalogo.agregarActividad(a2);
        Actividad[] resultados = catalogo.buscarActividadPorNombre(texto);
        assertEquals(0, resultados.length);
    }

    @Test
    @DisplayName("Buscar actividad por nombre vacío devuelve todas las actividades")
    void buscarActividadPorNombreVacio() {
        CatalogoActividades catalogo = new CatalogoActividades(3);
        Actividad a1 = new Actividad("Museo", 3, 3);
        a1.setDescripcion("Visita al museo");
        a1.setPrecio(10.0);
        a1.setDuracionMinutos(60);
        Actividad a2 = new Actividad("Parque", 3, 3);
        a2.setDescripcion("Paseo por el parque");
        a2.setPrecio(0.0);
        a2.setDuracionMinutos(45);
        Actividad a3 = new Actividad("Museo de arte", 3, 3);
        a3.setDescripcion("Arte moderno");
        a3.setPrecio(15.0);
        a3.setDuracionMinutos(90);
        catalogo.agregarActividad(a1);
        catalogo.agregarActividad(a2);
        catalogo.agregarActividad(a3);
        Actividad[] resultados = catalogo.buscarActividadPorNombre("");
        assertEquals(3, resultados.length);
    }

    @Test
    @DisplayName("Eliminar actividad existente")
    void eliminarActividadExistente() {
        CatalogoActividades catalogo = new CatalogoActividades(3);
        Actividad actividad = new Actividad("Museo", 3, 3);
        actividad.setDescripcion("Visita al museo");
        actividad.setPrecio(10.0);
        actividad.setDuracionMinutos(60);
        catalogo.agregarActividad(actividad);
        catalogo.eliminarActividad(actividad);
        assertEquals(0, catalogo.getNumActividades());
    }

    @Test
    @DisplayName("Eliminar actividad no existente")
    void eliminarActividadNoExistente() {
        CatalogoActividades catalogo = new CatalogoActividades(3);
        Actividad a1 = new Actividad("Museo", 3, 3);
        a1.setDescripcion("Visita al museo");
        a1.setPrecio(10.0);
        a1.setDuracionMinutos(60);
        Actividad a2 = new Actividad("Parque", 3, 3);
        a2.setDescripcion("Paseo por el parque");
        a2.setPrecio(0.0);
        a2.setDuracionMinutos(45);
        catalogo.agregarActividad(a1);
        catalogo.eliminarActividad(a2);
        assertEquals(1, catalogo.getNumActividades());
    }

    @Test
    @DisplayName("Eliminar actividad devuelve true cuando existe y false cuando no existe")
    void eliminarActividadDevuelveTrueOFalse() {
        CatalogoActividades catalogo = new CatalogoActividades(3);
        Actividad a1 = new Actividad("Museo", 3, 3);
        a1.setDescripcion("Visita al museo");
        a1.setPrecio(10.0);
        a1.setDuracionMinutos(60);
        Actividad a2 = new Actividad("Parque", 3, 3);
        a2.setDescripcion("Paseo por el parque");
        a2.setPrecio(0.0);
        a2.setDuracionMinutos(45);

        // Añadir solo a1 al catálogo
        catalogo.agregarActividad(a1);

        // Eliminar a1 (que existe) debe devolver true
        assertTrue(catalogo.eliminarActividad(a1), "Eliminar actividad existente debe devolver true");
        assertEquals(0, catalogo.getNumActividades(), "Después de eliminar, el catálogo debe estar vacío");

        // Intentar eliminar a2 (que no existe) debe devolver false
        assertFalse(catalogo.eliminarActividad(a2), "Eliminar actividad no existente debe devolver false");
        assertEquals(0, catalogo.getNumActividades(), "El número de actividades no debe cambiar");

        // Intentar eliminar a1 de nuevo (ya eliminada) debe devolver false
        assertFalse(catalogo.eliminarActividad(a1), "Eliminar actividad ya eliminada debe devolver false");
    }

    @Test
    @DisplayName("Agregar, llenar, eliminar y verificar ocupación correcta del catálogo")
    void agregarEliminarVerificarOcupacionCorrecta() {
        // Crear catálogo con capacidad para exactamente 2 actividades
        CatalogoActividades catalogo = new CatalogoActividades(2);

        Actividad a1 = new Actividad("Museo", 3, 3);
        a1.setDescripcion("Visita al museo");
        a1.setPrecio(10.0);
        a1.setDuracionMinutos(60);

        Actividad a2 = new Actividad("Parque", 3, 3);
        a2.setDescripcion("Paseo por el parque");
        a2.setPrecio(0.0);
        a2.setDuracionMinutos(45);

        // Estado inicial: catálogo vacío
        assertEquals(0, catalogo.getNumActividades(), "El catálogo debe estar vacío inicialmente");
        assertFalse(catalogo.actividadesCompletas(), "El catálogo no debe estar completo");

        // Agregar primera actividad
        assertEquals(CatalogoActividades.EXITO, catalogo.agregarActividad(a1), "Debe poder agregar la primera actividad");
        assertEquals(1, catalogo.getNumActividades(), "Debe haber 1 actividad");
        assertFalse(catalogo.actividadesCompletas(), "El catálogo no debe estar completo con 1 actividad");

        // Agregar segunda actividad (ahora está lleno)
        assertEquals(CatalogoActividades.EXITO, catalogo.agregarActividad(a2), "Debe poder agregar la segunda actividad");
        assertEquals(2, catalogo.getNumActividades(), "Debe haber 2 actividades");
        assertTrue(catalogo.actividadesCompletas(), "El catálogo debe estar completo con 2 actividades");

        // Eliminar primera actividad
        assertTrue(catalogo.eliminarActividad(a1), "Debe poder eliminar la primera actividad");
        assertEquals(1, catalogo.getNumActividades(), "Debe quedar 1 actividad tras eliminar");
        assertFalse(catalogo.actividadesCompletas(), "El catálogo no debe estar completo después de eliminar");

        // Verificar que la actividad restante es la correcta (a2)
        Actividad[] resultados = catalogo.buscarActividadPorNombre("Parque");
        assertEquals(1, resultados.length, "Debe encontrar exactamente 1 actividad 'Parque'");
        assertEquals("Parque", resultados[0].getNombre());

        // Eliminar segunda actividad
        assertTrue(catalogo.eliminarActividad(a2), "Debe poder eliminar la segunda actividad");
        assertEquals(0, catalogo.getNumActividades(), "El catálogo debe estar vacío");
        assertFalse(catalogo.actividadesCompletas(), "El catálogo vacío no debe estar completo");

        // Verificar que se puede volver a agregar actividades después de vaciar
        assertEquals(CatalogoActividades.EXITO, catalogo.agregarActividad(a1), "Debe poder agregar actividades después de vaciar");
        assertEquals(1, catalogo.getNumActividades(), "Debe haber 1 actividad nuevamente");
        assertEquals(CatalogoActividades.EXITO, catalogo.agregarActividad(a2), "Debe poder agregar la segunda actividad nuevamente");
        assertEquals(2, catalogo.getNumActividades(), "Debe haber 2 actividades nuevamente");
        assertTrue(catalogo.actividadesCompletas(), "El catálogo debe estar completo nuevamente");
    }

    @Test
    @DisplayName("Guardar y cargar actividades desde archivo")
    void guardarYCargarActividadesDesdeArchivo() {
        try {
            Path archivo = Files.createTempFile("actividades", ".txt");

            // Crear catálogo y guardar
            CatalogoActividades catalogo = new CatalogoActividades(3);
            Actividad a1 = new Actividad("Museo", 2, 2);
            a1.setDescripcion("Visita al museo");
            a1.setPrecio(10.0);
            a1.setDuracionMinutos(60);
            a1.agregarRecurso("Entrada");
            a1.agregarComentario("Muy interesante");
            Actividad a2 = new Actividad("Parque", 2, 2);
            a2.setDescripcion("Paseo por el parque");
            a2.setPrecio(0.0);
            a2.setDuracionMinutos(45);
            a2.agregarRecurso("Mapa");
            a2.agregarComentario("Ideal para relajarse");
            catalogo.agregarActividad(a1);
            catalogo.agregarActividad(a2);
            catalogo.guardarActividades(archivo.toString());

            // Crear nuevo catálogo y cargar
            CatalogoActividades nuevoCatalogo = new CatalogoActividades(3);
            nuevoCatalogo.cargarActividades(archivo.toString(), 2, 2);

            assertEquals(2, nuevoCatalogo.getNumActividades());
            Actividad[] resultados = nuevoCatalogo.buscarActividadPorNombre("Museo");
            assertEquals("Museo", resultados[0].getNombre());
            assertEquals("Entrada", resultados[0].getRecursos()[0]);
            assertEquals("Muy interesante", resultados[0].getComentarios()[0]);

        } catch (IOException e) {
            fail("No debería lanzar excepción al guardar/cargar actividades.");
        }
    }
}
