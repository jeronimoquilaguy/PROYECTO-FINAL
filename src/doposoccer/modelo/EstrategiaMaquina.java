package doposoccer.modelo;

import java.io.Serializable;
import java.util.Set;

/** Una estrategia decide las acciones sin cambiar el motor del juego. */
public interface EstrategiaMaquina extends Serializable {
    Set<Accion> decidir(Jugador jugador, Partida partida);
}
