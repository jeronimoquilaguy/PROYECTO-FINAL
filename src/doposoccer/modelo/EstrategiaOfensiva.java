package doposoccer.modelo;

import java.util.HashSet;
import java.util.Set;

/** Persigue el balon e intenta marcar goles. */
public class EstrategiaOfensiva implements EstrategiaMaquina {
    public Set<Accion> decidir(Jugador jugador, Partida partida) {
        Set<Accion> acciones = new HashSet<Accion>();
        AyudaMaquina.atacarBalon(acciones, jugador, partida.obtenerBalon());
        AyudaMaquina.activarPoder(acciones, jugador);
        return acciones;
    }
}
