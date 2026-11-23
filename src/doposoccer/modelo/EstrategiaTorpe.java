package doposoccer.modelo;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/** La maquina torpe elige una accion al azar. */
public class EstrategiaTorpe implements EstrategiaMaquina {
    private Random azar = new Random();

    public Set<Accion> decidir(Jugador jugador, Partida partida) {
        Set<Accion> acciones = new HashSet<Accion>();
        acciones.add(Accion.values()[azar.nextInt(Accion.values().length)]);
        return acciones;
    }
}
