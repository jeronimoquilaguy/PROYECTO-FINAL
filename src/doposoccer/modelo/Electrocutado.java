package doposoccer.modelo;

/** Efecto de Camerun: no puede saltar y se mueve al 50% de su velocidad. */
public class Electrocutado extends Efecto {

    /**
     * Construye una instancia de Electrocutado.
     */
    public Electrocutado() {
        super(3.0);
    }

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Electrocutado";
    }

    /**
     * Realiza la operación de permite saltar.
     * @return resultado de permite saltar.
     */
    public boolean permiteSaltar() {
        return false;
    }

    /**
     * Realiza la operación de obtener factor velocidad.
     * @return resultado de obtener factor velocidad.
     */
    public double obtenerFactorVelocidad() {
        return 0.5;
    }
}
