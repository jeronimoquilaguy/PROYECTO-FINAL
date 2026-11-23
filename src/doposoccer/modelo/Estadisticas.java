package doposoccer.modelo;

import java.io.Serializable;

/** Niveles (1 a 10) de las cuatro estadisticas de un jugador. */
public class Estadisticas implements Serializable {

    public static final int NIVEL_MINIMO = 1;
    public static final int NIVEL_MAXIMO = 10;
    public static final int PRECIO_BASE = 500;
    public static final double VIDA_BASE = 100;

    private int[] niveles;

    /**
     * Construye una instancia de Estadisticas.
     */
    public Estadisticas() {
        niveles = new int[TipoEstadistica.values().length];
        for (int i = 0; i < niveles.length; i++) {
            niveles[i] = NIVEL_MINIMO;
        }
    }

    /**
     * Realiza la operación de obtener nivel.
     * @param tipo valor de tipo.
     * @return resultado de obtener nivel.
     */
    public int obtenerNivel(TipoEstadistica tipo) {
        return niveles[tipo.ordinal()];
    }

    /** Cada punto por encima de 1 suma 10% sobre el valor base. */
    public double obtenerFactor(TipoEstadistica tipo) {
        return 1 + 0.10 * (obtenerNivel(tipo) - 1);
    }

    /**
     * Realiza la operación de obtener multiplicador kick.
     * @return resultado de obtener multiplicador kick.
     */
    public double obtenerMultiplicadorKick() {
        return obtenerFactor(TipoEstadistica.KICK);
    }

    /**
     * Realiza la operación de obtener vida maxima.
     * @return resultado de obtener vida maxima.
     */
    public double obtenerVidaMaxima() {
        return VIDA_BASE * obtenerFactor(TipoEstadistica.SURVIVAL);
    }

    /** El precio se duplica con cada mejora: 500, 1000, 2000... */
    public int obtenerPrecioMejora(TipoEstadistica tipo) throws DOPOSoccerException {
        int nivel = obtenerNivel(tipo);
        if (nivel >= NIVEL_MAXIMO) {
            throw new DOPOSoccerException(DOPOSoccerException.NIVEL_MAXIMO);
        }
        return (int) (PRECIO_BASE * Math.pow(2, nivel - 1));
    }

    /**
     * Realiza la operación de esta en maximo.
     * @param tipo valor de tipo.
     * @return resultado de esta en maximo.
     */
    public boolean estaEnMaximo(TipoEstadistica tipo) {
        return obtenerNivel(tipo) >= NIVEL_MAXIMO;
    }

    /**
     * Realiza la operación de subir nivel.
     * @param tipo valor de tipo.
     */
    public void subirNivel(TipoEstadistica tipo) throws DOPOSoccerException {
        if (estaEnMaximo(tipo)) {
            throw new DOPOSoccerException(DOPOSoccerException.NIVEL_MAXIMO);
        }
        niveles[tipo.ordinal()]++;
    }
}
