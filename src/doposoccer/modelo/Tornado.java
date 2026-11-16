package doposoccer.modelo;

/** Poder de Nigeria: remolino a ras de suelo que rebota rapidamente. */
public class Tornado extends Poder {

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Tornado";
    }

    /**
     * Realiza la operación de obtener dano tiro.
     * @return resultado de obtener dano tiro.
     */
    public double obtenerDanoTiro() {
        return 40;
    }

    /**
     * Realiza la operación de obtener color rgb.
     * @return resultado de obtener color rgb.
     */
    public int obtenerColorRgb() {
        return 0xB0B0B0;
    }

    /**
     * Realiza la operación de lanzar.
     * @param balon valor de balon.
     * @param direccion valor de direccion.
     * @param cancha valor de cancha.
     */
    public void lanzar(Balon balon, int direccion, Cancha cancha) {
        // El balon baja al suelo y sale rebotando muy bajito
        balon.fijarPosicion(balon.obtenerX(), cancha.obtenerSuelo() - balon.obtenerAlto());
        double e = cancha.obtenerEscala();
        balon.configurarTiro(direccion * 800 * e, -250 * e, 3.0, 0.97);
    }

    /**
     * Realiza la operación de crear efecto.
     * @param direccion valor de direccion.
     * @return resultado de crear efecto.
     */
    public Efecto crearEfecto(int direccion) {
        return new Inconsciente();
    }
}
