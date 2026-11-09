package doposoccer.modelo;

import java.util.Set;

/** Un jugador dentro de una partida: personaje, vida, barra de poder, efectos y movimiento. */
public class Jugador extends Entidad {

    private static final double TIEMPO_CARGA_PODER = 10.0;
    private static final double BONO_GOL_RECIBIDO = 0.25;
    private static final double ENFRIAMIENTO_PATADA = 0.5;
    private static final double DURACION_PATADA = 0.2;
    private static final double DANO_BASE_PATADA = 10;

    private Lado lado;
    private Personaje personaje;
    private Perfil perfil;
    private ControlJugador control;
    private Cancha cancha;

    private double vida;
    private double vidaMaxima;
    private double barraPoder;
    private Poder poderActivo;
    private Efecto efecto;

    private int intencionX;
    private boolean enSuelo;
    private boolean patadaPendiente;
    private double enfriamientoPatada;
    private double animacionPatada;

    public Jugador(Lado lado, Personaje personaje, Perfil perfil, ControlJugador control, Cancha cancha) {
        this.lado = lado;
        this.personaje = personaje;
        this.perfil = perfil;
        this.control = control;
        this.cancha = cancha;
        this.ancho = 60 * cancha.obtenerEscala();
        this.alto = 90 * cancha.obtenerEscala();
        this.vidaMaxima = perfil.obtenerEstadisticas().obtenerVidaMaxima();
        this.vida = vidaMaxima;
        this.barraPoder = 0;
        reiniciarPosicion(false);
    }

    // ---------- Acciones ----------

    /** Recibe lo que el control pidio en este instante. */
    public void ejecutarAcciones(Set<Accion> acciones) {
        intencionX = 0;
        if (acciones.contains(Accion.IZQUIERDA)) {
            intencionX = intencionX - 1;
        }
        if (acciones.contains(Accion.DERECHA)) {
            intencionX = intencionX + 1;
        }
        if (acciones.contains(Accion.SALTAR)) {
            saltar();
        }
        if (acciones.contains(Accion.PATEAR)) {
            patear();
        }
        if (acciones.contains(Accion.USAR_PODER)) {
            usarPoder();
        }
    }

    public void saltar() {
        if (efecto != null && !efecto.permiteSaltar()) {
            return;
        }
        if (!enSuelo) {
            return;
        }
        velY = -obtenerVelocidadSalto();
        enSuelo = false;
    }

    public void patear() {
        if (efecto != null && !efecto.permitePatear()) {
            return;
        }
        if (enfriamientoPatada > 0) {
            return;
        }
        patadaPendiente = true;
        enfriamientoPatada = ENFRIAMIENTO_PATADA;
        animacionPatada = DURACION_PATADA;
    }

    /** Activa el poder si la barra esta llena y no se tiene otro poder activo. */
    public void usarPoder() {
        if (efecto != null && !efecto.permitePoder()) {
            return;
        }
        if (barraPoder < 1.0) {
            return;
        }
        if (poderActivo != null) {
            return;
        }
        barraPoder = 0;
        poderActivo = personaje.crearPoder();
    }

    /** Ejecuta el tiro de poder sobre el balon y el aura desaparece. */
    public void lanzarPoder(Balon balon) {
        poderActivo.lanzar(balon, lado.obtenerDireccionFrente(), cancha);
        balon.iniciarTiroPoder(poderActivo, this);
        poderActivo = null;
    }

    // ---------- Actualizacion ----------

    public void actualizar(double dt) {
        actualizarEfecto(dt);
        barraPoder = Math.min(1.0, barraPoder + dt / TIEMPO_CARGA_PODER);
        if (enfriamientoPatada > 0) {
            enfriamientoPatada = enfriamientoPatada - dt;
        }
        if (animacionPatada > 0) {
            animacionPatada = animacionPatada - dt;
        }

        if (efecto == null || efecto.permiteMover()) {
            double factor = 1.0;
            if (efecto != null) {
                factor = efecto.obtenerFactorVelocidad();
            }
            velX = intencionX * obtenerVelocidad() * factor;
        } else if (enSuelo) {
            velX = velX * Math.max(0, 1 - 3 * dt);
        }

        aplicarGravedad(cancha.obtenerGravedad(), dt);
        x = x + velX * dt;
        y = y + velY * dt;

        if (x < 0) {
            x = 0;
            velX = 0;
        }
        double maximoX = cancha.obtenerAncho() - ancho;
        if (x > maximoX) {
            x = maximoX;
            velX = 0;
        }
        if (y < 0) {
            y = 0;
            velY = 0;
        }
        double suelo = cancha.obtenerSuelo();
        if (y + alto >= suelo) {
            y = suelo - alto;
            velY = 0;
            enSuelo = true;
        } else {
            enSuelo = false;
        }
        intencionX = 0;
    }

