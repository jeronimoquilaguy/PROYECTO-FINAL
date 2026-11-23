package doposoccer.modelo;


public class CoreaDelSur extends Personaje {

    /**
     * Construye una instancia de CoreaDelSur.
     */
    public CoreaDelSur() {
        super("Corea del Sur", 0xD2042D);
    }

    /**
     * Realiza la operación de crear poder.
     * @return resultado de crear poder.
     */
    public Poder crearPoder() {
        return new AuraAzul();
    }
}
