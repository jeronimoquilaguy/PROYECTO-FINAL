package doposoccer.modelo;

public class CoreaDelSur extends Personaje {

    public CoreaDelSur() {
        super("Corea del Sur", 0xD2042D);
    }

    public Poder crearPoder() {
        return new AuraAzul();
    }
}
