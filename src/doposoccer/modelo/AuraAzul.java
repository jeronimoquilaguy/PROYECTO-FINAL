package doposoccer.modelo;

/** Poder de Corea del Sur: tiro recto y horizontal a alta velocidad, empuja al rival. */
public class AuraAzul extends Poder {

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Aura Azul";
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
        return 0x1E90FF;
    }

    /**
     * Realiza la operación de lanzar.
     * @param balon valor de balon.
     * @param direccion valor de direccion.
     * @param cancha valor de cancha.
     */
    public void lanzar(Balon balon, int direccion, Cancha cancha) {
        balon.configurarTiro(direccion * 1400 * cancha.obtenerEscala(), 0, 0, 0.8);
    }

    /**
     * Realiza la operación de crear efecto.
     * @param direccion valor de direccion.
     * @return resultado de crear efecto.
     */
    public Efecto crearEfecto(int direccion) {
        return new Empuje(direccion);
    }
}
