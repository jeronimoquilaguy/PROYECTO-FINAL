package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PartidaTest {

    private Perfil perfil1 = new Perfil("Jugador 1");
    private Perfil perfil2 = new Perfil("Jugador 2");

    private Partida crearPartida(Personaje p1, Personaje p2, TipoPartido tipo) {
        Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);
        Jugador a = new Jugador(Lado.IZQUIERDO, p1, perfil1, new ControlHumano(), cancha);
        Jugador b = new Jugador(Lado.DERECHO, p2, perfil2, new ControlHumano(), cancha);
        return new Partida(cancha, a, b, tipo);
    }

    private void meterBalonEnArco(Partida partida, int indiceArco) {
        Cancha cancha = partida.obtenerCancha();
        double y = cancha.obtenerSuelo() - 40;
        double x = 5;
        if (indiceArco == 1) {
            x = cancha.obtenerAncho() - 35;
        }
        partida.obtenerBalon().fijarPosicion(x, y);
        partida.obtenerBalon().fijarVelocidad(0, 0);
        partida.actualizar(0.016);
    }

    private void esperarCelebracion(Partida partida) {
        for (int i = 0; i < 100; i++) {
            partida.actualizar(0.016);
        }
    }

    @Test
    public void empiezaEnCeroACeroYEnJuego() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        Jugador[] js = p.obtenerJugadores();
        assertEquals(EstadoPartida.EN_JUEGO, p.obtenerEstado());
        assertEquals(0, p.obtenerMarcador().obtenerGoles(js[0]));
        assertEquals(0, p.obtenerMarcador().obtenerGoles(js[1]));
        assertEquals(js[0].obtenerVidaMaxima(), js[0].obtenerVida(), 0.0001);
        assertEquals(0.0, js[1].obtenerBarraPoder(), 0.0001);
    }

    @Test
    public void unGoleEnElArcoIzquierdoLoAnotaElJugadorDerecho() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        Jugador[] js = p.obtenerJugadores();
        meterBalonEnArco(p, 0);
        assertEquals(1, p.obtenerMarcador().obtenerGoles(js[1]));
        assertEquals(0, p.obtenerMarcador().obtenerGoles(js[0]));
        assertEquals(0.25, js[0].obtenerBarraPoder(), 0.01);
        assertTrue(p.hayCelebracionGol());
    }

    @Test
    public void unBalonQueEntraSoloAMediasNoEsGol() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        Jugador[] js = p.obtenerJugadores();
        double y = p.obtenerCancha().obtenerSuelo() - 40;
        p.obtenerBalon().fijarPosicion(60, y);
        p.obtenerBalon().fijarVelocidad(0, 0);
        p.actualizar(0.016);
        assertEquals(0, p.obtenerMarcador().obtenerGoles(js[1]));
        assertFalse(p.hayCelebracionGol());
    }

    @Test
    public void despuesDelGolElBalonVuelveAlCentro() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(3));
        meterBalonEnArco(p, 1);
        esperarCelebracion(p);
        double centro = p.obtenerCancha().obtenerAncho() / 2;
        assertEquals(centro, p.obtenerBalon().obtenerCentroX(), 1.0);
        assertFalse(p.hayCelebracionGol());
    }

    @Test
    public void porGolesTerminaYRepartePuntos() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(2));
        Jugador[] js = p.obtenerJugadores();
        meterBalonEnArco(p, 1);
        esperarCelebracion(p);
        meterBalonEnArco(p, 1);
        assertEquals(EstadoPartida.TERMINADO, p.obtenerEstado());
        assertSame(js[0], p.obtenerGanador());
        assertEquals(100, p.obtenerPuntosGanados(0));
        assertEquals(25, p.obtenerPuntosGanados(1));
        assertEquals(100, perfil1.obtenerPuntos());
        assertEquals(25, perfil2.obtenerPuntos());
    }

    @Test
    public void terminarAntesNoDaGanadorNiPuntos() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(2));
        p.terminar();
        assertEquals(EstadoPartida.TERMINADO, p.obtenerEstado());
        assertNull(p.obtenerGanador());
        assertEquals(0, perfil1.obtenerPuntos());
    }

    @Test
    public void enPausaNadaAvanza() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorTiempo(1));
        p.pausar();
        double tiempo = p.obtenerTipoPartido().obtenerTiempoRestante();
        double yBalon = p.obtenerBalon().obtenerY();
        p.actualizar(0.5);
        assertEquals(EstadoPartida.PAUSADO, p.obtenerEstado());
        assertEquals(tiempo, p.obtenerTipoPartido().obtenerTiempoRestante(), 0.0001);
        assertEquals(yBalon, p.obtenerBalon().obtenerY(), 0.0001);
        p.reanudar();
        p.actualizar(0.5);
        assertTrue(p.obtenerTipoPartido().obtenerTiempoRestante() < tiempo);
    }

    @Test
    public void elBalonCaeYRebotaEnElSuelo() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(2));
        Balon balon = p.obtenerBalon();
        double suelo = p.obtenerCancha().obtenerSuelo();
        for (int i = 0; i < 300; i++) {
            p.actualizar(0.016);
            assertTrue(balon.obtenerY() + balon.obtenerAlto() <= suelo + 0.001);
        }
    }

    @Test
    public void patadaNormalHaceDanoMultiplicadoPorKick() throws DOPOSoccerException {
        for (int i = 0; i < 4; i++) {
            perfil1.obtenerEstadisticas().subirNivel(TipoEstadistica.KICK);
        }
        perfil2.obtenerEstadisticas().subirNivel(TipoEstadistica.SURVIVAL);
        perfil2.obtenerEstadisticas().subirNivel(TipoEstadistica.SURVIVAL);
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(2));
        Jugador a = p.obtenerJugadores()[0];
        Jugador b = p.obtenerJugadores()[1];
        b.fijarPosicion(a.obtenerX() + a.obtenerAncho() + 5, a.obtenerY());
        assertEquals(120.0, b.obtenerVida(), 0.0001);
        a.patear();
        p.actualizar(0.016);
        assertEquals(106.0, b.obtenerVida(), 0.0001);
    }

    @Test
    public void patadaQueTocaElBalonSoloLoImpulsa() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(2));
        Jugador a = p.obtenerJugadores()[0];
        Jugador b = p.obtenerJugadores()[1];
        Balon balon = p.obtenerBalon();
        double[] zona = a.obtenerZonaPie();
        balon.fijarPosicion(zona[0] + 5, zona[1] + 5);
        balon.fijarVelocidad(0, 0);
        b.fijarPosicion(zona[0] + 10, a.obtenerY());
        a.patear();
        p.actualizar(0.016);
        assertTrue(balon.obtenerVelX() > 0);
        assertEquals(b.obtenerVidaMaxima(), b.obtenerVida(), 0.0001);
    }

    @Test
    public void ejemploDelEnunciadoTiroDePoderDeCamerun() throws DOPOSoccerException {
        for (int i = 0; i < 4; i++) {
            perfil1.obtenerEstadisticas().subirNivel(TipoEstadistica.KICK);
        }
        perfil2.obtenerEstadisticas().subirNivel(TipoEstadistica.SURVIVAL);
        perfil2.obtenerEstadisticas().subirNivel(TipoEstadistica.SURVIVAL);
        Partida p = crearPartida(new Camerun(), new Nigeria(), new PorGoles(2));
        Jugador a = p.obtenerJugadores()[0];
        Jugador b = p.obtenerJugadores()[1];
        b.recibirGolpePoder(new Rayo(), a);
        assertEquals(36.0, b.obtenerVida(), 0.0001);
        assertEquals("Electrocutado", b.obtenerEfecto().obtenerNombre());
    }

    @Test
    public void elTiroDePoderGolpeaAlRivalYAplicaElEfecto() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new CoreaDelSur(), new PorGoles(2));
        Jugador a = p.obtenerJugadores()[0];
        Jugador b = p.obtenerJugadores()[1];
        for (int i = 0; i < 12; i++) {
            a.actualizar(1.0);
        }
        a.usarPoder();
        assertTrue(a.tienePoderActivo());

        Balon balon = p.obtenerBalon();
        double[] zona = a.obtenerZonaPie();
        balon.fijarPosicion(zona[0] + 5, zona[1] + 5);
        balon.fijarVelocidad(0, 0);
        a.patear();
        p.actualizar(0.016);
        assertFalse(a.tienePoderActivo());
        assertTrue(balon.tienePoderEnVuelo());

        for (int i = 0; i < 200 && balon.tienePoderEnVuelo(); i++) {
            p.actualizar(0.016);
        }
        // Rayo de Camerun: 60 de dano con multiplicador 1.0, y el rival queda electrocutado
        assertEquals(40.0, b.obtenerVida(), 0.0001);
        assertEquals("Electrocutado", b.obtenerEfecto().obtenerNombre());
        assertFalse(balon.tienePoderEnVuelo());
    }

    @Test
    public void golConPoderNoHaceDano() throws DOPOSoccerException {
        Partida p = crearPartida(new Camerun(), new CoreaDelSur(), new PorGoles(5));
        Jugador a = p.obtenerJugadores()[0];
        Jugador b = p.obtenerJugadores()[1];
        b.fijarPosicion(b.obtenerX(), 0);
        Balon balon = p.obtenerBalon();
        balon.iniciarTiroPoder(new Rayo(), a);
        meterBalonEnArco(p, 1);
        assertEquals(1, p.obtenerMarcador().obtenerGoles(a));
        assertEquals(b.obtenerVidaMaxima(), b.obtenerVida(), 0.0001);
        assertNull(b.obtenerEfecto());
    }
}
