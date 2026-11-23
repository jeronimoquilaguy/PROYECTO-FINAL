package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Pruebas de la cancha, los arcos y las enumeraciones del juego. */
public class CanchaYEnumeracionesTest {

    @Test
    public void hayTresResolucionesDeCancha() {
        assertEquals(3, ResolucionCancha.values().length);
        assertEquals("Pequeña (800 x 450)", ResolucionCancha.PEQUENA.obtenerNombre());
        assertEquals("Mediana (1000 x 560)", ResolucionCancha.MEDIANA.obtenerNombre());
        assertEquals("Grande (1200 x 675)", ResolucionCancha.GRANDE.obtenerNombre());
        assertEquals(1200, ResolucionCancha.GRANDE.obtenerAncho());
        assertEquals(675, ResolucionCancha.GRANDE.obtenerAlto());
        assertEquals(ResolucionCancha.GRANDE, ResolucionCancha.valueOf("GRANDE"));
    }

    @Test
    public void laCanchaSeEscalaSegunLaResolucion() {
        assertEquals(0.8, new Cancha(ResolucionCancha.PEQUENA).obtenerEscala(), 0.0001);
        assertEquals(1.0, new Cancha(ResolucionCancha.MEDIANA).obtenerEscala(), 0.0001);
        assertEquals(1.2, new Cancha(ResolucionCancha.GRANDE).obtenerEscala(), 0.0001);
    }

    @Test
    public void laCanchaRecuerdaSuResolucionYSuSuelo() {
        Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);
        assertEquals(ResolucionCancha.MEDIANA, cancha.obtenerResolucion());
        assertEquals(520.0, cancha.obtenerSuelo(), 0.0001);
        assertEquals(1800.0, cancha.obtenerGravedad(), 0.0001);
    }

    @Test
    public void losArcosEstanEnLosExtremosYTienenTravesano() {
        Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);
        Arco izquierdo = new Arco(Lado.IZQUIERDO, cancha);
        Arco derecho = new Arco(Lado.DERECHO, cancha);
        assertEquals(0.0, izquierdo.obtenerX(), 0.0001);
        assertEquals(cancha.obtenerAncho() - derecho.obtenerAncho(), derecho.obtenerX(), 0.0001);
        assertEquals(160.0, izquierdo.obtenerAlto(), 0.0001);
        assertEquals(10.0, izquierdo.obtenerGrosor(), 0.0001);
        assertEquals(cancha.obtenerSuelo() - 160, izquierdo.obtenerYTravesano(), 0.0001);
    }

    @Test
    public void unBalonPorEncimaDelTravesanoNoEsGol() {
        Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);
        Arco arco = new Arco(Lado.IZQUIERDO, cancha);
        Balon balon = new Balon(cancha);
        balon.fijarPosicion(5, arco.obtenerYTravesano() - 50);
        assertFalse(arco.contiene(balon));
    }

    @Test
    public void cadaLadoMiraHaciaElArcoRival() {
        assertEquals(1, Lado.IZQUIERDO.obtenerDireccionFrente());
        assertEquals(-1, Lado.DERECHO.obtenerDireccionFrente());
        assertEquals(Lado.DERECHO, Lado.valueOf("DERECHO"));
    }

    @Test
    public void lasCuatroEstadisticasTienenNombre() {
        assertEquals(4, TipoEstadistica.values().length);
        assertEquals(TipoEstadistica.KICK, TipoEstadistica.valueOf("KICK"));
        for (TipoEstadistica tipo : TipoEstadistica.values()) {
            assertFalse(tipo.obtenerNombre().isEmpty());
        }
    }

    @Test
    public void laPartidaTieneTresEstados() {
        assertEquals(3, EstadoPartida.values().length);
        assertEquals(EstadoPartida.PAUSADO, EstadoPartida.valueOf("PAUSADO"));
    }

    @Test
    public void hayCincoAcciones() {
        assertEquals(5, Accion.values().length);
        assertEquals(Accion.SALTAR, Accion.valueOf("SALTAR"));
    }
}
