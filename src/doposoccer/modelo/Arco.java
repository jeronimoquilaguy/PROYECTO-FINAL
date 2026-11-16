package doposoccer.modelo;

/** El arco de un lado de la cancha. Tiene un travesano donde el balon rebota. */
public class Arco {

    private Lado lado;
    private Cancha cancha;
    private double x;
    private double ancho;
    private double alto;
    private double grosor;
    private double yTravesano;

    /**
     * Construye una instancia de Arco.
     * @param lado valor de lado.
     * @param cancha valor de cancha.
     */
    public Arco(Lado lado, Cancha cancha) {
        double e = cancha.obtenerEscala();
        this.lado = lado;
        this.cancha = cancha;
        this.ancho = 70 * e;
        this.alto = 160 * e;
        this.grosor = 10 * e;
        if (lado == Lado.IZQUIERDO) {
            this.x = 0;
        } else {
            this.x = cancha.obtenerAncho() - ancho;
        }
        this.yTravesano = cancha.obtenerSuelo() - alto;
    }

    /** Hay gol cuando el balon entra completamente en el arco, por debajo del travesano. */
    public boolean contiene(Balon balon) {
        boolean dentroX;
        if (lado == Lado.IZQUIERDO) {
            dentroX = balon.obtenerX() + balon.obtenerAncho() <= ancho;
        } else {
            dentroX = balon.obtenerX() >= cancha.obtenerAncho() - ancho;
        }
        boolean dentroY = balon.obtenerY() >= yTravesano + grosor;
        return dentroX && dentroY;
    }

    /**
     * Realiza la operación de obtener lado.
     * @return resultado de obtener lado.
     */
    public Lado obtenerLado() {
        return lado;
    }

    /**
     * Realiza la operación de obtener x.
     * @return resultado de obtener x.
     */
    public double obtenerX() {
        return x;
    }

    /**
     * Realiza la operación de obtener ancho.
     * @return resultado de obtener ancho.
     */
    public double obtenerAncho() {
        return ancho;
    }

    /**
     * Realiza la operación de obtener alto.
     * @return resultado de obtener alto.
     */
    public double obtenerAlto() {
        return alto;
    }

    /**
     * Realiza la operación de obtener grosor.
     * @return resultado de obtener grosor.
     */
    public double obtenerGrosor() {
        return grosor;
    }

    /**
     * Realiza la operación de obtener ytravesano.
     * @return resultado de obtener ytravesano.
     */
    public double obtenerYTravesano() {
        return yTravesano;
    }
}
