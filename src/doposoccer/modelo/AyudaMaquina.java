package doposoccer.modelo;

import java.util.Set;

/** Movimientos comunes de las estrategias. */
public class AyudaMaquina {
    public static void mover(Set<Accion> acciones, Jugador jugador, double destino) {
        double diferencia = destino - jugador.obtenerCentroX();
        if (diferencia < -12) acciones.add(Accion.IZQUIERDA);
        if (diferencia > 12) acciones.add(Accion.DERECHA);
    }

    public static void atacarBalon(Set<Accion> acciones, Jugador jugador, Balon balon) {
        mover(acciones, jugador, balon.obtenerCentroX());
        double distancia = Math.abs(balon.obtenerCentroX() - jugador.obtenerCentroX());
        if (distancia < jugador.obtenerAncho() * 1.4
                && Math.abs(balon.obtenerCentroY() - jugador.obtenerCentroY()) < jugador.obtenerAlto()) {
            acciones.add(Accion.PATEAR);
        }
        if (distancia < jugador.obtenerAncho() * 2
                && balon.obtenerCentroY() < jugador.obtenerCentroY() - jugador.obtenerAlto() * 0.25) {
            acciones.add(Accion.SALTAR);
        }
    }

    public static void activarPoder(Set<Accion> acciones, Jugador jugador) {
        if (jugador.obtenerBarraPoder() >= 1.0 && !jugador.tienePoderActivo()) {
            acciones.add(Accion.USAR_PODER);
        }
    }

    public static double zonaDefensa(Jugador jugador, Partida partida) {
        double ancho = partida.obtenerCancha().obtenerAncho();
        return jugador.obtenerLado() == Lado.IZQUIERDO ? ancho * 0.22 : ancho * 0.78;
    }
}
