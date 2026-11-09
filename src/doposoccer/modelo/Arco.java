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

    public Lado obtenerLado() {
        return lado;
    }

    public double obtenerX() {
        return x;
    }

    public double obtenerAncho() {
        return ancho;
    }

    public double obtenerAlto() {
        return alto;
    }

    public double obtenerGrosor() {
        return grosor;
    }

    public double obtenerYTravesano() {
        return yTravesano;
    }
}
