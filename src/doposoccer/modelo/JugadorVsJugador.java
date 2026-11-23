package doposoccer.modelo;


public class JugadorVsJugador extends Modalidad {

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Jugador vs Jugador";
    }

    /**
     * Realiza la operación de crear control.
     * @param numeroJugador valor de numero jugador.
     * @return resultado de crear control.
     */
    public ControlJugador crearControl(int numeroJugador) {
        return new ControlHumano();
    }
}
