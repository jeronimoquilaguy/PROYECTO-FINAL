package doposoccer.modelo;


/** Efecto de Corea del Sur: el rival sale lanzado hacia su propio arco. */
public class Empuje extends Efecto {

    private int direccion;

    /**
     * Construye una instancia de Empuje.
     * @param direccion valor de direccion.
     */
    public Empuje(int direccion) {
        super(1.5);
        this.direccion = direccion;
    }

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Empujado";
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
     * Realiza la operación de al aplicar.
     * @param jugador valor de jugador.
     */
    public void alAplicar(Jugador jugador) {
        jugador.empujar(direccion);
    }
}
