package doposoccer.modelo;

/** Efecto de Corea del Sur: el rival sale lanzado hacia su propio arco. */
public class Empuje extends Efecto {

    private int direccion;

    public Empuje(int direccion) {
        super(1.5);
        this.direccion = direccion;
    }

    public String obtenerNombre() {
        return "Empujado";
    }

    public boolean permiteMover() {
        return false;
    }

    public boolean permiteSaltar() {
        return false;
    }

    public boolean permitePatear() {
        return false;
    }

    public void alAplicar(Jugador jugador) {
        jugador.empujar(direccion);
    }
}
