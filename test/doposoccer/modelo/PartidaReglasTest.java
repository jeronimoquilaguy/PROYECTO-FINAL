package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Mas pruebas de las reglas de la partida: autogol, poderes, muerte subita y estados. */
public class PartidaReglasTest {

    private Perfil perfil1 = new Perfil("Jugador 1");
    private Perfil perfil2 = new Perfil("Jugador 2");

    private Partida crearPartida(Personaje p1, Personaje p2, TipoPartido tipo) {
        Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);
        Jugador a = new Jugador(Lado.IZQUIERDO, p1, perfil1, new ControlHumano(), cancha);
        Jugador b = new Jugador(Lado.DERECHO, p2, perfil2, new ControlHumano(), cancha);
        return new Partida(cancha, a, b, tipo);
    }

    /** indiceArco 0 = arco izquierdo, 1 = arco derecho. */
    private void meterBalonEnArco(Partida partida, int indiceArco) {
        Cancha cancha = partida.obtenerCancha();
        double x = 5;
        if (indiceArco == 1) {
            x = cancha.obtenerAncho() - 35;
        }
        partida.obtenerBalon().fijarPosicion(x, cancha.obtenerSuelo() - 40);
        partida.obtenerBalon().fijarVelocidad(0, 0);
        partida.actualizar(0.016);
    }

    private void esperar(Partida partida, double segundos) {
        for (double t = 0; t < segundos; t += 0.016) {
            partida.actualizar(0.016);
        }
    }

    private void llenarBarra(Jugador jugador) {
        for (int i = 0; i < 4; i++) {
            jugador.recibirGol();
        }
    }

    @Test
    public void tieneDosArcosUnoPorLado() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        assertEquals(2, p.obtenerArcos().length);
        assertEquals(Lado.IZQUIERDO, p.obtenerArcos()[0].obtenerLado());
        assertEquals(Lado.DERECHO, p.obtenerArcos()[1].obtenerLado());
    }

    @Test
    public void autogolEsGolDelRival() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        Jugador[] js = p.obtenerJugadores();
        js[0].fijarPosicion(30, js[0].obtenerY());
        meterBalonEnArco(p, 0);
        assertEquals(1, p.obtenerMarcador().obtenerGoles(js[1]));
        assertSame(js[1], p.obtenerUltimoAnotador());
    }

    @Test
    public void recordamosQuienHizoElUltimoGol() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(5));
        assertNull(p.obtenerUltimoAnotador());
        meterBalonEnArco(p, 1);
        assertSame(p.obtenerJugadores()[0], p.obtenerUltimoAnotador());
    }

    @Test
    public void tocarElBalonConElAuraLanzaElTiroDePoder() throws DOPOSoccerException {
        Partida p = crearPartida(new Nigeria(), new Camerun(), new PorGoles(3));
        Jugador a = p.obtenerJugadores()[0];
        llenarBarra(a);
        a.usarPoder();
        Balon balon = p.obtenerBalon();
        balon.fijarPosicion(a.obtenerX() + 10, a.obtenerY() - balon.obtenerAlto() + 5);
        balon.fijarVelocidad(0, 50);
        p.actualizar(0.016);
        assertFalse(a.tienePoderActivo());
        assertTrue(balon.tienePoderEnVuelo());
        assertEquals("Tornado", balon.obtenerPoder().obtenerNombre());
    }

    @Test
    public void elTornadoDejaInconscienteAlRival() throws DOPOSoccerException {
        Partida p = crearPartida(new Nigeria(), new Camerun(), new PorGoles(3));
        Jugador a = p.obtenerJugadores()[0];
        Jugador b = p.obtenerJugadores()[1];
        b.recibirGolpePoder(new Tornado(), a);
        assertEquals(60.0, b.obtenerVida(), 0.0001);
        assertEquals("Inconsciente", b.obtenerEfecto().obtenerNombre());
        assertFalse(b.estaEnSuelo());
    }

    @Test
    public void elAuraAzulEmpujaAlRivalHaciaSuArco() throws DOPOSoccerException {
        Partida p = crearPartida(new CoreaDelSur(), new Camerun(), new PorGoles(3));
        Jugador a = p.obtenerJugadores()[0];
        Jugador b = p.obtenerJugadores()[1];
        b.recibirGolpePoder(new AuraAzul(), a);
        assertEquals("Empujado", b.obtenerEfecto().obtenerNombre());
        assertTrue(b.obtenerVelX() > 0);
    }

    @Test
    public void unTiroDePoderQueDejaLaVidaEnCeroAturde() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        Jugador a = p.obtenerJugadores()[0];
        Jugador b = p.obtenerJugadores()[1];
        b.recibirDano(50);
        b.recibirGolpePoder(new Rayo(), a);
        assertEquals(0.0, b.obtenerVida(), 0.0001);
        assertTrue(b.estaAturdido());
    }

    @Test
    public void unaPatadaQueNoAlcanzaANadieNoHaceDano() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        Jugador a = p.obtenerJugadores()[0];
        Jugador b = p.obtenerJugadores()[1];
        a.patear();
        p.actualizar(0.016);
        assertEquals(b.obtenerVidaMaxima(), b.obtenerVida(), 0.0001);
    }

    @Test
    public void porTiempoConEmpateSigueEnMuerteSubitaHastaElGol() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorTiempo(1));
        esperar(p, 61);
        assertEquals(EstadoPartida.EN_JUEGO, p.obtenerEstado());
        assertTrue(p.obtenerTipoPartido().estaEnMuerteSubita(p.obtenerMarcador()));
        meterBalonEnArco(p, 0);
        assertEquals(EstadoPartida.TERMINADO, p.obtenerEstado());
        assertSame(p.obtenerJugadores()[1], p.obtenerGanador());
        assertEquals(25, p.obtenerPuntosGanados(0));
        assertEquals(100, p.obtenerPuntosGanados(1));
    }

    @Test
    public void unaPartidaTerminadaYaNoSePuedePausarNiCambia() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        p.terminar();
        p.pausar();
        assertEquals(EstadoPartida.TERMINADO, p.obtenerEstado());
        p.reanudar();
        assertEquals(EstadoPartida.TERMINADO, p.obtenerEstado());
        p.terminar();
        assertEquals(EstadoPartida.TERMINADO, p.obtenerEstado());
    }

    @Test
    public void reanudarSinEstarPausadoNoCambiaNada() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        p.reanudar();
        assertEquals(EstadoPartida.EN_JUEGO, p.obtenerEstado());
    }

    @Test
    public void enUnaPartidaTerminadaElBalonNoSeMueve() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        p.terminar();
        double y = p.obtenerBalon().obtenerY();
        p.actualizar(0.5);
        assertEquals(y, p.obtenerBalon().obtenerY(), 0.0001);
    }
}
