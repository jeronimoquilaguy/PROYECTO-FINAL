package doposoccer.modelo;

/** Cuando la vida llega a cero: 3 segundos sin moverse, saltar ni patear. */
public class Aturdimiento extends Efecto {

    public Aturdimiento() {
        super(3.0);
    }

    public String obtenerNombre() {
        return "Aturdido";
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

    public boolean permitePoder() {
        return false;
    }

    public boolean esAturdimiento() {
        return true;
    }

    public void alAplicar(Jugador jugador) {
        jugador.detener();
    }

    /** Al terminar el aturdimiento la vida vuelve al maximo. */
    public void alTerminar(Jugador jugador) {
        jugador.restaurarVida();
    }
}
