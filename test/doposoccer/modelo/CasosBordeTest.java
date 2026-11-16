package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Casos borde: el "otro lado" de las condiciones del modelo. */
public class CasosBordeTest {

    private Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);

    private Jugador crearJugador() {
        return new Jugador(Lado.IZQUIERDO, new Camerun(), new Perfil("X"), new ControlHumano(), cancha);
    }

    @Test
    public void unEfectoQueLoPermiteDejaSaltarPatearYActivarElPoder() {
        Jugador jugador = crearJugador();
        for (int i = 0; i < 4; i++) {
            jugador.recibirGol();
        }
        jugador.aplicarEfecto(new Confusion());
        jugador.saltar();
        jugador.patear();
        jugador.usarPoder();
        assertTrue(jugador.obtenerVelY() < 0);
        assertTrue(jugador.consumirPatadaPendiente());
        assertTrue(jugador.tienePoderActivo());
    }

    @Test
    public void conElAuraActivaNoSeActivaOtroPoderAunqueLaBarraSeLlene() {
        Jugador jugador = crearJugador();
        for (int i = 0; i < 4; i++) {
            jugador.recibirGol();
        }
        jugador.usarPoder();
        Poder primero = jugador.obtenerPoderActivo();
        for (int i = 0; i < 4; i++) {
            jugador.recibirGol();
        }
        jugador.usarPoder();
        assertSame(primero, jugador.obtenerPoderActivo());
    }

    @Test
    public void unJugadorInconscienteEnElAireNoFrena() {
        Jugador jugador = crearJugador();
        jugador.fijarVelocidad(200, 0);
        jugador.aplicarEfecto(new Inconsciente());
        jugador.actualizar(0.05);
        assertEquals(200.0, jugador.obtenerVelX(), 0.0001);
    }

    @Test
    public void golpearAUnJugadorYaAturdidoNoReiniciaElAturdimiento() {
        Jugador jugador = crearJugador();
        jugador.recibirDano(1000);
        jugador.actualizar(1.0);
        double restante = jugador.obtenerEfecto().obtenerRestante();
        jugador.recibirDano(10);
        assertEquals(restante, jugador.obtenerEfecto().obtenerRestante(), 0.0001);
    }

    @Test
    public void unAturdimientoNuevoReemplazaAlAnterior() {
        Jugador jugador = crearJugador();
        jugador.recibirDano(1000);
        jugador.actualizar(1.0);
        jugador.aplicarEfecto(new Aturdimiento());
        assertEquals(3.0, jugador.obtenerEfecto().obtenerRestante(), 0.0001);
    }

    @Test
    public void conTiempoRestanteNoHayMuerteSubita() throws DOPOSoccerException {
        Jugador a = crearJugador();
        Jugador b = new Jugador(Lado.DERECHO, new Nigeria(), new Perfil("Y"), new ControlHumano(), cancha);
        Marcador marcador = new Marcador(a, b);
        PorTiempo tipo = new PorTiempo(1);
        assertFalse(tipo.estaEnMuerteSubita(marcador));
        marcador.sumarGol(a);
        tipo.actualizar(61);
        assertFalse(tipo.estaEnMuerteSubita(marcador));
    }

    @Test
    public void noSePuedeConsultarElPrecioDeUnaEstadisticaEnElMaximo() throws DOPOSoccerException {
        Estadisticas estadisticas = new Estadisticas();
        for (int i = 0; i < 9; i++) {
            estadisticas.subirNivel(TipoEstadistica.JUMP);
        }
        assertTrue(estadisticas.estaEnMaximo(TipoEstadistica.JUMP));
        assertThrows(DOPOSoccerException.class, () -> estadisticas.obtenerPrecioMejora(TipoEstadistica.JUMP));
    }

    @Test
    public void quienLanzoElTiroDePoderPuedePatearloSinRecibirDano() throws DOPOSoccerException {
        Cancha c = new Cancha(ResolucionCancha.MEDIANA);
        Jugador a = new Jugador(Lado.IZQUIERDO, new Camerun(), new Perfil("A"), new ControlHumano(), c);
        Jugador b = new Jugador(Lado.DERECHO, new Nigeria(), new Perfil("B"), new ControlHumano(), c);
        Partida p = new Partida(c, a, b, new PorGoles(3));
        Balon balon = p.obtenerBalon();
        double[] zona = a.obtenerZonaPie();
        balon.fijarPosicion(zona[0] + 5, zona[1] + 5);
        balon.fijarVelocidad(0, 0);
        balon.iniciarTiroPoder(new Rayo(), a);
        a.patear();
        p.actualizar(0.016);
        assertEquals(a.obtenerVidaMaxima(), a.obtenerVida(), 0.0001);
        assertTrue(balon.tienePoderEnVuelo());
    }

    @Test
    public void unBalonQueSubeDesdeElSueloNoRebota() {
        Balon balon = new Balon(cancha);
        balon.fijarPosicion(400, cancha.obtenerSuelo() - balon.obtenerAlto());
        balon.fijarVelocidad(0, -500);
        balon.actualizar(0.001);
        assertTrue(balon.obtenerVelY() < 0);
    }

    @Test
    public void dosRectangulosSeparadosNoSeCruzan() {
        Jugador jugador = crearJugador();
        double x = jugador.obtenerX();
        double y = jugador.obtenerY();
        assertTrue(jugador.intersecta(x + 10, y + 10, 5, 5));
        assertFalse(jugador.intersecta(x - 100, y, 50, 50));
        assertFalse(jugador.intersecta(x + 500, y, 50, 50));
        assertFalse(jugador.intersecta(x, y - 200, 50, 50));
        assertFalse(jugador.intersecta(x, y + 500, 50, 50));
    }
}
