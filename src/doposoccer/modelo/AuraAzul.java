package doposoccer.modelo;

/** Poder de Corea del Sur: tiro recto y horizontal a alta velocidad, empuja al rival. */
public class AuraAzul extends Poder {

    public String obtenerNombre() {
        return "Aura Azul";
    }

    public double obtenerDanoTiro() {
        return 40;
    }

    public int obtenerColorRgb() {
        return 0x1E90FF;
    }

    public void lanzar(Balon balon, int direccion, Cancha cancha) {
        balon.configurarTiro(direccion * 1400 * cancha.obtenerEscala(), 0, 0, 0.8);
    }

    public Efecto crearEfecto(int direccion) {
        return new Empuje(direccion);
    }
}
