package doposoccer.modelo;

/**
 * Tiro de poder. Para agregar un poder nuevo se crea una subclase
 * (no hay que modificar ninguna clase existente).
 */
public abstract class Poder {

    public abstract String obtenerNombre();

    /** Dano base del tiro (antes de multiplicar por el multiplicadorKick). */
    public abstract double obtenerDanoTiro();

    /** Color del aura y del tiro, como numero RGB (ej: 0x1E90FF). */
    public abstract int obtenerColorRgb();

    /** Da al balon la velocidad propia de este tiro. direccion: 1 derecha, -1 izquierda. */
    public abstract void lanzar(Balon balon, int direccion, Cancha cancha);

    /** Crea el efecto que sufre el rival si el tiro lo golpea. */
    public abstract Efecto crearEfecto(int direccion);
}
