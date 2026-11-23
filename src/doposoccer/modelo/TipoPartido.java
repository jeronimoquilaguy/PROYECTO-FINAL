package doposoccer.modelo;

import java.io.Serializable;

/**
 * Forma de ganar la partida. Para agregar un tipo nuevo se crea una subclase.
 */
public abstract class TipoPartido implements Serializable {

    /**
     * Realiza la operación de actualizar.
     * @param dt valor de dt.
     */
    public void actualizar(double dt) {
    }

    /**
     * Realiza la operación de ha terminado.
     * @param marcador valor de marcador.
     * @return resultado de ha terminado.
     */
    public abstract boolean haTerminado(Marcador marcador);

    /**
     * Realiza la operación de obtener ganador.
     * @param marcador valor de marcador.
     * @return resultado de obtener ganador.
     */
    public abstract Jugador obtenerGanador(Marcador marcador);

    /**
     * Realiza la operación de obtener descripcion.
     * @return resultado de obtener descripcion.
     */
    public abstract String obtenerDescripcion();

    /** true si este tipo de partido muestra un reloj. */
    public boolean usaTiempo() {
        return false;
    }

    /**
     * Realiza la operación de obtener tiempo restante.
     * @return resultado de obtener tiempo restante.
     */
    public double obtenerTiempoRestante() {
        return 0;
    }

    /**
     * Realiza la operación de esta en muerte subita.
     * @param marcador valor de marcador.
     * @return resultado de esta en muerte subita.
     */
    public boolean estaEnMuerteSubita(Marcador marcador) {
        return false;
    }
}
