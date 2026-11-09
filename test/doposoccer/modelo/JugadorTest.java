package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class JugadorTest {

    private Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);

    private Jugador crear(Personaje personaje) {
        return new Jugador(Lado.IZQUIERDO, personaje, new Perfil("X"), new ControlHumano(), cancha);
    }

    private void pasarTiempo(Jugador j, double segundos) {
        double dt = 0.05;
        for (double t = 0; t < segundos; t += dt) {
            j.actualizar(dt);
        }
    }

    @Test
    public void empiezaConVidaMaximaYBarraVacia() {
        Jugador j = crear(new Camerun());
        assertEquals(100.0, j.obtenerVida(), 0.0001);
        assertEquals(0.0, j.obtenerBarraPoder(), 0.0001);
        assertFalse(j.tienePoderActivo());
    }

    @Test
    public void laBarraSeLlenaEnDiezSegundos() {
        Jugador j = crear(new Camerun());
        pasarTiempo(j, 5);
        assertEquals(0.5, j.obtenerBarraPoder(), 0.05);
        pasarTiempo(j, 5.5);
        assertEquals(1.0, j.obtenerBarraPoder(), 0.0001);
    }

    @Test
    public void recibirUnGolSumaUnCuartoALaBarra() {
        Jugador j = crear(new Camerun());
        j.recibirGol();
        assertEquals(0.25, j.obtenerBarraPoder(), 0.0001);
        j.recibirGol();
        j.recibirGol();
        j.recibirGol();
        j.recibirGol();
        assertEquals(1.0, j.obtenerBarraPoder(), 0.0001);
    }

    @Test
    public void usarPoderVaciaLaBarraYDaElAura() {
        Jugador j = crear(new Camerun());
        pasarTiempo(j, 10.5);
        j.usarPoder();
        assertTrue(j.tienePoderActivo());
        assertEquals(0.0, j.obtenerBarraPoder(), 0.0001);
    }

    @Test
    public void conPoderActivoNoSePuedeActivarOtro() {
        Jugador j = crear(new Camerun());
        pasarTiempo(j, 10.5);
        j.usarPoder();
        Poder primero = j.obtenerPoderActivo();
        pasarTiempo(j, 10.5);
        j.usarPoder();
        assertSame(primero, j.obtenerPoderActivo());
        assertEquals(1.0, j.obtenerBarraPoder(), 0.0001);
    }

    @Test
    public void sinBarraLlenaNoSeActivaElPoder() {
        Jugador j = crear(new Camerun());
        j.usarPoder();
        assertFalse(j.tienePoderActivo());
    }

    @Test
    public void vidaEnCeroAturdeYLuegoRestauraLaVida() {
        Jugador j = crear(new Camerun());
        j.recibirDano(1000);
        assertEquals(0.0, j.obtenerVida(), 0.0001);
        assertTrue(j.estaAturdido());
        pasarTiempo(j, 3.2);
        assertFalse(j.estaAturdido());
        assertEquals(100.0, j.obtenerVida(), 0.0001);
    }

    @Test
    public void elAturdidoNoSePuedeMoverNiSaltar() {
        Jugador j = crear(new Camerun());
        j.recibirDano(1000);
        double xAntes = j.obtenerX();
        j.saltar();
        j.patear();
        assertTrue(j.estaEnSuelo());
        assertFalse(j.consumirPatadaPendiente());
        pasarTiempo(j, 1);
        assertEquals(xAntes, j.obtenerX(), 0.0001);
    }

    @Test
    public void unNuevoEfectoReemplazaAlAnterior() {
        Jugador j = crear(new Camerun());
        j.aplicarEfecto(new Electrocutado());
        j.aplicarEfecto(new Confusion());
        assertTrue(j.estaConfundido());
        assertEquals("Confundido", j.obtenerEfecto().obtenerNombre());
    }

    @Test
    public void saltarSoloSePuedeDesdeElSuelo() {
        Jugador j = crear(new Camerun());
        j.saltar();
        j.actualizar(0.05);
        assertFalse(j.estaEnSuelo());
        double velocidadSubiendo = j.obtenerVelY();
        j.saltar();
        assertEquals(velocidadSubiendo, j.obtenerVelY(), 0.0001);
    }
}
