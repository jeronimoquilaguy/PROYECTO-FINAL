package doposoccer.modelo;


public class FabricaPorGoles extends FabricaTipoPartido {

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Por goles";
    }

    /**
     * Realiza la operación de obtener etiqueta valor.
     * @return resultado de obtener etiqueta valor.
     */
    public String obtenerEtiquetaValor() {
        return "Goles para ganar";
    }

    /**
     * Realiza la operación de obtener valor por defecto.
     * @return resultado de obtener valor por defecto.
     */
    public int obtenerValorPorDefecto() {
        return 5;
    }

    /**
     * Realiza la operación de crear.
     * @param valor valor de valor.
     * @return resultado de crear.
     */
    public TipoPartido crear(int valor) throws DOPOSoccerException {
        return new PorGoles(valor);
    }
}
