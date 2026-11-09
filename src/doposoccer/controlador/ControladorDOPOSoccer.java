package doposoccer.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Timer;

import doposoccer.modelo.Accion;
import doposoccer.modelo.DOPOSoccer;
import doposoccer.modelo.DOPOSoccerException;
import doposoccer.modelo.Estadisticas;
import doposoccer.modelo.EstadoPartida;
import doposoccer.modelo.FabricaTipoPartido;
import doposoccer.modelo.Jugador;
import doposoccer.modelo.Partida;
import doposoccer.modelo.Perfil;
import doposoccer.modelo.RegistroErrores;
import doposoccer.modelo.ResolucionCancha;
import doposoccer.modelo.TipoEstadistica;
import doposoccer.vista.VentanaPrincipal;

/**
 * Controlador (MVC): conecta la vista con el modelo.
 * - Recibe lo que el usuario hace en la vista (botones y teclado).
 * - Le pide cosas al modelo.
 * - Le dice a la vista cuando debe redibujarse.
 */
public class ControladorDOPOSoccer implements ActionListener, KeyListener {

    private static final int RETARDO_MS = 16;

    /** Codigo propio para distinguir el Ctrl derecho del Ctrl izquierdo. */
    private static final int CTRL_DERECHO = 100017;

    private static final Accion[] ACCIONES = {
        Accion.IZQUIERDA, Accion.DERECHA, Accion.SALTAR, Accion.PATEAR, Accion.USAR_PODER
    };

    /** Teclas de cada jugador, en el mismo orden de ACCIONES. */
    private static final int[][] TECLAS = {
        { KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_Q },
        { KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, CTRL_DERECHO }
    };

    private DOPOSoccer modelo;
    private VentanaPrincipal ventana;
    private Timer reloj;
    private long ultimoTiempo;
    private Set<Integer> teclasPresionadas = new HashSet<Integer>();

    public ControladorDOPOSoccer(DOPOSoccer modelo) {
        this.modelo = modelo;
        this.reloj = new Timer(RETARDO_MS, this);
    }

    public void iniciar() {
        ventana = new VentanaPrincipal(this);
        ventana.mostrarConfiguracion();
        ventana.setVisible(true);
    }

    // ---------- Configuracion ----------

    public void iniciarPartida(int modalidad, int resolucion, int personaje1, int personaje2,
                               int tipo, int valor) {
        try {
            modelo.nuevaPartida(
                    modelo.obtenerModalidades().get(modalidad),
                    ResolucionCancha.values()[resolucion],
                    modelo.obtenerPersonajes().get(personaje1),
                    modelo.obtenerPersonajes().get(personaje2),
                    modelo.obtenerTiposPartido().get(tipo),
                    valor);
            teclasPresionadas.clear();
            ventana.mostrarJuego(modelo.obtenerPartida());
            ultimoTiempo = System.nanoTime();
            reloj.start();
        } catch (DOPOSoccerException e) {
            RegistroErrores.registrar(e);
            ventana.mostrarError(e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            RegistroErrores.registrar(e);
            ventana.mostrarError("Seleccione una opcion valida en cada lista de la configuracion.");
        }
    }

    public void mejorarEstadistica(int jugador, int numeroEstadistica) {
        try {
            modelo.mejorarEstadistica(jugador, TipoEstadistica.values()[numeroEstadistica]);
        } catch (DOPOSoccerException e) {
            RegistroErrores.registrar(e);
            ventana.mostrarError(e.getMessage());
        }
        ventana.actualizarConfiguracion();
    }

    // ---------- Datos para la vista de configuracion ----------

    public String[] obtenerNombresModalidades() {
        String[] nombres = new String[modelo.obtenerModalidades().size()];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = modelo.obtenerModalidades().get(i).obtenerNombre();
        }
        return nombres;
    }

