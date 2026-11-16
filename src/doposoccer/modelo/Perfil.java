package doposoccer.modelo;

/** Progreso de un jugador humano: puntos acumulados y estadisticas. */
public class Perfil {

    private String nombre;
    private int puntos;
    private Estadisticas estadisticas;

    /**
     * Construye una instancia de Perfil.
     * @param nombre valor de nombre.
     */
    public Perfil(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
        this.estadisticas = new Estadisticas();
    }

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return nombre;
    }

    /**
     * Realiza la operación de obtener puntos.
     * @return resultado de obtener puntos.
     */
    public int obtenerPuntos() {
        return puntos;
    }

    /**
     * Realiza la operación de obtener estadisticas.
     * @return resultado de obtener estadisticas.
     */
    public Estadisticas obtenerEstadisticas() {
        return estadisticas;
    }

    /**
     * Realiza la operación de sumar puntos.
     * @param cantidad valor de cantidad.
     */
    public void sumarPuntos(int cantidad) {
        puntos += cantidad;
    }

    /**
     * Realiza la operación de mejorar.
     * @param tipo valor de tipo.
     */
    public void mejorar(TipoEstadistica tipo) throws DOPOSoccerException {
        int precio = estadisticas.obtenerPrecioMejora(tipo);
        if (puntos < precio) {
            throw new DOPOSoccerException("Puntos insuficientes para mejorar " + tipo.obtenerNombre()
                    + ": cuesta " + precio + " y usted tiene " + puntos + ".");
        }
        estadisticas.subirNivel(tipo);
        puntos -= precio;
    }
}
