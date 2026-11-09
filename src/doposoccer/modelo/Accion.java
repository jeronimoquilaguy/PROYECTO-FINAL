package doposoccer.modelo;

/**
 * Acciones que un jugador puede realizar.
 * Las acciones "instantaneas" se ejecutan una sola vez por pulsacion (ej: usar el poder).
 */
public enum Accion {
    IZQUIERDA(false),
    DERECHA(false),
    SALTAR(false),
    PATEAR(false),
    USAR_PODER(true);

    private final boolean instantanea;

    Accion(boolean instantanea) {
        this.instantanea = instantanea;
    }

    public boolean esInstantanea() {
        return instantanea;
    }
}
