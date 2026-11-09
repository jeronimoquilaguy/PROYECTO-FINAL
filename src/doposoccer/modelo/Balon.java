package doposoccer.modelo;

/** El balon. Cae por la gravedad, rebota y puede llevar un tiro de poder. */
public class Balon extends Entidad {

    private static final double REBOTE_NORMAL = 0.75;
    private static final double DURACION_MAXIMA_PODER = 4.0;

    private Cancha cancha;
    private double radio;
    private double factorGravedad;
    private double rebote;
    private Poder poder;
    private Jugador dueno;
    private double tiempoPoder;

    public Balon(Cancha cancha) {
        this.cancha = cancha;
        this.radio = 15 * cancha.obtenerEscala();
        this.ancho = 2 * radio;
        this.alto = 2 * radio;
        reiniciarEnCentro();
    }

    public double obtenerRadio() {
        return radio;
    }

    /** El balon cae desde el centro de la cancha. */
    public void reiniciarEnCentro() {
        fijarPosicion(cancha.obtenerAncho() / 2 - radio, cancha.obtenerAlto() * 0.15);
        fijarVelocidad(0, 0);
        terminarTiroPoder();
    }

    public void actualizar(double dt) {
        if (poder != null) {
            tiempoPoder = tiempoPoder - dt;
            if (tiempoPoder <= 0) {
                terminarTiroPoder();
            }
        }
        aplicarGravedad(cancha.obtenerGravedad() * factorGravedad, dt);
        x = x + velX * dt;
        y = y + velY * dt;
        rebotarEnSuelo(dt);
        rebotarEnBordes();
    }

    private void rebotarEnSuelo(double dt) {
        double suelo = cancha.obtenerSuelo();
        if (y + alto >= suelo) {
            y = suelo - alto;
            if (velY > 0) {
                velY = -velY * rebote;
                if (Math.abs(velY) < 60 * cancha.obtenerEscala()) {
                    velY = 0;
                }
            }
            velX = velX * (1 - 0.8 * dt);
        }
    }

    private void rebotarEnBordes() {
        if (x < 0) {
            x = 0;
            velX = -velX * 0.9;
            terminarTiroPoder();
        }
        if (x + ancho > cancha.obtenerAncho()) {
            x = cancha.obtenerAncho() - ancho;
            velX = -velX * 0.9;
            terminarTiroPoder();
        }
        if (y < 0) {
            y = 0;
            velY = -velY * 0.9;
            terminarTiroPoder();
        }
    }

    /** Una patada normal solo impulsa el balon. */
    public void recibirPatada(int direccion) {
        double e = cancha.obtenerEscala();
        fijarVelocidad(direccion * 650 * e, -450 * e);
    }

    /** Usado por los poderes para dar velocidad al tiro. */
    public void configurarTiro(double nuevaVelX, double nuevaVelY, double nuevoFactorGravedad, double nuevoRebote) {
        fijarVelocidad(nuevaVelX, nuevaVelY);
        factorGravedad = nuevoFactorGravedad;
        rebote = nuevoRebote;
    }

    public void iniciarTiroPoder(Poder nuevoPoder, Jugador nuevoDueno) {
        poder = nuevoPoder;
        dueno = nuevoDueno;
        tiempoPoder = DURACION_MAXIMA_PODER;
    }

    /** El balon vuelve a ser un balon normal. */
    public void terminarTiroPoder() {
        poder = null;
        dueno = null;
        tiempoPoder = 0;
        factorGravedad = 1.0;
        rebote = REBOTE_NORMAL;
    }

    public boolean tienePoderEnVuelo() {
        return poder != null;
    }

    public Poder obtenerPoder() {
        return poder;
    }

    public Jugador obtenerDueno() {
        return dueno;
    }
}
