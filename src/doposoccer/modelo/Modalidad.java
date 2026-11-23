package doposoccer.modelo;

import java.io.Serializable;

/**
 * Modalidad de juego. Para agregar "Jugador vs Maquina" se crea una subclase nueva.
 */
public abstract class Modalidad implements Serializable {

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public abstract String obtenerNombre();

    /** Crea el control del jugador numero 0 o 1. */
    public abstract ControlJugador crearControl(int numeroJugador);
}
