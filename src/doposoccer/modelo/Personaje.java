package doposoccer.modelo;

import java.io.Serializable;

/**
 * Personaje que representa a un pais. Cada uno tiene su propio poder.
 * Para agregar un personaje nuevo se crea una subclase.
 */
public abstract class Personaje implements Serializable {

    private String pais;
    private int colorRgb;

    /**
     * Construye una instancia de Personaje.
     * @param pais valor de pais.
     * @param colorRgb valor de color rgb.
     */
    protected Personaje(String pais, int colorRgb) {
        this.pais = pais;
        this.colorRgb = colorRgb;
    }

    /**
     * Realiza la operación de obtener pais.
     * @return resultado de obtener pais.
     */
    public String obtenerPais() {
        return pais;
    }

    /**
     * Realiza la operación de obtener color rgb.
     * @return resultado de obtener color rgb.
     */
    public int obtenerColorRgb() {
        return colorRgb;
    }

    /** Crea el poder que este personaje recibe al activar su barra. */
    public abstract Poder crearPoder();
}
