package doposoccer.modelo;

/**
 * Personaje que representa a un pais. Cada uno tiene su propio poder.
 * Para agregar un personaje nuevo se crea una subclase.
 */
public abstract class Personaje {

    private String pais;
    private int colorRgb;

    protected Personaje(String pais, int colorRgb) {
        this.pais = pais;
        this.colorRgb = colorRgb;
    }

    public String obtenerPais() {
        return pais;
    }

    public int obtenerColorRgb() {
        return colorRgb;
    }

    /** Crea el poder que este personaje recibe al activar su barra. */
    public abstract Poder crearPoder();
}
