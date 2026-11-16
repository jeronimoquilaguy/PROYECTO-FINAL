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

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return nombre;
    }

    /**
     * Realiza la operación de obtener ancho.
     * @return resultado de obtener ancho.
     */
    public int obtenerAncho() {
        return ancho;
    }

    /**
     * Realiza la operación de obtener alto.
     * @return resultado de obtener alto.
     */
    public int obtenerAlto() {
        return alto;
    }
}
