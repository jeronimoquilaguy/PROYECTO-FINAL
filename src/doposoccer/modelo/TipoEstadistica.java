package doposoccer.modelo;

/** Las cuatro estadisticas que se pueden mejorar. */
public enum TipoEstadistica {
    SPEED("Velocidad"),
    JUMP("Salto"),
    KICK("Patada"),
    SURVIVAL("Vida");

    private final String nombre;

    TipoEstadistica(String nombre) {
        this.nombre = nombre;
    }

    public String obtenerNombre() {
        return nombre;
    }
}
