package doposoccer.modelo;

/** Poder de Camerun: tiro recto cubierto de electricidad. */
public class Rayo extends Poder {

    public String obtenerNombre() {
        return "Rayo";
    }

    public double obtenerDanoTiro() {
        return 60;
    }

    public int obtenerColorRgb() {
        return 0xFFD700;
    }

    public void lanzar(Balon balon, int direccion, Cancha cancha) {
        balon.configurarTiro(direccion * 1000 * cancha.obtenerEscala(), 0, 0, 0.8);
    }

    public Efecto crearEfecto(int direccion) {
        return new Electrocutado();
    }
}
