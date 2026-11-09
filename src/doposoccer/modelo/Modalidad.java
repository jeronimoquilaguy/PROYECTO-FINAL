package doposoccer.modelo;

/**
 * Modalidad de juego. Para agregar "Jugador vs Maquina" se crea una subclase nueva.
 */
public abstract class Modalidad {

    public abstract String obtenerNombre();

    /** Crea el control del jugador numero 0 o 1. */
    public abstract ControlJugador crearControl(int numeroJugador);
}
