package doposoccer.modelo;

import java.util.Set;

/** Cambia de tactica segun el marcador y la posicion del balon. */
public class EstrategiaExperta implements EstrategiaMaquina {
    private EstrategiaDefensiva defensa = new EstrategiaDefensiva();
    private EstrategiaOfensiva ataque = new EstrategiaOfensiva();

    public Set<Accion> decidir(Jugador jugador, Partida partida) {
        Jugador[] jugadores = partida.obtenerJugadores();
        Jugador rival = jugadores[0] == jugador ? jugadores[1] : jugadores[0];
        int propios = partida.obtenerMarcador().obtenerGoles(jugador);
        int contrarios = partida.obtenerMarcador().obtenerGoles(rival);
        double ancho = partida.obtenerCancha().obtenerAncho();
        double x = partida.obtenerBalon().obtenerCentroX();
        boolean balonEnMiCampo = jugador.obtenerLado() == Lado.IZQUIERDO
                ? x < ancho * 0.50 : x > ancho * 0.50;
        // Si va ganando protege el arco; si va perdiendo presiona el ataque.
        if (propios > contrarios && !balonEnMiCampo) {
            return defensa.decidir(jugador, partida);
        }
        if (balonEnMiCampo && Math.abs(x - AyudaMaquina.zonaDefensa(jugador, partida)) < ancho * 0.25) {
            return defensa.decidir(jugador, partida);
        }
        return ataque.decidir(jugador, partida);
    }
}
