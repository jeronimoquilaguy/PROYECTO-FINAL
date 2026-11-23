package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/** Pruebas del movimiento del jugador: teclas, bordes de la cancha, patada y poder. */
public class JugadorMovimientoTest {

    private Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);

    private Jugador crearJugador(Lado lado) {
        return new Jugador(lado, new Camerun(), new Perfil("X"), new ControlHumano(), cancha);
    }

    /** Conjunto con una sola accion, como si se oprimiera una tecla. */
    private Set<Accion> soloLaAccion(Accion accion) {
        Set<Accion> acciones = new HashSet<Accion>();
        acciones.add(accion);
        return acciones;
    }

    private void moverDurante(Jugador jugador, Accion accion, double segundos) {
        for (double t = 0; t < segundos; t += 0.05) {
            jugador.ejecutarAcciones(soloLaAccion(accion));
            jugador.actualizar(0.05);
        }
    }

    @Test
    public void conLaTeclaIzquierdaSeMueveALaIzquierda() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        double xInicial = jugador.obtenerX();
        moverDurante(jugador, Accion.IZQUIERDA, 0.2);
        assertTrue(jugador.obtenerX() < xInicial);
    }

    @Test
    public void conLaTeclaDerechaSeMueveALaDerecha() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        double xInicial = jugador.obtenerX();
        moverDurante(jugador, Accion.DERECHA, 0.2);
        assertTrue(jugador.obtenerX() > xInicial);
    }

    @Test
    public void conLasDosTeclasALaVezNoSeMueve() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        double xInicial = jugador.obtenerX();
        Set<Accion> acciones = new HashSet<Accion>();
        acciones.add(Accion.IZQUIERDA);
        acciones.add(Accion.DERECHA);
        jugador.ejecutarAcciones(acciones);
        jugador.actualizar(0.1);
        assertEquals(xInicial, jugador.obtenerX(), 0.0001);
    }

    @Test
    public void conLaTeclaDeSaltoSaltaYLuegoCaeAlSuelo() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        jugador.ejecutarAcciones(soloLaAccion(Accion.SALTAR));
        jugador.actualizar(0.05);
        assertFalse(jugador.estaEnSuelo());
        for (int i = 0; i < 40; i++) {
            jugador.actualizar(0.05);
        }
        assertTrue(jugador.estaEnSuelo());
    }

    @Test
    public void conLaTeclaDePatearLanzaElPie() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        jugador.ejecutarAcciones(soloLaAccion(Accion.PATEAR));
        assertTrue(jugador.estaPateando());
        assertTrue(jugador.consumirPatadaPendiente());
        assertFalse(jugador.consumirPatadaPendiente());
    }

    @Test
    public void noSePuedePatearOtraVezDeInmediato() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        jugador.patear();
        jugador.consumirPatadaPendiente();
        jugador.patear();
        assertFalse(jugador.consumirPatadaPendiente());
        jugador.actualizar(0.6);
        jugador.patear();
        assertTrue(jugador.consumirPatadaPendiente());
    }

    @Test
    public void laAnimacionDeLaPatadaTerminaSola() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        jugador.patear();
        jugador.actualizar(0.3);
        assertFalse(jugador.estaPateando());
    }

    @Test
    public void conLaTeclaDePoderSeActivaSiLaBarraEstaLlena() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        for (int i = 0; i < 4; i++) {
            jugador.recibirGol();
        }
        jugador.ejecutarAcciones(soloLaAccion(Accion.USAR_PODER));
        assertTrue(jugador.tienePoderActivo());
        assertEquals("Rayo", jugador.obtenerPoderActivo().obtenerNombre());
    }

    @Test
    public void unJugadorInconscienteNoPuedeActivarElPoder() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        for (int i = 0; i < 4; i++) {
            jugador.recibirGol();
        }
        jugador.aplicarEfecto(new Inconsciente());
        jugador.usarPoder();
        assertFalse(jugador.tienePoderActivo());
        assertEquals(1.0, jugador.obtenerBarraPoder(), 0.0001);
    }

    @Test
    public void noSaleDelBordeIzquierdoDeLaCancha() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        moverDurante(jugador, Accion.IZQUIERDA, 3);
        assertEquals(0.0, jugador.obtenerX(), 0.0001);
    }

    @Test
    public void noSaleDelBordeDerechoDeLaCancha() {
        Jugador jugador = crearJugador(Lado.DERECHO);
        moverDurante(jugador, Accion.DERECHA, 3);
        assertEquals(cancha.obtenerAncho() - jugador.obtenerAncho(), jugador.obtenerX(), 0.0001);
    }

    @Test
    public void noSaleDelTechoDeLaCancha() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        jugador.fijarPosicion(jugador.obtenerX(), 5);
        jugador.fijarVelocidad(0, -2000);
        jugador.actualizar(0.05);
        assertEquals(0.0, jugador.obtenerY(), 0.0001);
    }

    @Test
    public void elPieDelJugadorDerechoQuedaASuIzquierda() {
        Jugador derecho = crearJugador(Lado.DERECHO);
        double[] zona = derecho.obtenerZonaPie();
        assertTrue(zona[0] + zona[2] <= derecho.obtenerX());
    }

    @Test
    public void elPieDelJugadorIzquierdoQuedaASuDerecha() {
        Jugador izquierdo = crearJugador(Lado.IZQUIERDO);
        double[] zona = izquierdo.obtenerZonaPie();
        assertEquals(izquierdo.obtenerX() + izquierdo.obtenerAncho(), zona[0], 0.0001);
    }

    @Test
    public void despuesDeUnGolElJugadorCaeDesdeArriba() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        jugador.reiniciarPosicion(true);
        assertFalse(jugador.estaEnSuelo());
        assertTrue(jugador.obtenerY() + jugador.obtenerAlto() < cancha.obtenerSuelo());
    }

    @Test
    public void guardaSuPersonajeSuPerfilYSuControl() {
        Perfil perfil = new Perfil("Ana");
        ControlHumano control = new ControlHumano();
        Personaje personaje = new Nigeria();
        Jugador jugador = new Jugador(Lado.DERECHO, personaje, perfil, control, cancha);
        assertSame(personaje, jugador.obtenerPersonaje());
        assertSame(perfil, jugador.obtenerPerfil());
        assertSame(control, jugador.obtenerControl());
    }

    @Test
    public void conMasSpeedCorreMasRapido() throws DOPOSoccerException {
        Perfil rapido = new Perfil("Rapido");
        rapido.obtenerEstadisticas().subirNivel(TipoEstadistica.SPEED);
        Jugador normal = crearJugador(Lado.IZQUIERDO);
        Jugador veloz = new Jugador(Lado.IZQUIERDO, new Camerun(), rapido, new ControlHumano(), cancha);
        normal.ejecutarAcciones(soloLaAccion(Accion.DERECHA));
        veloz.ejecutarAcciones(soloLaAccion(Accion.DERECHA));
        normal.actualizar(0.1);
        veloz.actualizar(0.1);
        assertEquals(normal.obtenerVelX() * 1.1, veloz.obtenerVelX(), 0.0001);
    }

    @Test
    public void conMasJumpSaltaMasAlto() throws DOPOSoccerException {
        Perfil saltador = new Perfil("Saltador");
        for (int i = 0; i < 3; i++) {
            saltador.obtenerEstadisticas().subirNivel(TipoEstadistica.JUMP);
        }
        Jugador normal = crearJugador(Lado.IZQUIERDO);
        Jugador alto = new Jugador(Lado.IZQUIERDO, new Camerun(), saltador, new ControlHumano(), cancha);
        normal.saltar();
        alto.saltar();
        assertTrue(alto.obtenerVelY() < normal.obtenerVelY());
    }

    @Test
    public void laVidaNoBajaDeCero() {
        Jugador jugador = crearJugador(Lado.IZQUIERDO);
        jugador.recibirDano(30);
        assertEquals(70.0, jugador.obtenerVida(), 0.0001);
        jugador.recibirDano(500);
        assertEquals(0.0, jugador.obtenerVida(), 0.0001);
    }

    @Test
    public void elDanoDeLaPatadaUsaElMultiplicadorKick() throws DOPOSoccerException {
        Perfil fuerte = new Perfil("Fuerte");
        for (int i = 0; i < 9; i++) {
            fuerte.obtenerEstadisticas().subirNivel(TipoEstadistica.KICK);
        }
        Jugador jugador = new Jugador(Lado.IZQUIERDO, new Camerun(), fuerte, new ControlHumano(), cancha);
        assertEquals(19.0, jugador.obtenerDanoPatada(), 0.0001);
    }
}
