package doposoccer.modelo;

import java.util.HashSet;
import java.util.Set;

/** Control de un jugador humano: guarda las acciones que el teclado le va indicando. */
public class ControlHumano implements ControlJugador {

    private Set<Accion> mantenidas = new HashSet<Accion>();
    private Set<Accion> toques = new HashSet<Accion>();

    /**
     * Realiza la operación de obtener acciones.
     * @param jugador valor de jugador.
     * @param partida valor de partida.
     * @return resultado de obtener acciones.
     */
    public Set<Accion> obtenerAcciones(Jugador jugador, Partida partida) {
        Set<Accion> resultado = new HashSet<Accion>(mantenidas);
        resultado.addAll(toques);
        toques.clear();
        return resultado;
    }

    /**
     * Realiza la operación de presionar.
     * @param accion valor de accion.
     */
    public void presionar(Accion accion) {
        toques.add(accion);
        if (!accion.esInstantanea()) {
            mantenidas.add(accion);
        }
    }

    /**
     * Realiza la operación de soltar.
     * @param accion valor de accion.
     */
    public void soltar(Accion accion) {
        mantenidas.remove(accion);
    }
}
