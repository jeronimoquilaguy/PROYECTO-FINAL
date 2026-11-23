package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Pruebas del balon: patada, rebotes en los bordes y tiro de poder. */
public class BalonTest {

    private Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);

    private Jugador crearJugador() {
        return new Jugador(Lado.IZQUIERDO, new Camerun(), new Perfil("X"), new ControlHumano(), cancha);
    }

    @Test
    public void empiezaQuietoEnElCentroDeLaCancha() {
        Balon balon = new Balon(cancha);
        assertEquals(cancha.obtenerAncho() / 2.0, balon.obtenerCentroX(), 0.0001);
        assertEquals(0.0, balon.obtenerVelX(), 0.0001);
        assertEquals(0.0, balon.obtenerVelY(), 0.0001);
        assertFalse(balon.tienePoderEnVuelo());
    }

    @Test
    public void suTamanoDependeDeLaResolucion() {
        assertEquals(15.0, new Balon(cancha).obtenerRadio(), 0.0001);
        assertEquals(12.0, new Balon(new Cancha(ResolucionCancha.PEQUENA)).obtenerRadio(), 0.0001);
    }

    @Test
    public void unaPatadaLoLanzaHaciaArribaYHaciaElFrente() {
        Balon balon = new Balon(cancha);
        balon.recibirPatada(-1);
        assertTrue(balon.obtenerVelX() < 0);
        assertTrue(balon.obtenerVelY() < 0);
    }

    @Test
    public void rebotaEnElBordeIzquierdo() {
        Balon balon = new Balon(cancha);
        balon.fijarPosicion(2, 200);
        balon.fijarVelocidad(-500, 0);
        balon.actualizar(0.016);
        assertEquals(0.0, balon.obtenerX(), 0.0001);
        assertTrue(balon.obtenerVelX() > 0);
    }

    @Test
    public void rebotaEnElBordeDerecho() {
        Balon balon = new Balon(cancha);
        balon.fijarPosicion(cancha.obtenerAncho() - balon.obtenerAncho() - 2, 200);
        balon.fijarVelocidad(500, 0);
        balon.actualizar(0.016);
        assertEquals(cancha.obtenerAncho() - balon.obtenerAncho(), balon.obtenerX(), 0.0001);
        assertTrue(balon.obtenerVelX() < 0);
    }

    @Test
    public void rebotaEnElTecho() {
        Balon balon = new Balon(cancha);
        balon.fijarPosicion(400, 2);
        balon.fijarVelocidad(0, -800);
        balon.actualizar(0.016);
        assertEquals(0.0, balon.obtenerY(), 0.0001);
        assertTrue(balon.obtenerVelY() > 0);
    }

    @Test
    public void elTiroDePoderGuardaElPoderYQuienLoLanzo() {
        Balon balon = new Balon(cancha);
        Jugador dueno = crearJugador();
        Poder rayo = new Rayo();
        balon.iniciarTiroPoder(rayo, dueno);
        assertTrue(balon.tienePoderEnVuelo());
        assertSame(rayo, balon.obtenerPoder());
        assertSame(dueno, balon.obtenerDueno());
    }

    @Test
    public void elTiroDePoderSeAcabaAlChocarConUnBorde() {
        Balon balon = new Balon(cancha);
        balon.iniciarTiroPoder(new Rayo(), crearJugador());
        balon.fijarPosicion(2, 200);
        balon.fijarVelocidad(-500, 0);
        balon.actualizar(0.016);
        assertFalse(balon.tienePoderEnVuelo());
        assertNull(balon.obtenerDueno());
    }

    @Test
    public void elTiroDePoderSeAcabaSoloDespuesDeCuatroSegundos() {
        Balon balon = new Balon(cancha);
        balon.iniciarTiroPoder(new Ilusion(), crearJugador());
        balon.configurarTiro(0, 0, 0, 0.8);
        for (int i = 0; i < 20; i++) {
            balon.actualizar(0.1);
        }
        assertTrue(balon.tienePoderEnVuelo());
        for (int i = 0; i < 25; i++) {
            balon.actualizar(0.1);
        }
        assertFalse(balon.tienePoderEnVuelo());
    }

    @Test
    public void sinGravedadElTiroVaRecto() {
        Balon balon = new Balon(cancha);
        double y = balon.obtenerY();
        balon.configurarTiro(300, 0, 0, 0.8);
        balon.actualizar(0.1);
        assertEquals(y, balon.obtenerY(), 0.0001);
        assertEquals(300.0, balon.obtenerVelX(), 0.0001);
    }

    @Test
    public void alVolverAlCentroPierdeElPoder() {
        Balon balon = new Balon(cancha);
        balon.iniciarTiroPoder(new Rayo(), crearJugador());
        balon.fijarVelocidad(100, 100);
        balon.reiniciarEnCentro();
        assertFalse(balon.tienePoderEnVuelo());
        assertEquals(0.0, balon.obtenerVelX(), 0.0001);
    }
}
