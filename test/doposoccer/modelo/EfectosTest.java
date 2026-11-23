package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

/** Pruebas de los efectos que dejan los tiros de poder y del aturdimiento. */
public class EfectosTest {

    private Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);

    /** Conjunto con una sola accion, como si se oprimiera una tecla. */
    private Set<Accion> soloLaAccion(Accion accion) {
        Set<Accion> acciones = new HashSet<Accion>();
        acciones.add(accion);
        return acciones;
    }

    private Jugador crearJugador() {
        return new Jugador(Lado.IZQUIERDO, new Camerun(), new Perfil("X"), new ControlHumano(), cancha);
    }

    @Test
    public void empujeDuraUnSegundoYMedioYNoDejaHacerNadaExceptoElPoder() {
        Efecto empuje = new Empuje(1);
        assertEquals("Empujado", empuje.obtenerNombre());
        assertEquals(1.5, empuje.obtenerRestante(), 0.0001);
        assertFalse(empuje.permiteMover());
        assertFalse(empuje.permiteSaltar());
        assertFalse(empuje.permitePatear());
        assertTrue(empuje.permitePoder());
    }

    @Test
    public void empujeLanzaAlJugadorEnLaDireccionDelTiro() {
        Jugador jugador = crearJugador();
        jugador.aplicarEfecto(new Empuje(-1));
        assertEquals(-700.0, jugador.obtenerVelX(), 0.0001);
    }

    @Test
    public void unJugadorEmpujadoSeDeslizaHastaFrenar() {
        Jugador jugador = crearJugador();
        double xInicial = jugador.obtenerX();
        jugador.aplicarEfecto(new Empuje(1));
        jugador.actualizar(0.1);
        double velocidadAntes = jugador.obtenerVelX();
        jugador.actualizar(0.1);
        assertTrue(jugador.obtenerX() > xInicial);
        assertTrue(jugador.obtenerVelX() < velocidadAntes);
    }

    @Test
    public void electrocutadoDuraTresSegundosNoSaltaYVaALaMitad() {
        Efecto efecto = new Electrocutado();
        assertEquals(3.0, efecto.obtenerRestante(), 0.0001);
        assertTrue(efecto.permiteMover());
        assertFalse(efecto.permiteSaltar());
        assertTrue(efecto.permitePatear());
        assertEquals(0.5, efecto.obtenerFactorVelocidad(), 0.0001);
    }

    @Test
    public void unJugadorElectrocutadoCorreALaMitadDeVelocidad() {
        Jugador normal = crearJugador();
        Jugador electrocutado = crearJugador();
        electrocutado.aplicarEfecto(new Electrocutado());
        normal.ejecutarAcciones(soloLaAccion(Accion.DERECHA));
        electrocutado.ejecutarAcciones(soloLaAccion(Accion.DERECHA));
        normal.actualizar(0.1);
        electrocutado.actualizar(0.1);
        assertEquals(normal.obtenerVelX() / 2, electrocutado.obtenerVelX(), 0.0001);
    }

    @Test
    public void inconscienteNoDejaHacerNadaYLanzaAlAire() {
        Efecto efecto = new Inconsciente();
        assertEquals("Inconsciente", efecto.obtenerNombre());
        assertEquals(3.0, efecto.obtenerRestante(), 0.0001);
        assertFalse(efecto.permiteMover());
        assertFalse(efecto.permiteSaltar());
        assertFalse(efecto.permitePatear());
        assertFalse(efecto.permitePoder());

        Jugador jugador = crearJugador();
        jugador.aplicarEfecto(efecto);
        assertTrue(jugador.obtenerVelY() < 0);
        assertFalse(jugador.estaEnSuelo());
    }

    @Test
    public void confusionDuraDosSegundosYMedioYPermiteTodo() {
        Efecto efecto = new Confusion();
        assertEquals("Confundido", efecto.obtenerNombre());
        assertEquals(2.5, efecto.obtenerRestante(), 0.0001);
        assertTrue(efecto.esConfusion());
        assertFalse(efecto.esAturdimiento());
        assertTrue(efecto.permiteMover());
        assertTrue(efecto.permiteSaltar());
        assertTrue(efecto.permitePatear());
        assertTrue(efecto.permitePoder());
        assertEquals(1.0, efecto.obtenerFactorVelocidad(), 0.0001);
    }

    @Test
    public void aturdimientoDuraTresSegundosYNoDejaHacerNada() {
        Efecto efecto = new Aturdimiento();
        assertEquals("Aturdido", efecto.obtenerNombre());
        assertEquals(3.0, efecto.obtenerRestante(), 0.0001);
        assertTrue(efecto.esAturdimiento());
        assertFalse(efecto.esConfusion());
        assertFalse(efecto.permiteMover());
        assertFalse(efecto.permiteSaltar());
        assertFalse(efecto.permitePatear());
        assertFalse(efecto.permitePoder());
    }

    @Test
    public void elTiempoDeUnEfectoSeAcabaPocoAPoco() {
        Efecto efecto = new Confusion();
        efecto.actualizar(1.0);
        assertEquals(1.5, efecto.obtenerRestante(), 0.0001);
        assertFalse(efecto.haTerminado());
        efecto.actualizar(1.5);
        assertTrue(efecto.haTerminado());
    }

    @Test
    public void alTerminarUnEfectoElJugadorQuedaLibre() {
        Jugador jugador = crearJugador();
        jugador.aplicarEfecto(new Confusion());
        assertTrue(jugador.estaConfundido());
        for (int i = 0; i < 30; i++) {
            jugador.actualizar(0.1);
        }
        assertNull(jugador.obtenerEfecto());
        assertFalse(jugador.estaConfundido());
    }

    @Test
    public void unJugadorAturdidoNoRecibeOtrosEfectos() {
        Jugador jugador = crearJugador();
        jugador.recibirDano(1000);
        jugador.aplicarEfecto(new Confusion());
        assertTrue(jugador.estaAturdido());
        assertFalse(jugador.estaConfundido());
    }
}
