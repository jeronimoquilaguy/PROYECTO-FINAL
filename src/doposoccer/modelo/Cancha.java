package doposoccer.modelo;

/** La cancha: tamano, suelo y gravedad. Todo se escala segun la resolucion. */
public class Cancha {

    private ResolucionCancha resolucion;
    private double escala;

    /**
     * Construye una instancia de Cancha.
     * @param resolucion valor de resolucion.
     */
    public Cancha(ResolucionCancha resolucion) {
        this.resolucion = resolucion;
        this.escala = resolucion.obtenerAncho() / 1000.0;
    }

    /**
     * Realiza la operación de obtener resolucion.
     * @return resultado de obtener resolucion.
     */
    public ResolucionCancha obtenerResolucion() {
        return resolucion;
    }

    /**
     * Realiza la operación de obtener ancho.
     * @return resultado de obtener ancho.
     */
    public double obtenerAncho() {
        return resolucion.obtenerAncho();
    }

    /**
     * Realiza la operación de obtener alto.
     * @return resultado de obtener alto.
     */
    public double obtenerAlto() {
        return resolucion.obtenerAlto();
    }

    /**
     * Realiza la operación de obtener escala.
     * @return resultado de obtener escala.
     */
    public double obtenerEscala() {
        return escala;
    }

    /** Posicion Y del suelo (el cesped empieza aqui). */
    public double obtenerSuelo() {
        return obtenerAlto() - 40 * escala;
    }

    /**
     * Realiza la operación de obtener gravedad.
     * @return resultado de obtener gravedad.
     */
    public double obtenerGravedad() {
        return 1800 * escala;
    }
}
