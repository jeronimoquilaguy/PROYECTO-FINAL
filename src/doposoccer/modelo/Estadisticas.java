package doposoccer.modelo;

/** Niveles (1 a 10) de las cuatro estadisticas de un jugador. */
public class Estadisticas {

    public static final int NIVEL_MINIMO = 1;
    public static final int NIVEL_MAXIMO = 10;
    public static final int PRECIO_BASE = 500;
    public static final double VIDA_BASE = 100;

    private int[] niveles;

    public Estadisticas() {
        niveles = new int[TipoEstadistica.values().length];
        for (int i = 0; i < niveles.length; i++) {
            niveles[i] = NIVEL_MINIMO;
        }
    }

    public int obtenerNivel(TipoEstadistica tipo) {
        return niveles[tipo.ordinal()];
    }

    /** Cada punto por encima de 1 suma 10% sobre el valor base. */
    public double obtenerFactor(TipoEstadistica tipo) {
        return 1 + 0.10 * (obtenerNivel(tipo) - 1);
    }

    public double obtenerMultiplicadorKick() {
        return obtenerFactor(TipoEstadistica.KICK);
    }

    public double obtenerVidaMaxima() {
        return VIDA_BASE * obtenerFactor(TipoEstadistica.SURVIVAL);
    }

   
    public int obtenerPrecioMejora(TipoEstadistica tipo) throws DOPOSoccerException {
        int nivel = obtenerNivel(tipo);
        if (nivel >= NIVEL_MAXIMO) {
            throw new DOPOSoccerException(DOPOSoccerException.NIVEL_MAXIMO);
        }
        return (int) (PRECIO_BASE * Math.pow(2, nivel - 1));
    }

    public boolean estaEnMaximo(TipoEstadistica tipo) {
        return obtenerNivel(tipo) >= NIVEL_MAXIMO;
    }

    public void subirNivel(TipoEstadistica tipo) throws DOPOSoccerException {
        if (estaEnMaximo(tipo)) {
            throw new DOPOSoccerException(DOPOSoccerException.NIVEL_MAXIMO);
        }
        niveles[tipo.ordinal()]++;
    }
}
