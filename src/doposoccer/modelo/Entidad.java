package doposoccer.modelo;

/** Todo lo que se mueve en la cancha: tiene posicion, velocidad y tamano. */
public abstract class Entidad {

    protected double x;
    protected double y;
    protected double velX;
    protected double velY;
    protected double ancho;
    protected double alto;

    /**
     * Realiza la operación de obtener x.
     * @return resultado de obtener x.
     */
    public double obtenerX() {
        return x;
    }

    /**
     * Realiza la operación de obtener y.
     * @return resultado de obtener y.
     */
    public double obtenerY() {
        return y;
    }

    /**
     * Realiza la operación de obtener vel x.
     * @return resultado de obtener vel x.
     */
    public double obtenerVelX() {
        return velX;
    }

    /**
     * Realiza la operación de obtener vel y.
     * @return resultado de obtener vel y.
     */
    public double obtenerVelY() {
        return velY;
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
     * Realiza la operación de obtener centro x.
     * @return resultado de obtener centro x.
     */
    public double obtenerCentroX() {
        return x + ancho / 2;
    }

    /**
     * Realiza la operación de obtener centro y.
     * @return resultado de obtener centro y.
     */
    public double obtenerCentroY() {
        return y + alto / 2;
    }

    /**
     * Realiza la operación de fijar posicion.
     * @param nuevaX valor de nueva x.
     * @param nuevaY valor de nueva y.
     */
    public void fijarPosicion(double nuevaX, double nuevaY) {
        x = nuevaX;
        y = nuevaY;
    }

    /**
     * Realiza la operación de fijar velocidad.
     * @param nuevaVelX valor de nueva vel x.
     * @param nuevaVelY valor de nueva vel y.
     */
    public void fijarVelocidad(double nuevaVelX, double nuevaVelY) {
        velX = nuevaVelX;
        velY = nuevaVelY;
    }

    /**
     * Realiza la operación de aplicar gravedad.
     * @param gravedad valor de gravedad.
     * @param dt valor de dt.
     */
    public void aplicarGravedad(double gravedad, double dt) {
        velY = velY + gravedad * dt;
    }

    /** Dice si el rectangulo de la entidad se cruza con otro rectangulo. */
    public boolean intersecta(double rx, double ry, double rw, double rh) {
        return x < rx + rw && x + ancho > rx && y < ry + rh && y + alto > ry;
    }

    /**
     * Realiza la operación de actualizar.
     * @param dt valor de dt.
     */
    public abstract void actualizar(double dt);
}