    public String[] obtenerNombresResoluciones() {
        ResolucionCancha[] resoluciones = ResolucionCancha.values();
        String[] nombres = new String[resoluciones.length];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = resoluciones[i].obtenerNombre();
        }
        return nombres;
    }

    public String[] obtenerNombresPersonajes() {
        String[] nombres = new String[modelo.obtenerPersonajes().size()];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = modelo.obtenerPersonajes().get(i).obtenerPais();
        }
        return nombres;
    }

    public String[] obtenerNombresTipos() {
        String[] nombres = new String[modelo.obtenerTiposPartido().size()];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = modelo.obtenerTiposPartido().get(i).obtenerNombre();
        }
        return nombres;
    }

    public String obtenerEtiquetaValor(int tipo) {
        return modelo.obtenerTiposPartido().get(tipo).obtenerEtiquetaValor();
    }

    public int obtenerValorPorDefecto(int tipo) {
        FabricaTipoPartido fabrica = modelo.obtenerTiposPartido().get(tipo);
        return fabrica.obtenerValorPorDefecto();
    }

    public String obtenerTextoPuntos(int jugador) {
        return "Puntos disponibles: " + modelo.obtenerPerfil(jugador).obtenerPuntos();
    }

    public String obtenerTextoMejora(int jugador, int numeroEstadistica) {
        TipoEstadistica tipo = TipoEstadistica.values()[numeroEstadistica];
        Estadisticas estadisticas = modelo.obtenerPerfil(jugador).obtenerEstadisticas();
        String texto = tipo.obtenerNombre() + ": nivel " + estadisticas.obtenerNivel(tipo);
        if (estadisticas.estaEnMaximo(tipo)) {
            return texto + " (maximo)";
        }
        try {
            return texto + "  -  mejorar por " + estadisticas.obtenerPrecioMejora(tipo);
        } catch (DOPOSoccerException e) {
            return texto;
        }
    }

    // ---------- Ciclo del juego ----------

    /** Se llama cada 16 ms mientras hay una partida en curso. */
    public void actionPerformed(ActionEvent e) {
        long ahora = System.nanoTime();
        double dt = (ahora - ultimoTiempo) / 1000000000.0;
        ultimoTiempo = ahora;
        if (dt > 0.05) {
            dt = 0.05;
        }
        modelo.actualizar(dt);
        ventana.refrescarJuego();
        if (modelo.obtenerPartida().obtenerEstado() == EstadoPartida.TERMINADO) {
            finalizarPartida();
        }
    }

    public void pausarOReanudar() {
        Partida partida = modelo.obtenerPartida();
        if (partida == null) {
            return;
        }
        if (partida.obtenerEstado() == EstadoPartida.EN_JUEGO) {
            modelo.pausar();
        } else if (partida.obtenerEstado() == EstadoPartida.PAUSADO) {
            ultimoTiempo = System.nanoTime();
            modelo.reanudar();
        }
        ventana.refrescarJuego();
    }

    public void terminarPartida() {
        modelo.terminarPartida();
        finalizarPartida();
    }

    private void finalizarPartida() {
        reloj.stop();
        ventana.refrescarJuego();
        ventana.mostrarMensaje("Fin de la partida", armarMensajeFinal(modelo.obtenerPartida()));
        ventana.mostrarConfiguracion();
    }

    private String armarMensajeFinal(Partida partida) {
        Jugador[] jugadores = partida.obtenerJugadores();
        String marcador = partida.obtenerMarcador().obtenerGoles(jugadores[0]) + " - "
                + partida.obtenerMarcador().obtenerGoles(jugadores[1]);
        if (partida.obtenerGanador() == null) {
            return "La partida se termino antes de tiempo.\nMarcador: " + marcador
                    + "\nNo se otorgan puntos.";
        }
        Jugador ganador = partida.obtenerGanador();
        String texto = "Ganador: " + ganador.obtenerPerfil().obtenerNombre() + " ("
                + ganador.obtenerPersonaje().obtenerPais() + ")\nMarcador: " + marcador + "\n";
        for (int i = 0; i < jugadores.length; i++) {
            Perfil perfil = jugadores[i].obtenerPerfil();
            texto = texto + "\n" + perfil.obtenerNombre() + ": +" + partida.obtenerPuntosGanados(i)
                    + " puntos (total " + perfil.obtenerPuntos() + ")";
        }
        return texto;
    }

    // ---------- Teclado ----------

    /** Devuelve el codigo de la tecla; el Ctrl derecho tiene un codigo propio. */
    private int obtenerCodigo(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL && e.getKeyLocation() == KeyEvent.KEY_LOCATION_RIGHT) {
            return CTRL_DERECHO;
        }
        return e.getKeyCode();
    }

    public void keyPressed(KeyEvent e) {
        int codigo = obtenerCodigo(e);
        if (codigo == KeyEvent.VK_P) {
            if (!teclasPresionadas.contains(codigo)) {
                teclasPresionadas.add(codigo);
                pausarOReanudar();
            }
            return;
        }
        if (codigo == KeyEvent.VK_ESCAPE) {
            terminarPartida();
            return;
        }
        if (teclasPresionadas.contains(codigo)) {
            return; // ignora la repeticion automatica del teclado
        }
        teclasPresionadas.add(codigo);
        for (int jugador = 0; jugador < TECLAS.length; jugador++) {
            for (int i = 0; i < ACCIONES.length; i++) {
                if (TECLAS[jugador][i] == codigo) {
                    modelo.presionarAccion(jugador, ACCIONES[i]);
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int codigo = obtenerCodigo(e);
        teclasPresionadas.remove(codigo);
        for (int jugador = 0; jugador < TECLAS.length; jugador++) {
            for (int i = 0; i < ACCIONES.length; i++) {
                if (TECLAS[jugador][i] == codigo) {
                    modelo.soltarAccion(jugador, ACCIONES[i]);
                }
            }
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}
