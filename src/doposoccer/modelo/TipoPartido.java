package doposoccer.modelo;

/**
 * Forma de ganar la partida. Para agregar un tipo nuevo se crea una subclase.
 */
public abstract class TipoPartido {

    public void actualizar(double dt) {
    }

    public abstract boolean haTerminado(Marcador marcador);

    public abstract Jugador obtenerGanador(Marcador marcador);

    public abstract String obtenerDescripcion();

    /** true si este tipo de partido muestra un reloj. */
    public boolean usaTiempo() {
        return false;
    }

    public double obtenerTiempoRestante() {
        return 0;
    }

    public boolean estaEnMuerteSubita(Marcador marcador) {
        return false;
    }
}
