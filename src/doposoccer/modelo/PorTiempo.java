package doposoccer.modelo;


/** Gana quien tenga mas goles al terminar el tiempo. Si hay empate: muerte subita. */
public class PorTiempo extends TipoPartido {

    private double tiempoRestante;
    private int minutos;

    /**
     * Construye una instancia de PorTiempo.
     * @param minutos valor de minutos.
     */
    public PorTiempo(int minutos) throws DOPOSoccerException {
        if (minutos <= 0) {
            throw new DOPOSoccerException(DOPOSoccerException.MINUTOS_INVALIDOS);
        }
        this.minutos = minutos;
        this.tiempoRestante = minutos * 60.0;
    }

    /**
     * Realiza la operación de actualizar.
     * @param dt valor de dt.
     */
    public void actualizar(double dt) {
        if (tiempoRestante > 0) {
            tiempoRestante = tiempoRestante - dt;
            if (tiempoRestante < 0) {
                tiempoRestante = 0;
            }
        }
    }

    /**
     * Realiza la operación de ha terminado.
     * @param marcador valor de marcador.
     * @return resultado de ha terminado.
     */
    public boolean haTerminado(Marcador marcador) {
        return tiempoRestante <= 0 && marcador.obtenerLider() != null;
    }

    /**
     * Realiza la operación de obtener ganador.
     * @param marcador valor de marcador.
     * @return resultado de obtener ganador.
     */
    public Jugador obtenerGanador(Marcador marcador) {
        return marcador.obtenerLider();
    }

    /**
     * Realiza la operación de esta en muerte subita.
     * @param marcador valor de marcador.
     * @return resultado de esta en muerte subita.
     */
    public boolean estaEnMuerteSubita(Marcador marcador) {
        return tiempoRestante <= 0 && marcador.obtenerLider() == null;
    }

    /**
     * Realiza la operación de usa tiempo.
     * @return resultado de usa tiempo.
     */
    public boolean usaTiempo() {
        return true;
    }

    /**
     * Realiza la operación de obtener tiempo restante.
     * @return resultado de obtener tiempo restante.
     */
    public double obtenerTiempoRestante() {
        return tiempoRestante;
    }

    /**
     * Realiza la operación de obtener descripcion.
     * @return resultado de obtener descripcion.
     */
    public String obtenerDescripcion() {
        return "Por tiempo (" + minutos + " min)";
    }
}
