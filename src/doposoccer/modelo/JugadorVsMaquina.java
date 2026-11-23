package doposoccer.modelo;

/** Modalidad donde el jugador 1 es humano y el jugador 2 es controlado por la maquina. */
public class JugadorVsMaquina extends Modalidad {

    private PerfilMaquina perfil;

    public JugadorVsMaquina() {
        this(PerfilMaquina.TORPE);
    }

    public JugadorVsMaquina(PerfilMaquina perfil) {
        this.perfil = perfil;
    }

    public String obtenerNombre() {
        return "Jugador vs Maquina";
    }

    public ControlJugador crearControl(int numeroJugador) {
        if (numeroJugador == 0) {
            return new ControlHumano();
        }
        return new ControlMaquina(perfil);
    }

    public PerfilMaquina obtenerPerfil() {
        return perfil;
    }
}
