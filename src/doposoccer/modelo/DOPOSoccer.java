package doposoccer.modelo;

import java.util.List;

/**
 * Fachada del dominio: es lo unico que el controlador necesita conocer del modelo.
 * Los catalogos (personajes, modalidades, tipos de partido) llegan por el constructor,
 * asi se pueden agregar opciones nuevas sin modificar esta clase.
 */
public class DOPOSoccer {

    private List<Personaje> personajes;
    private List<Modalidad> modalidades;
    private List<FabricaTipoPartido> tiposPartido;
    private Perfil[] perfiles;
    private ControlJugador[] controles;
    private Partida partida;

    public DOPOSoccer(List<Personaje> personajes, List<Modalidad> modalidades,
                      List<FabricaTipoPartido> tiposPartido) {
        this.personajes = personajes;
        this.modalidades = modalidades;
        this.tiposPartido = tiposPartido;
        this.perfiles = new Perfil[] { new Perfil("Jugador 1"), new Perfil("Jugador 2") };
    }

    public void nuevaPartida(Modalidad modalidad, ResolucionCancha resolucion, Personaje personaje1,
                             Personaje personaje2, FabricaTipoPartido fabrica, int valor)
            throws DOPOSoccerException {
        if (modalidad == null || resolucion == null || personaje1 == null || personaje2 == null
                || fabrica == null) {
            throw new DOPOSoccerException(DOPOSoccerException.DATOS_INCOMPLETOS);
        }
        TipoPartido tipo = fabrica.crear(valor);
        Cancha cancha = new Cancha(resolucion);
        controles = new ControlJugador[] { modalidad.crearControl(0), modalidad.crearControl(1) };
        Jugador izquierdo = new Jugador(Lado.IZQUIERDO, personaje1, perfiles[0], controles[0], cancha);
        Jugador derecho = new Jugador(Lado.DERECHO, personaje2, perfiles[1], controles[1], cancha);
        partida = new Partida(cancha, izquierdo, derecho, tipo);
    }

    public void actualizar(double dt) {
        if (partida != null) {
            partida.actualizar(dt);
        }
    }

    public void pausar() {
        if (partida != null) {
            partida.pausar();
        }
    }

    public void reanudar() {
        if (partida != null) {
            partida.reanudar();
        }
    }

    public void terminarPartida() {
        if (partida != null) {
            partida.terminar();
        }
    }

    public void presionarAccion(int numeroJugador, Accion accion) {
        if (partida != null) {
            controles[numeroJugador].presionar(accion);
        }
    }

    public void soltarAccion(int numeroJugador, Accion accion) {
        if (partida != null) {
            controles[numeroJugador].soltar(accion);
        }
    }

    public void mejorarEstadistica(int numeroJugador, TipoEstadistica tipo) throws DOPOSoccerException {
        perfiles[numeroJugador].mejorar(tipo);
    }

    public Partida obtenerPartida() {
        return partida;
    }

    public Perfil obtenerPerfil(int numeroJugador) {
        return perfiles[numeroJugador];
    }

    public List<Personaje> obtenerPersonajes() {
        return personajes;
    }

    public List<Modalidad> obtenerModalidades() {
        return modalidades;
    }

    public List<FabricaTipoPartido> obtenerTiposPartido() {
        return tiposPartido;
    }
}
