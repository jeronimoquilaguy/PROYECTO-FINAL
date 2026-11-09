package doposoccer.modelo;

public class Nigeria extends Personaje {

    public Nigeria() {
        super("Nigeria", 0x2E9E3E);
    }

    public Poder crearPoder() {
        return new Tornado();
    }
}
