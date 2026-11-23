package doposoccer.modelo;


public class Nigeria extends Personaje {

    /**
     * Construye una instancia de Nigeria.
     */
    public Nigeria() {
        super("Nigeria", 0x2E9E3E);
    }

    /**
     * Realiza la operación de crear poder.
     * @return resultado de crear poder.
     */
    public Poder crearPoder() {
        return new Tornado();
    }
}
