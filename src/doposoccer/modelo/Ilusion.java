package doposoccer.modelo;

/** Poder de Estados Unidos: el balon se "divide" y el rival queda confundido. */
public class Ilusion extends Poder {

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Ilusion";
    }

    /**
     * Realiza la operación de obtener dano tiro.
     * @return resultado de obtener dano tiro.
     */
    public double obtenerDanoTiro() {
        return 30;
    }

    /**
     * Realiza la operación de obtener color rgb.
     * @return resultado de obtener color rgb.
     */
    public int obtenerColorRgb() {
        return 0xFF69B4;
    }

    /**
     * Realiza la operación de lanzar.
     * @param balon valor de balon.
     * @param direccion valor de direccion.
     * @param cancha valor de cancha.
     */
    public void lanzar(Balon balon, int direccion, Cancha cancha) {
        double e = cancha.obtenerEscala();
        balon.configurarTiro(direccion * 600 * e, -200 * e, 0.3, 0.8);
    }

    /**
     * Realiza la operación de crear efecto.
     * @param direccion valor de direccion.
     * @return resultado de crear efecto.
     */
    public Efecto crearEfecto(int direccion) {
        return new Confusion();
    }
}
