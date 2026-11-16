package doposoccer.modelo;

/** Poder de Camerun: tiro recto cubierto de electricidad. */
public class Rayo extends Poder {

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Rayo";
    }

    /**
     * Realiza la operación de obtener dano tiro.
     * @return resultado de obtener dano tiro.
     */
    public double obtenerDanoTiro() {
        return 60;
    }

    /**
     * Realiza la operación de obtener color rgb.
     * @return resultado de obtener color rgb.
     */
    public int obtenerColorRgb() {
        return 0xFFD700;
    }

    /**
     * Realiza la operación de lanzar.
     * @param balon valor de balon.
     * @param direccion valor de direccion.
     * @param cancha valor de cancha.
     */
    public void lanzar(Balon balon, int direccion, Cancha cancha) {
        balon.configurarTiro(direccion * 1000 * cancha.obtenerEscala(), 0, 0, 0.8);
    }

    /**
     * Realiza la operación de crear efecto.
     * @param direccion valor de direccion.
     * @return resultado de crear efecto.
     */
    public Efecto crearEfecto(int direccion) {
        return new Electrocutado();
    }
}
