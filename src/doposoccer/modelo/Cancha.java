package doposoccer.modelo;

/** La cancha: tamano, suelo y gravedad. Todo se escala segun la resolucion. */
public class Cancha {

    private ResolucionCancha resolucion;
    private double escala;

    public Cancha(ResolucionCancha resolucion) {
        this.resolucion = resolucion;
        this.escala = resolucion.obtenerAncho() / 1000.0;
    }

    public ResolucionCancha obtenerResolucion() {
        return resolucion;
    }

    public double obtenerAncho() {
        return resolucion.obtenerAncho();
    }

    public double obtenerAlto() {
        return resolucion.obtenerAlto();
    }

    public double obtenerEscala() {
        return escala;
    }

    /** Posicion Y del suelo (el cesped empieza aqui). */
    public double obtenerSuelo() {
        return obtenerAlto() - 40 * escala;
    }

    public double obtenerGravedad() {
        return 1800 * escala;
    }
}
