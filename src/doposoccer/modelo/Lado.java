package doposoccer.modelo;

/** Lado de la cancha que defiende un jugador. */
public enum Lado {
    IZQUIERDO(1),
    DERECHO(-1);

    private final int direccionFrente;

    Lado(int direccionFrente) {
        this.direccionFrente = direccionFrente;
    }

    /** 1 si el jugador mira hacia la derecha, -1 si mira hacia la izquierda. */
    public int obtenerDireccionFrente() {
        return direccionFrente;
    }
}
