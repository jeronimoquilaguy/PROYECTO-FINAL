package doposoccer.modelo;

public class JugadorVsJugador extends Modalidad {

    public String obtenerNombre() {
        return "Jugador vs Jugador";
    }

    public ControlJugador crearControl(int numeroJugador) {
        return new ControlHumano();
    }
}
