package doposoccer.modelo;

/** Efecto de Nigeria: el rival sale al aire y no puede moverse, saltar ni patear. */
public class Inconsciente extends Efecto {

    public Inconsciente() {
        super(3.0);
    }

    public String obtenerNombre() {
        return "Inconsciente";
    }

    public boolean permiteMover() {
        return false;
    }

    public boolean permiteSaltar() {
        return false;
    }

    public boolean permitePatear() {
        return false;
    }

    public boolean permitePoder() {
        return false;
    }

    public void alAplicar(Jugador jugador) {
        jugador.lanzarAlAire();
    }
}
