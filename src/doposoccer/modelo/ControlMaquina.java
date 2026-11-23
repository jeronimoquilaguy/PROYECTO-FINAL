package doposoccer.modelo;

import java.util.Set;

/** Delega las decisiones a un perfil de inteligencia artificial. */
public class ControlMaquina implements ControlJugador {
    private PerfilMaquina perfil;
    private EstrategiaMaquina estrategia;

    public ControlMaquina(PerfilMaquina perfil) {
        if (perfil == null) throw new IllegalArgumentException("Seleccione un perfil de maquina.");
        this.perfil = perfil;
        if (perfil == PerfilMaquina.TORPE) estrategia = new EstrategiaTorpe();
        else if (perfil == PerfilMaquina.OFENSIVA) estrategia = new EstrategiaOfensiva();
        else if (perfil == PerfilMaquina.DEFENSIVA) estrategia = new EstrategiaDefensiva();
        else estrategia = new EstrategiaExperta();
    }

    /** Permite agregar nuevas estrategias sin cambiar la partida. */
    public ControlMaquina(PerfilMaquina perfil, EstrategiaMaquina estrategia) {
        this.perfil = perfil;
        this.estrategia = estrategia;
    }

    public PerfilMaquina obtenerPerfil() { return perfil; }

    public Set<Accion> obtenerAcciones(Jugador jugador, Partida partida) {
        return estrategia.decidir(jugador, partida);
    }

    public void presionar(Accion accion) { }
    public void soltar(Accion accion) { }
}
