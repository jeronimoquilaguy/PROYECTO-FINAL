package doposoccer.modelo;

/** Excepcion propia del juego. Todos los errores esperados del dominio usan esta clase. */
public class DOPOSoccerException extends Exception {

    public static final String PARTIDA_NO_CREADA = "No hay una partida en curso. Inicie una partida primero.";
    public static final String DATOS_INCOMPLETOS = "Faltan datos de configuracion: seleccione modalidad, cancha, personajes y tipo de partida.";
    public static final String MINUTOS_INVALIDOS = "Los minutos del partido deben ser un numero mayor que cero.";
    public static final String GOLES_INVALIDOS = "El numero de goles para ganar debe ser mayor que cero.";
    public static final String NIVEL_MAXIMO = "La estadistica ya esta en el nivel maximo (10).";

    public DOPOSoccerException(String mensaje) {
        super(mensaje);
    }

    public DOPOSoccerException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
