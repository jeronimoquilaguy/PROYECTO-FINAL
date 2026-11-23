package doposoccer.modelo;

import java.util.HashSet;
import java.util.Set;

/** Cuida su arco y persigue el balon si se acerca a su zona. */
public class EstrategiaDefensiva implements EstrategiaMaquina {
    public Set<Accion> decidir(Jugador jugador, Partida partida) {
        Set<Accion> acciones = new HashSet<Accion>();
        Balon balon = partida.obtenerBalon();
        double ancho = partida.obtenerCancha().obtenerAncho();
        double distancia = Math.abs(balon.obtenerCentroX() - AyudaMaquina.zonaDefensa(jugador, partida));
        if (distancia < ancho * 0.30) {
            AyudaMaquina.atacarBalon(acciones, jugador, balon);
            AyudaMaquina.activarPoder(acciones, jugador);
        } else {
            AyudaMaquina.mover(acciones, jugador, AyudaMaquina.zonaDefensa(jugador, partida));
        }
        return acciones;
    }
}
