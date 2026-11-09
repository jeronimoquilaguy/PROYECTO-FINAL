package doposoccer.modelo;

public class FabricaPorGoles extends FabricaTipoPartido {

    public String obtenerNombre() {
        return "Por goles";
    }

    public String obtenerEtiquetaValor() {
        return "Goles para ganar";
    }

    public int obtenerValorPorDefecto() {
        return 5;
    }

    public TipoPartido crear(int valor) throws DOPOSoccerException {
        return new PorGoles(valor);
    }
}
