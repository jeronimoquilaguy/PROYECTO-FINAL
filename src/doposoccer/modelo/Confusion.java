package doposoccer.modelo;

/** Efecto de Estados Unidos: el rival no sabe cual es el balon real. */
public class Confusion extends Efecto {

    /**
     * Construye una instancia de Confusion.
     */
    public Confusion() {
        super(2.5);
    }

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Confundido";
    }

    /**
     * Realiza la operación de es confusion.
     * @return resultado de es confusion.
     */
    public boolean esConfusion() {
        return true;
    }
}
