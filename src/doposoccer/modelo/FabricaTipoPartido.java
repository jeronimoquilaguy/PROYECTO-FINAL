package doposoccer.modelo;

/**
 * Crea un tipo de partido a partir de un numero (minutos o goles).
 * La pantalla de configuracion se arma con las fabricas que existan.
 */
public abstract class FabricaTipoPartido {

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public abstract String obtenerNombre();

    /** Texto del dato que se pide: "Minutos", "Goles", etc. */
    public abstract String obtenerEtiquetaValor();

    /**
     * Realiza la operación de obtener valor por defecto.
     * @return resultado de obtener valor por defecto.
     */
    public abstract int obtenerValorPorDefecto();

    /**
     * Realiza la operación de crear.
     * @param valor valor de valor.
     * @return resultado de crear.
     */
    public abstract TipoPartido crear(int valor) throws DOPOSoccerException;
}
