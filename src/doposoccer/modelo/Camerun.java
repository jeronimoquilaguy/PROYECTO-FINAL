package doposoccer.modelo;

public class Camerun extends Personaje {

    public Camerun() {
        super("Camerún", 0x007A5E);
    }

    public Poder crearPoder() {
        return new Rayo();
    }
}