    private void actualizarEfecto(double dt) {
        if (efecto == null) {
            return;
        }
        efecto.actualizar(dt);
        if (efecto.haTerminado()) {
            Efecto terminado = efecto;
            efecto = null;
            terminado.alTerminar(this);
        }
    }

    // ---------- Dano y efectos ----------

    public void recibirDano(double dano) {
        vida = Math.max(0, vida - dano);
        if (vida == 0 && !estaAturdido()) {
            aplicarEfecto(new Aturdimiento());
        }
    }

    /** Un nuevo efecto reemplaza al anterior. Un jugador aturdido no recibe otros efectos. */
    public void aplicarEfecto(Efecto nuevo) {
        if (estaAturdido() && !nuevo.esAturdimiento()) {
            return;
        }
        efecto = nuevo;
        nuevo.alAplicar(this);
    }

    /** El tiro de poder golpeo a este jugador: aplica efecto y dano. */
    public void recibirGolpePoder(Poder poder, Jugador atacante) {
        double multiplicador = atacante.obtenerPerfil().obtenerEstadisticas().obtenerMultiplicadorKick();
        double dano = poder.obtenerDanoTiro() * multiplicador;
        aplicarEfecto(poder.crearEfecto(atacante.obtenerLado().obtenerDireccionFrente()));
        recibirDano(dano);
    }

    /** Dano de una patada normal que alcanza al rival. */
    public double obtenerDanoPatada() {
        return DANO_BASE_PATADA * perfil.obtenerEstadisticas().obtenerMultiplicadorKick();
    }

    public void recibirGol() {
        barraPoder = Math.min(1.0, barraPoder + BONO_GOL_RECIBIDO);
    }

    public void empujar(int direccion) {
        velX = direccion * 700 * cancha.obtenerEscala();
    }

    public void lanzarAlAire() {
        velY = -600 * cancha.obtenerEscala();
        enSuelo = false;
    }

    public void detener() {
        velX = 0;
    }

    public void restaurarVida() {
        vida = vidaMaxima;
    }

    // ---------- Posicion ----------

    /** Vuelve al lado de la cancha que defiende. Si enAire es true, cae desde arriba. */
    public void reiniciarPosicion(boolean enAire) {
        double nuevaX;
        if (lado == Lado.IZQUIERDO) {
            nuevaX = cancha.obtenerAncho() * 0.2;
        } else {
            nuevaX = cancha.obtenerAncho() * 0.8 - ancho;
        }
        double nuevaY = cancha.obtenerSuelo() - alto;
        if (enAire) {
            nuevaY = nuevaY - 120 * cancha.obtenerEscala();
        }
        fijarPosicion(nuevaX, nuevaY);
        fijarVelocidad(0, 0);
        enSuelo = !enAire;
        patadaPendiente = false;
        intencionX = 0;
    }

    /** Rectangulo delante del jugador donde llega su pie: {x, y, ancho, alto}. */
    public double[] obtenerZonaPie() {
        double alcance = 45 * cancha.obtenerEscala();
        double zonaX;
        if (lado.obtenerDireccionFrente() == 1) {
            zonaX = x + ancho;
        } else {
            zonaX = x - alcance;
        }
        return new double[] { zonaX, y + alto * 0.4, alcance, alto * 0.6 };
    }

    public boolean consumirPatadaPendiente() {
        boolean pendiente = patadaPendiente;
        patadaPendiente = false;
        return pendiente;
    }

    // ---------- Datos derivados de las estadisticas ----------

    private double obtenerVelocidad() {
        double base = 320 * cancha.obtenerEscala();
        return base * perfil.obtenerEstadisticas().obtenerFactor(TipoEstadistica.SPEED);
    }

    private double obtenerVelocidadSalto() {
        double altura = 170 * cancha.obtenerEscala()
                * perfil.obtenerEstadisticas().obtenerFactor(TipoEstadistica.JUMP);
        return Math.sqrt(2 * cancha.obtenerGravedad() * altura);
    }

    // ---------- Consultas ----------

    public Lado obtenerLado() {
        return lado;
    }

    public Personaje obtenerPersonaje() {
        return personaje;
    }

    public Perfil obtenerPerfil() {
        return perfil;
    }

    public ControlJugador obtenerControl() {
        return control;
    }

    public double obtenerVida() {
        return vida;
    }

    public double obtenerVidaMaxima() {
        return vidaMaxima;
    }

    public double obtenerBarraPoder() {
        return barraPoder;
    }

    public Poder obtenerPoderActivo() {
        return poderActivo;
    }

    public boolean tienePoderActivo() {
        return poderActivo != null;
    }

    public Efecto obtenerEfecto() {
        return efecto;
    }

    public boolean estaAturdido() {
        return efecto != null && efecto.esAturdimiento();
    }

    public boolean estaConfundido() {
        return efecto != null && efecto.esConfusion();
    }

    public boolean estaPateando() {
        return animacionPatada > 0;
    }

    public boolean estaEnSuelo() {
        return enSuelo;
    }
}
