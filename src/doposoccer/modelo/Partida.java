package doposoccer.modelo;

/** Una partida: cancha, balon, dos jugadores, arcos, marcador y reglas de fin. */
public class Partida {

    public static final int PUNTOS_GANADOR = 100;
    public static final int PUNTOS_PERDEDOR = 25;
    private static final double PAUSA_TRAS_GOL = 1.2;

    private Cancha cancha;
    private Balon balon;
    private Jugador[] jugadores;
    private Arco[] arcos;
    private Marcador marcador;
    private TipoPartido tipoPartido;
    private EstadoPartida estado;
    private Jugador ganador;
    private Jugador ultimoAnotador;
    private int[] puntosGanados;
    private double pausaGol;

    /** El jugador 0 defiende el lado izquierdo y el 1 el derecho. */
    public Partida(Cancha cancha, Jugador izquierdo, Jugador derecho, TipoPartido tipoPartido) {
        this.cancha = cancha;
        this.jugadores = new Jugador[] { izquierdo, derecho };
        this.arcos = new Arco[] { new Arco(Lado.IZQUIERDO, cancha), new Arco(Lado.DERECHO, cancha) };
        this.balon = new Balon(cancha);
        this.marcador = new Marcador(izquierdo, derecho);
        this.tipoPartido = tipoPartido;
        this.estado = EstadoPartida.EN_JUEGO;
        this.puntosGanados = new int[2];
        this.pausaGol = 0;
    }

    // ---------- Ciclo del juego ----------

    public void actualizar(double dt) {
        if (estado != EstadoPartida.EN_JUEGO) {
            return;
        }
        if (pausaGol > 0) {
            pausaGol = pausaGol - dt;
            if (pausaGol <= 0) {
                reiniciarPosiciones();
            }
            return;
        }
        tipoPartido.actualizar(dt);
        moverJugadores(dt);
        balon.actualizar(dt);
        resolverChoques();
        resolverPatadas();
        detectarGol();
        verificarFin();
    }

    private void moverJugadores(double dt) {
        for (int i = 0; i < jugadores.length; i++) {
            Jugador j = jugadores[i];
            j.ejecutarAcciones(j.obtenerControl().obtenerAcciones(j, this));
            j.actualizar(dt);
        }
    }

    private void resolverChoques() {
        for (int i = 0; i < jugadores.length; i++) {
            Jugador j = jugadores[i];
            boolean esTiroPropio = balon.tienePoderEnVuelo() && balon.obtenerDueno() == j;
            if (!esTiroPropio) {
                boolean choco = Colisiones.balonContraRectangulo(balon, j.obtenerX(), j.obtenerY(),
                        j.obtenerAncho(), j.obtenerAlto(), j.obtenerVelX(), j.obtenerVelY());
                if (choco) {
                    alTocarBalon(j);
                }
            }
        }
        for (int i = 0; i < arcos.length; i++) {
            Arco a = arcos[i];
            Colisiones.balonContraRectangulo(balon, a.obtenerX(), a.obtenerYTravesano(),
                    a.obtenerAncho(), a.obtenerGrosor(), 0, 0);
        }
    }

    /** Reglas de los poderes cuando un jugador toca el balon. */
    private void alTocarBalon(Jugador j) {
        if (balon.tienePoderEnVuelo()) {
            if (balon.obtenerDueno() != j) {
                j.recibirGolpePoder(balon.obtenerPoder(), balon.obtenerDueno());
                balon.terminarTiroPoder();
            }
        } else if (j.tienePoderActivo()) {
            j.lanzarPoder(balon);
        }
    }

    private void resolverPatadas() {
        for (int i = 0; i < jugadores.length; i++) {
            Jugador j = jugadores[i];
            Jugador rival = jugadores[1 - i];
            if (j.consumirPatadaPendiente()) {
                double[] zona = j.obtenerZonaPie();
                if (balon.intersecta(zona[0], zona[1], zona[2], zona[3])) {
                    // Si la patada toca el balon, solo lo impulsa
                    balon.recibirPatada(j.obtenerLado().obtenerDireccionFrente());
                    alTocarBalon(j);
                } else if (rival.intersecta(zona[0], zona[1], zona[2], zona[3])) {
                    rival.recibirDano(j.obtenerDanoPatada());
                }
            }
        }
    }

    private void detectarGol() {
        for (int k = 0; k < arcos.length; k++) {
            if (arcos[k].contiene(balon)) {
                Jugador anotador = jugadores[1 - k];
                Jugador receptor = jugadores[k];
                marcador.sumarGol(anotador);
                receptor.recibirGol();
                balon.terminarTiroPoder();
                ultimoAnotador = anotador;
                pausaGol = PAUSA_TRAS_GOL;
                return;
            }
        }
    }

    private void verificarFin() {
        if (tipoPartido.haTerminado(marcador)) {
            finalizar(tipoPartido.obtenerGanador(marcador));
        }
    }

    /** Fin normal: se reparten los puntos (100 al ganador, 25 al perdedor). */
    private void finalizar(Jugador elGanador) {
        ganador = elGanador;
        estado = EstadoPartida.TERMINADO;
        for (int i = 0; i < jugadores.length; i++) {
            int puntos = PUNTOS_PERDEDOR;
            if (jugadores[i] == ganador) {
                puntos = PUNTOS_GANADOR;
            }
            puntosGanados[i] = puntos;
            jugadores[i].obtenerPerfil().sumarPuntos(puntos);
        }
    }

    private void reiniciarPosiciones() {
        balon.reiniciarEnCentro();
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i].reiniciarPosicion(true);
        }
    }

    // ---------- Control de la partida ----------

    public void pausar() {
        if (estado == EstadoPartida.EN_JUEGO) {
            estado = EstadoPartida.PAUSADO;
        }
    }

    public void reanudar() {
        if (estado == EstadoPartida.PAUSADO) {
            estado = EstadoPartida.EN_JUEGO;
        }
    }

    /** Terminar antes de tiempo: no hay ganador ni se otorgan puntos. */
    public void terminar() {
        if (estado != EstadoPartida.TERMINADO) {
            estado = EstadoPartida.TERMINADO;
            ganador = null;
        }
    }

    // ---------- Consultas ----------

    public Cancha obtenerCancha() {
        return cancha;
    }

    public Balon obtenerBalon() {
        return balon;
    }

    public Jugador[] obtenerJugadores() {
        return jugadores;
    }

    public Arco[] obtenerArcos() {
        return arcos;
    }

    public Marcador obtenerMarcador() {
        return marcador;
    }

    public TipoPartido obtenerTipoPartido() {
        return tipoPartido;
    }

    public EstadoPartida obtenerEstado() {
        return estado;
    }

    /** Ganador de la partida, o null si no termino o se termino antes de tiempo. */
    public Jugador obtenerGanador() {
        return ganador;
    }

    public int obtenerPuntosGanados(int numeroJugador) {
        return puntosGanados[numeroJugador];
    }

    public boolean hayCelebracionGol() {
        return pausaGol > 0;
    }

    public Jugador obtenerUltimoAnotador() {
        return ultimoAnotador;
    }
}
