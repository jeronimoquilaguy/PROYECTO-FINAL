package doposoccer.modelo;

/** Todo lo que se mueve en la cancha: tiene posicion, velocidad y tamano. */
public abstract class Entidad {

    protected double x;
    protected double y;
    protected double velX;
    protected double velY;
    protected double ancho;
    protected double alto;

    public double obtenerX() {
        return x;
    }

    public double obtenerY() {
        return y;
    }

    public double obtenerVelX() {
        return velX;
    }

    public double obtenerVelY() {
        return velY;
    }

    public double obtenerAncho() {
        return ancho;
    }

    public double obtenerAlto() {
        return alto;
    }

    public double obtenerCentroX() {
        return x + ancho / 2;
    }

    public double obtenerCentroY() {
        return y + alto / 2;
    }

    public void fijarPosicion(double nuevaX, double nuevaY) {
        x = nuevaX;
        y = nuevaY;
    }

    public void fijarVelocidad(double nuevaVelX, double nuevaVelY) {
        velX = nuevaVelX;
        velY = nuevaVelY;
    }

    public void aplicarGravedad(double gravedad, double dt) {
        velY = velY + gravedad * dt;
    }

    
    public boolean intersecta(double rx, double ry, double rw, double rh) {
        return x < rx + rw && x + ancho > rx && y < ry + rh && y + alto > ry;
    }

    public abstract void actualizar(double dt);
}
