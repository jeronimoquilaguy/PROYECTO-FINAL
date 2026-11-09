package doposoccer.modelo;

/** Efecto de Estados Unidos: el rival no sabe cual es el balon real. */
public class Confusion extends Efecto {

    public Confusion() {
        super(2.5);
    }

    public String obtenerNombre() {
        return "Confundido";
    }

    public boolean esConfusion() {
        return true;
    }
}
