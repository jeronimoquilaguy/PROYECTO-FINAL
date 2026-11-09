package doposoccer.modelo;

/** Las tres resoluciones de la cancha. */
public enum ResolucionCancha {
    PEQUENA("Pequeña (800 x 450)", 800, 450),
    MEDIANA("Mediana (1000 x 560)", 1000, 560),
    GRANDE("Grande (1200 x 675)", 1200, 675);

    private final String nombre;
    private final int ancho;
    private final int alto;

    ResolucionCancha(String nombre, int ancho, int alto) {
        this.nombre = nombre;
        this.ancho = ancho;
        this.alto = alto;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public int obtenerAncho() {
        return ancho;
    }

    public int obtenerAlto() {
        return alto;
    }
}
