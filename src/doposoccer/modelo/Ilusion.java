package doposoccer.modelo;

/** Poder de Estados Unidos: el balon se "divide" y el rival queda confundido. */
public class Ilusion extends Poder {

    public String obtenerNombre() {
        return "Ilusion";
    }

    public double obtenerDanoTiro() {
        return 30;
    }

    public int obtenerColorRgb() {
        return 0xFF69B4;
    }

    public void lanzar(Balon balon, int direccion, Cancha cancha) {
        double e = cancha.obtenerEscala();
        balon.configurarTiro(direccion * 600 * e, -200 * e, 0.3, 0.8);
    }

    public Efecto crearEfecto(int direccion) {
        return new Confusion();
    }
}
