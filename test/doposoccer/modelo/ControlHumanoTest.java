package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;

/** Pruebas del control de teclado: teclas sostenidas y teclas de un solo toque. */
public class ControlHumanoTest {

    @Test
    public void sinTeclasNoHayAcciones() {
        ControlHumano control = new ControlHumano();
        assertTrue(control.obtenerAcciones(null, null).isEmpty());
    }

    @Test
    public void unaTeclaSostenidaSeRepiteHastaQueSeSuelta() {
        ControlHumano control = new ControlHumano();
        control.presionar(Accion.DERECHA);
        assertTrue(control.obtenerAcciones(null, null).contains(Accion.DERECHA));
        assertTrue(control.obtenerAcciones(null, null).contains(Accion.DERECHA));
        control.soltar(Accion.DERECHA);
        assertFalse(control.obtenerAcciones(null, null).contains(Accion.DERECHA));
    }

    @Test
    public void elPoderSoloSeActivaUnaVezPorPulsacion() {
        ControlHumano control = new ControlHumano();
        control.presionar(Accion.USAR_PODER);
        assertTrue(control.obtenerAcciones(null, null).contains(Accion.USAR_PODER));
        assertFalse(control.obtenerAcciones(null, null).contains(Accion.USAR_PODER));
    }

    @Test
    public void unToqueRapidoNoSePierdeAunqueSeSuelteAntes() {
        ControlHumano control = new ControlHumano();
        control.presionar(Accion.PATEAR);
        control.soltar(Accion.PATEAR);
        Set<Accion> acciones = control.obtenerAcciones(null, null);
        assertTrue(acciones.contains(Accion.PATEAR));
    }

    @Test
    public void soloElPoderEsUnaAccionInstantanea() {
        assertTrue(Accion.USAR_PODER.esInstantanea());
        assertFalse(Accion.IZQUIERDA.esInstantanea());
        assertFalse(Accion.DERECHA.esInstantanea());
        assertFalse(Accion.SALTAR.esInstantanea());
        assertFalse(Accion.PATEAR.esInstantanea());
    }
}
