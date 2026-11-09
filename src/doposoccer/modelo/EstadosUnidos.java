package doposoccer.modelo;

public class EstadosUnidos extends Personaje {

    public EstadosUnidos() {
        super("Estados Unidos", 0x3C3B6E);
    }

    public Poder crearPoder() {
        return new Ilusion();
    }
}
