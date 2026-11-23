package doposoccer.modelo;

import java.io.Serializable;

import java.util.Set;

/**
 * Quien manda las acciones de un jugador: una persona (teclado) o, mas adelante, una maquina.
 */
public interface ControlJugador extends Serializable {

    Set<Accion> obtenerAcciones(Jugador jugador, Partida partida);

    void presionar(Accion accion);

    void soltar(Accion accion);
}
