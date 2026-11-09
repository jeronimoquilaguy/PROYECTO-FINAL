package doposoccer.modelo;

import java.util.HashMap;
import java.util.Map;

/** Goles de cada jugador en la partida. */
public class Marcador {

    private Map<Jugador, Integer> goles = new HashMap<Jugador, Integer>();
    private Jugador primero;
    private Jugador segundo;

    public Marcador(Jugador primero, Jugador segundo) {
        this.primero = primero;
        this.segundo = segundo;
        goles.put(primero, 0);
        goles.put(segundo, 0);
    }

    public void sumarGol(Jugador jugador) {
        goles.put(jugador, goles.get(jugador) + 1);
    }

    public int obtenerGoles(Jugador jugador) {
        return goles.get(jugador);
    }

    public int obtenerMayorNumeroDeGoles() {
        return Math.max(goles.get(primero), goles.get(segundo));
    }

    /** Jugador con mas goles, o null si van empatados. */
    public Jugador obtenerLider() {
        if (goles.get(primero) > goles.get(segundo)) {
            return primero;
        }
        if (goles.get(segundo) > goles.get(primero)) {
            return segundo;
        }
        return null;
    }
}
