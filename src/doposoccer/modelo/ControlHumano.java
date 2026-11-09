package doposoccer.modelo;

import java.util.HashSet;
import java.util.Set;

/** Control de un jugador humano: guarda las acciones que el teclado le va indicando. */
public class ControlHumano implements ControlJugador {

    private Set<Accion> mantenidas = new HashSet<Accion>();
    private Set<Accion> toques = new HashSet<Accion>();

    public Set<Accion> obtenerAcciones(Jugador jugador, Partida partida) {
        Set<Accion> resultado = new HashSet<Accion>(mantenidas);
        resultado.addAll(toques);
        toques.clear();
        return resultado;
    }

    public void presionar(Accion accion) {
        toques.add(accion);
        if (!accion.esInstantanea()) {
            mantenidas.add(accion);
        }
    }

    public void soltar(Accion accion) {
        mantenidas.remove(accion);
    }
}
