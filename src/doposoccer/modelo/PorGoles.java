package doposoccer.modelo;


/** Gana el primero que llegue a la meta de goles. No hay limite de tiempo. */
public class PorGoles extends TipoPartido {

    private int meta;

    /**
     * Construye una instancia de PorGoles.
     * @param meta valor de meta.
     */
    public PorGoles(int meta) throws DOPOSoccerException {
        if (meta <= 0) {
            throw new DOPOSoccerException(DOPOSoccerException.GOLES_INVALIDOS);
        }
        this.meta = meta;
    }

    /**
     * Realiza la operación de ha terminado.
     * @param marcador valor de marcador.
     * @return resultado de ha terminado.
     */
    public boolean haTerminado(Marcador marcador) {
        return marcador.obtenerMayorNumeroDeGoles() >= meta;
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
     * Realiza la operación de obtener descripcion.
     * @return resultado de obtener descripcion.
     */
    public String obtenerDescripcion() {
        return "Primero a " + meta + " goles";
    }
}
