package doposoccer.modelo;

/** Efecto de Camerun: no puede saltar y se mueve al 50% de su velocidad. */
public class Electrocutado extends Efecto {

    public Electrocutado() {
        super(3.0);
    }

    public String obtenerNombre() {
        return "Electrocutado";
    }

    public boolean permiteSaltar() {
        return false;
    }

    public double obtenerFactorVelocidad() {
        return 0.5;
    }
}
