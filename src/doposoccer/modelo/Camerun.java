package doposoccer.modelo;

public class Camerun extends Personaje {

    /**
     * Construye una instancia de Camerun.
     */
    public Camerun() {
        super("Camerún", 0x007A5E);
    }

    /**
     * Realiza la operación de crear poder.
     * @return resultado de crear poder.
     */
    public Poder crearPoder() {
        return new Rayo();
    }
}
