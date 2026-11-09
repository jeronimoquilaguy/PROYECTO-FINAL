package doposoccer.modelo;

/**
 * Efecto temporal sobre un jugador (electrocutado, aturdido, etc).
 * Para crear un efecto nuevo basta con extender esta clase.
 */
public abstract class Efecto {

    private double restante;

    protected Efecto(double duracion) {
        this.restante = duracion;
    }

    public void actualizar(double dt) {
        restante = restante - dt;
    }

    public boolean haTerminado() {
        return restante <= 0;
    }

    public double obtenerRestante() {
        return restante;
    }

    public abstract String obtenerNombre();

    public boolean permiteMover() {
        return true;
    }

    public boolean permiteSaltar() {
        return true;
    }

    public boolean permitePatear() {
        return true;
    }

    public boolean permitePoder() {
        return true;
    }

    public double obtenerFactorVelocidad() {
        return 1.0;
    }

    public boolean esConfusion() {
        return false;
    }

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
