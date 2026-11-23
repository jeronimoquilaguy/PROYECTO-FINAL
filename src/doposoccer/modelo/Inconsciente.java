package doposoccer.modelo;


/** Efecto de Nigeria: el rival sale al aire y no puede moverse, saltar ni patear. */
public class Inconsciente extends Efecto {

    /**
     * Construye una instancia de Inconsciente.
     */
    public Inconsciente() {
        super(3.0);
    }

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Inconsciente";
    }

    /**
     * Realiza la operación de permite mover.
     * @return resultado de permite mover.
     */
    public boolean permiteMover() {
        return false;
    }

    /**
     * Realiza la operación de permite saltar.
     * @return resultado de permite saltar.
     */
    public boolean permiteSaltar() {
        return false;
    }

    /**
     * Realiza la operación de permite patear.
     * @return resultado de permite patear.
     */
    public boolean permitePatear() {
        return false;
    }

    /**
     * Realiza la operación de permite poder.
     * @return resultado de permite poder.
     */
    public boolean permitePoder() {
        return false;
    }

    /**
     * Realiza la operación de al aplicar.
     * @param jugador valor de jugador.
     */
    public void alAplicar(Jugador jugador) {
        jugador.lanzarAlAire();
    }
}
