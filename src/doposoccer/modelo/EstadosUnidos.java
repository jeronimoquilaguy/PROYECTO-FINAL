package doposoccer.modelo;

public class EstadosUnidos extends Personaje {

    /**
     * Construye una instancia de EstadosUnidos.
     */
    public EstadosUnidos() {
        super("Estados Unidos", 0x3C3B6E);
    }

    /**
     * Realiza la operación de crear poder.
     * @return resultado de crear poder.
     */
    public Poder crearPoder() {
        return new Ilusion();
    }
}
