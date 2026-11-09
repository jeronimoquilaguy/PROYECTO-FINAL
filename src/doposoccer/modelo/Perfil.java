package doposoccer.modelo;

/** Progreso de un jugador humano: puntos acumulados y estadisticas. */
public class Perfil {

    private String nombre;
    private int puntos;
    private Estadisticas estadisticas;

    public Perfil(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
        this.estadisticas = new Estadisticas();
    }

    public String obtenerNombre() {
        return nombre;
    }

    public int obtenerPuntos() {
        return puntos;
    }

    public Estadisticas obtenerEstadisticas() {
        return estadisticas;
    }

    public void sumarPuntos(int cantidad) {
        puntos += cantidad;
    }

    public void mejorar(TipoEstadistica tipo) throws DOPOSoccerException {
        int precio = estadisticas.obtenerPrecioMejora(tipo);
        if (puntos < precio) {
            throw new DOPOSoccerException("Puntos insuficientes para mejorar " + tipo.obtenerNombre()
                    + ": cuesta " + precio + " y usted tiene " + puntos + ".");
        }
        estadisticas.subirNivel(tipo);
        puntos -= precio;
    }
}
