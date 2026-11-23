package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Pruebas de las fabricas de tipos de partido y de la modalidad Jugador vs Jugador. */
public class FabricasYModalidadesTest {

    @Test
    public void fabricaPorTiempoDescribeLoQuePide() {
        FabricaTipoPartido fabrica = new FabricaPorTiempo();
        assertEquals("Por tiempo", fabrica.obtenerNombre());
        assertEquals("Minutos", fabrica.obtenerEtiquetaValor());
        assertEquals(3, fabrica.obtenerValorPorDefecto());
    }

    @Test
    public void fabricaPorTiempoCreaUnPartidoConReloj() throws DOPOSoccerException {
        TipoPartido tipo = new FabricaPorTiempo().crear(2);
        assertTrue(tipo.usaTiempo());
        assertEquals(120.0, tipo.obtenerTiempoRestante(), 0.0001);
        assertEquals("Por tiempo (2 min)", tipo.obtenerDescripcion());
    }

    @Test
    public void fabricaPorGolesDescribeLoQuePide() {
        FabricaTipoPartido fabrica = new FabricaPorGoles();
        assertEquals("Por goles", fabrica.obtenerNombre());
        assertEquals("Goles para ganar", fabrica.obtenerEtiquetaValor());
        assertEquals(5, fabrica.obtenerValorPorDefecto());
    }

    @Test
    public void fabricaPorGolesCreaUnPartidoSinReloj() throws DOPOSoccerException {
        TipoPartido tipo = new FabricaPorGoles().crear(7);
        assertFalse(tipo.usaTiempo());
        assertEquals(0.0, tipo.obtenerTiempoRestante(), 0.0001);
        assertEquals("Primero a 7 goles", tipo.obtenerDescripcion());
    }

    @Test
    public void lasFabricasNoAceptanValoresInvalidos() {
        assertThrows(DOPOSoccerException.class, () -> new FabricaPorGoles().crear(0));
        assertThrows(DOPOSoccerException.class, () -> new FabricaPorTiempo().crear(-2));
    }

    @Test
    public void jugadorVsJugadorTieneNombre() {
        Modalidad modalidad = new JugadorVsJugador();
        assertEquals("Jugador vs Jugador", modalidad.obtenerNombre());
    }

    @Test
    public void jugadorVsJugadorDaUnControlDeTecladoDistintoACadaJugador() {
        Modalidad modalidad = new JugadorVsJugador();
        ControlJugador control1 = modalidad.crearControl(0);
        ControlJugador control2 = modalidad.crearControl(1);
        assertTrue(control1 instanceof ControlHumano);
        assertTrue(control2 instanceof ControlHumano);
        assertNotSame(control1, control2);
    }
}
