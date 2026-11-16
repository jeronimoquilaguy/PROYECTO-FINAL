package doposoccer.modelo;

/** Cuando la vida llega a cero: 3 segundos sin moverse, saltar ni patear. */
public class Aturdimiento extends Efecto {

    /**
     * Construye una instancia de Aturdimiento.
     */
    public Aturdimiento() {
        super(3.0);
    }

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Aturdido";
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
     * Realiza la operación de es aturdimiento.
     * @return resultado de es aturdimiento.
     */
    public boolean esAturdimiento() {
        return true;
    }

    /**
     * Realiza la operación de al aplicar.
     * @param jugador valor de jugador.
     */
    public void alAplicar(Jugador jugador) {
        jugador.detener();
    }

    /** Al terminar el aturdimiento la vida vuelve al maximo. */
    public void alTerminar(Jugador jugador) {
        jugador.restaurarVida();
    }
}
