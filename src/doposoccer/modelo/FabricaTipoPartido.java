package doposoccer.modelo;

/**
 * Crea un tipo de partido a partir de un numero (minutos o goles).
 * La pantalla de configuracion se arma con las fabricas que existan.
 */
public abstract class FabricaTipoPartido {

    public abstract String obtenerNombre();

    /** Texto del dato que se pide: "Minutos", "Goles", etc. */
    public abstract String obtenerEtiquetaValor();

    public abstract int obtenerValorPorDefecto();

    public abstract TipoPartido crear(int valor) throws DOPOSoccerException;
}
