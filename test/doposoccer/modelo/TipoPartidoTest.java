package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TipoPartidoTest {

    private Jugador crearJugador(Lado lado, Cancha cancha) {
        return new Jugador(lado, new CoreaDelSur(), new Perfil("X"), new ControlHumano(), cancha);
    }

    @Test
    public void porTiempoConEmpateEntraEnMuerteSubita() throws DOPOSoccerException {
        Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);
        Jugador a = crearJugador(Lado.IZQUIERDO, cancha);
        Jugador b = crearJugador(Lado.DERECHO, cancha);
        Marcador marcador = new Marcador(a, b);
        PorTiempo tipo = new PorTiempo(1);

        tipo.actualizar(61);
        assertEquals(0, tipo.obtenerTiempoRestante(), 0.0001);
        assertFalse(tipo.haTerminado(marcador));
        assertTrue(tipo.estaEnMuerteSubita(marcador));

        marcador.sumarGol(b);
        assertTrue(tipo.haTerminado(marcador));
        assertSame(b, tipo.obtenerGanador(marcador));
    }

    @Test
    public void porTiempoGanaQuienTengaMasGolesAlAcabarse() throws DOPOSoccerException {
        Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);
        Jugador a = crearJugador(Lado.IZQUIERDO, cancha);
        Jugador b = crearJugador(Lado.DERECHO, cancha);
        Marcador marcador = new Marcador(a, b);
        PorTiempo tipo = new PorTiempo(1);
        marcador.sumarGol(a);
        tipo.actualizar(30);
        assertFalse(tipo.haTerminado(marcador));
        tipo.actualizar(31);
        assertTrue(tipo.haTerminado(marcador));
        assertSame(a, tipo.obtenerGanador(marcador));
    }

    @Test
    public void porGolesTerminaAlLlegarALaMeta() throws DOPOSoccerException {
        Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);
        Jugador a = crearJugador(Lado.IZQUIERDO, cancha);
        Jugador b = crearJugador(Lado.DERECHO, cancha);
        Marcador marcador = new Marcador(a, b);
        PorGoles tipo = new PorGoles(2);
        marcador.sumarGol(a);
        assertFalse(tipo.haTerminado(marcador));
        marcador.sumarGol(a);
        assertTrue(tipo.haTerminado(marcador));
        assertSame(a, tipo.obtenerGanador(marcador));
    }

    @Test
    public void valoresInvalidosLanzanExcepcion() {
        assertThrows(DOPOSoccerException.class, () -> new PorTiempo(0));
        assertThrows(DOPOSoccerException.class, () -> new PorGoles(-1));
        assertThrows(DOPOSoccerException.class, () -> new FabricaPorTiempo().crear(0));
    }
}
