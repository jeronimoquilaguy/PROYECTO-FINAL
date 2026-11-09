package doposoccer.modelo;

/** Poder de Nigeria: remolino a ras de suelo que rebota rapidamente. */
public class Tornado extends Poder {

    public String obtenerNombre() {
        return "Tornado";
    }

    public double obtenerDanoTiro() {
        return 40;
    }

    public int obtenerColorRgb() {
        return 0xB0B0B0;
    }

    public void lanzar(Balon balon, int direccion, Cancha cancha) {
        // El balon baja al suelo y sale rebotando muy bajito
        balon.fijarPosicion(balon.obtenerX(), cancha.obtenerSuelo() - balon.obtenerAlto());
        double e = cancha.obtenerEscala();
        balon.configurarTiro(direccion * 800 * e, -250 * e, 3.0, 0.97);
    }

    public Efecto crearEfecto(int direccion) {
        return new Inconsciente();
    }
}
