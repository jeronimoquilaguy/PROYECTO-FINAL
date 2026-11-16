package doposoccer.modelo;

public class FabricaPorTiempo extends FabricaTipoPartido {

    /**
     * Realiza la operación de obtener nombre.
     * @return resultado de obtener nombre.
     */
    public String obtenerNombre() {
        return "Por tiempo";
    }

    /**
     * Realiza la operación de obtener etiqueta valor.
     * @return resultado de obtener etiqueta valor.
     */
    public String obtenerEtiquetaValor() {
        return "Minutos";
    }

    /**
     * Realiza la operación de obtener valor por defecto.
     * @return resultado de obtener valor por defecto.
     */
    public int obtenerValorPorDefecto() {
        return 3;
    }

    /**
     * Realiza la operación de crear.
     * @param valor valor de valor.
     * @return resultado de crear.
     */
    public TipoPartido crear(int valor) throws DOPOSoccerException {
        return new PorTiempo(valor);
    }
}
