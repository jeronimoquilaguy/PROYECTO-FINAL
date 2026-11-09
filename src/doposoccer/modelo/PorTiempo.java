package doposoccer.modelo;

/** Gana quien tenga mas goles al terminar el tiempo. Si hay empate: muerte subita. */
public class PorTiempo extends TipoPartido {

    private double tiempoRestante;
    private int minutos;

    public PorTiempo(int minutos) throws DOPOSoccerException {
        if (minutos <= 0) {
            throw new DOPOSoccerException(DOPOSoccerException.MINUTOS_INVALIDOS);
        }
        this.minutos = minutos;
        this.tiempoRestante = minutos * 60.0;
    }

    public void actualizar(double dt) {
        if (tiempoRestante > 0) {
            tiempoRestante = tiempoRestante - dt;
            if (tiempoRestante < 0) {
                tiempoRestante = 0;
            }
        }
    }

    public boolean haTerminado(Marcador marcador) {
        return tiempoRestante <= 0 && marcador.obtenerLider() != null;
    }

    public Jugador obtenerGanador(Marcador marcador) {
        return marcador.obtenerLider();
    }

    public boolean estaEnMuerteSubita(Marcador marcador) {
        return tiempoRestante <= 0 && marcador.obtenerLider() == null;
    }

    public boolean usaTiempo() {
        return true;
    }

    public double obtenerTiempoRestante() {
        return tiempoRestante;
    }

    public String obtenerDescripcion() {
        return "Por tiempo (" + minutos + " min)";
    }
}
