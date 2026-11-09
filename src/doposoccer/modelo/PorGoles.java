package doposoccer.modelo;

/** Gana el primero que llegue a la meta de goles. No hay limite de tiempo. */
public class PorGoles extends TipoPartido {

    private int meta;

    public PorGoles(int meta) throws DOPOSoccerException {
        if (meta <= 0) {
            throw new DOPOSoccerException(DOPOSoccerException.GOLES_INVALIDOS);
        }
        this.meta = meta;
    }

    public boolean haTerminado(Marcador marcador) {
        return marcador.obtenerMayorNumeroDeGoles() >= meta;
    }

    public Jugador obtenerGanador(Marcador marcador) {
        return marcador.obtenerLider();
    }

    public String obtenerDescripcion() {
        return "Primero a " + meta + " goles";
    }
}
