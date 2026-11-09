package doposoccer.modelo;

public class FabricaPorTiempo extends FabricaTipoPartido {

    public String obtenerNombre() {
        return "Por tiempo";
    }

    public String obtenerEtiquetaValor() {
        return "Minutos";
    }

    public int obtenerValorPorDefecto() {
        return 3;
    }

    public TipoPartido crear(int valor) throws DOPOSoccerException {
        return new PorTiempo(valor);
    }
}
