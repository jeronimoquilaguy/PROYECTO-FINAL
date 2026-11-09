package doposoccer.modelo;

/** Calculo de choques entre el balon (circulo) y un rectangulo. */
public class Colisiones {

    private static final double RESTITUCION = 0.8;

    private static double limitar(double valor, double minimo, double maximo) {
        if (valor < minimo) {
            return minimo;
        }
        if (valor > maximo) {
            return maximo;
        }
        return valor;
    }

    /**
     * Si el balon toca el rectangulo, lo saca del rectangulo y lo hace rebotar.
     * Devuelve true si hubo choque. (rvx, rvy) es la velocidad del rectangulo.
     */
    public static boolean balonContraRectangulo(Balon balon, double rx, double ry, double rw, double rh,
                                                double rvx, double rvy) {
        double radio = balon.obtenerRadio();
        double cx = balon.obtenerCentroX();
        double cy = balon.obtenerCentroY();
        double px = limitar(cx, rx, rx + rw);
        double py = limitar(cy, ry, ry + rh);
        double dx = cx - px;
        double dy = cy - py;
        double distancia = Math.sqrt(dx * dx + dy * dy);
        if (distancia >= radio) {
            return false;
        }

        double nx;
        double ny;
        if (distancia == 0) {
            nx = 0;
            ny = -1;
        } else {
            nx = dx / distancia;
            ny = dy / distancia;
        }

        double penetracion = radio - distancia;
        balon.fijarPosicion(balon.obtenerX() + nx * penetracion, balon.obtenerY() + ny * penetracion);

        double relX = balon.obtenerVelX() - rvx;
        double relY = balon.obtenerVelY() - rvy;
        double velNormal = relX * nx + relY * ny;
        if (velNormal < 0) {
            double factor = (1 + RESTITUCION) * velNormal;
            balon.fijarVelocidad(rvx + relX - factor * nx, rvy + relY - factor * ny);
        }
        return true;
    }
}
