package doposoccer.modelo;

import java.io.Serializable;

/**
 * Efecto temporal sobre un jugador (electrocutado, aturdido, etc).
 * Para crear un efecto nuevo basta con extender esta clase.
 */
public abstract class Efecto implements Serializable {

    private double restante;

    /**
     * Construye una instancia de Efecto.
     * @param duracion valor de duracion.
     */
    protected Efecto(double duracion) {
        this.restante = duracion;
    }

    /**
     * Realiza la operación de actualizar.
     * @param dt valor de dt.
     */
    public void actualizar(double dt) {
        restante = restante - dt;
    }

    /**
     * Realiza la operación de ha terminado.
     * @return resultado de ha terminado.
     */
    public boolean haTerminado() {
        return restante <= 0;
    }

    /**
     * Realiza la operación de obtener restante.
     * @return resultado de obtener restante.
     */
    public double obtenerRestante() {
        return restante;
    }

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public abstract String obtenerNombre();

    /**
     * Realiza la operación de permite mover.
     * @return resultado de permite mover.
     */
    public boolean permiteMover() {
        return true;
    }

    /**
     * Realiza la operación de permite saltar.
     * @return resultado de permite saltar.
     */
    public boolean permiteSaltar() {
        return true;
    }

    /**
     * Realiza la operación de permite patear.
     * @return resultado de permite patear.
     */
    public boolean permitePatear() {
        return true;
    }

    /**
     * Realiza la operación de permite poder.
     * @return resultado de permite poder.
     */
    public boolean permitePoder() {
        return true;
    }

    /**
     * Realiza la operación de obtener factor velocidad.
     * @return resultado de obtener factor velocidad.
     */
    public double obtenerFactorVelocidad() {
        return 1.0;
    }

    /**
     * Realiza la operación de es confusion.
     * @return resultado de es confusion.
     */
    public boolean esConfusion() {
        return false;
    }

    /**
     * Realiza la operación de es aturdimiento.
     * @return resultado de es aturdimiento.
     */
    public boolean esAturdimiento() {
        return false;
    }

    /** Se llama una vez cuando el efecto empieza. */
    public void alAplicar(Jugador jugador) {
    }

    /** Se llama una vez cuando el efecto termina. */
    public void alTerminar(Jugador jugador) {
    }
}
