package doposoccer.modelo;

import java.io.Serializable;

import java.util.List;

/**
 * Fachada del dominio: es lo unico que el controlador necesita conocer del modelo.
 * Los catalogos (personajes, modalidades, tipos de partido) llegan por el constructor,
 * asi se pueden agregar opciones nuevas sin modificar esta clase.
 */
public class DOPOSoccer implements Serializable {

    private List<Personaje> personajes;
    private List<Modalidad> modalidades;
    private List<FabricaTipoPartido> tiposPartido;
    private Perfil[] perfiles;
    private ControlJugador[] controles;
    private Partida partida;

    /**
     * Construye una instancia de DOPOSoccer.
     * @param personajes valor de personajes.
     * @param modalidades valor de modalidades.
     * @param tiposPartido valor de tipos partido.
     */
    public DOPOSoccer(List<Personaje> personajes, List<Modalidad> modalidades,
                      List<FabricaTipoPartido> tiposPartido) {
        this.personajes = personajes;
        this.modalidades = modalidades;
        this.tiposPartido = tiposPartido;
        this.perfiles = new Perfil[] { new Perfil("Jugador 1"), new Perfil("Jugador 2") };
    }

    /**
     * Realiza la operación de nueva partida.
     * @param modalidad valor de modalidad.
     * @param resolucion valor de resolucion.
     * @param personaje1 valor de personaje1.
     * @param personaje2 valor de personaje2.
     * @param fabrica valor de fabrica.
     * @param valor valor de valor.
     */
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

    /**
     * Realiza la operación de actualizar.
     * @param dt valor de dt.
     */
    public void actualizar(double dt) {
        if (partida != null) {
            partida.actualizar(dt);
        }
    }

    /**
     * Realiza la operación de pausar.
     */
    public void pausar() {
        if (partida != null) {
            partida.pausar();
        }
    }

    /**
     * Realiza la operación de reanudar.
     */
    public void reanudar() {
        if (partida != null) {
            partida.reanudar();
        }
    }

    /**
     * Realiza la operación de terminar partida.
     */
    public void terminarPartida() {
        if (partida != null) {
            partida.terminar();
        }
    }

    /**
     * Realiza la operación de presionar accion.
     * @param numeroJugador valor de numero jugador.
     * @param accion valor de accion.
     */
    public void presionarAccion(int numeroJugador, Accion accion) {
        if (partida != null) {
            controles[numeroJugador].presionar(accion);
        }
    }

    /**
     * Realiza la operación de soltar accion.
     * @param numeroJugador valor de numero jugador.
     * @param accion valor de accion.
     */
    public void soltarAccion(int numeroJugador, Accion accion) {
        if (partida != null) {
            controles[numeroJugador].soltar(accion);
        }
    }

    /**
     * Realiza la operación de mejorar estadistica.
     * @param numeroJugador valor de numero jugador.
     * @param tipo valor de tipo.
     */
    public void mejorarEstadistica(int numeroJugador, TipoEstadistica tipo) throws DOPOSoccerException {
        perfiles[numeroJugador].mejorar(tipo);
    }

    /** Limpia las teclas retenidas cuando se abre una partida guardada. */
    public void limpiarTeclas() {
        if (controles == null) return;
        for (ControlJugador control : controles) {
            if (control instanceof ControlHumano) ((ControlHumano) control).limpiar();
        }
    }

    /** Restaura el progreso persistente antes de iniciar una nueva partida. */
    public void restaurarPerfiles(Perfil[] perfilesGuardados) {
        if (perfilesGuardados == null || perfilesGuardados.length != 2
                || perfilesGuardados[0] == null || perfilesGuardados[1] == null) {
            throw new IllegalArgumentException("Se necesitan dos perfiles validos.");
        }
        perfiles = perfilesGuardados;
    }

    /** Perfiles que se guardan al terminar y al comprar mejoras. */
    public Perfil[] obtenerPerfiles() {
        return perfiles.clone();
    }

    /**
     * Realiza la operación de obtener partida.
     * @return resultado de obtener partida.
     */
    public Partida obtenerPartida() {
        return partida;
    }

    /**
     * Realiza la operación de obtener perfil.
     * @param numeroJugador valor de numero jugador.
     * @return resultado de obtener perfil.
     */
    public Perfil obtenerPerfil(int numeroJugador) {
        return perfiles[numeroJugador];
    }

    /**
     * Realiza la operación de obtener personajes.
     * @return resultado de obtener personajes.
     */
    public List<Personaje> obtenerPersonajes() {
        return personajes;
    }

    /**
     * Realiza la operación de obtener modalidades.
     * @return resultado de obtener modalidades.
     */
    public List<Modalidad> obtenerModalidades() {
        return modalidades;
    }

    /**
     * Realiza la operación de obtener tipos partido.
     * @return resultado de obtener tipos partido.
     */
    public List<FabricaTipoPartido> obtenerTiposPartido() {
        return tiposPartido;
    }
}
