package doposoccer.controlador;

import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.Timer;
import javax.swing.JFileChooser;

import doposoccer.modelo.Accion;
import doposoccer.modelo.DOPOSoccer;
import doposoccer.modelo.DOPOSoccerException;
import doposoccer.modelo.Estadisticas;
import doposoccer.modelo.EstadoPartida;
import doposoccer.modelo.FabricaTipoPartido;
import doposoccer.modelo.Jugador;
import doposoccer.modelo.JugadorVsMaquina;
import doposoccer.modelo.PerfilMaquina;
import doposoccer.modelo.Partida;
import doposoccer.modelo.Perfil;
import doposoccer.modelo.RegistroErrores;
import doposoccer.modelo.ResolucionCancha;
import doposoccer.modelo.TipoEstadistica;
import doposoccer.persistencia.AlmacenamientoPartida;
import doposoccer.persistencia.AlmacenamientoProgreso;
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

    /**
     * Construye una instancia de ControladorDOPOSoccer.
     * @param modelo valor de modelo.
     */
    public ControladorDOPOSoccer(DOPOSoccer modelo) {
        this.modelo = modelo;
        this.reloj = new Timer(RETARDO_MS, this);
    }

    /**
     * Realiza la operación de iniciar.
     */
    public void iniciar() {
        ventana = new VentanaPrincipal(this);
        ventana.mostrarConfiguracion();
        ventana.setVisible(true);
    }

    // ---------- Configuracion ----------

    /**
     * Realiza la operación de iniciar partida.
     * @param modalidad valor de modalidad.
     * @param resolucion valor de resolucion.
     * @param personaje1 valor de personaje1.
     * @param personaje2 valor de personaje2.
     * @param tipo valor de tipo.
     * @param valor valor de valor.
     */
    public void iniciarPartida(int modalidad, int resolucion, int personaje1, int personaje2,
                               int tipo, int valor) {
        iniciarPartida(modalidad, resolucion, personaje1, personaje2, tipo, valor, 0);
    }

    /** Inicia la partida con el perfil de maquina seleccionado, si corresponde. */
    public void iniciarPartida(int modalidad, int resolucion, int personaje1, int personaje2,
                               int tipo, int valor, int perfilMaquina) {
        try {
            doposoccer.modelo.Modalidad modalidadElegida = modelo.obtenerModalidades().get(modalidad);
            if (modalidadElegida instanceof JugadorVsMaquina) {
                PerfilMaquina perfil = PerfilMaquina.values()[perfilMaquina];
                modalidadElegida = new JugadorVsMaquina(perfil);
            }
            modelo.nuevaPartida(
                    modalidadElegida,
                    ResolucionCancha.values()[resolucion],
                    modelo.obtenerPersonajes().get(personaje1),
                    modelo.obtenerPersonajes().get(personaje2),
                    modelo.obtenerTiposPartido().get(tipo),
                    valor);
            reloj.stop();
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

    /**
     * Realiza la operación de mejorar estadistica.
     * @param jugador valor de jugador.
     * @param numeroEstadistica valor de numero estadistica.
     */
    public void mejorarEstadistica(int jugador, int numeroEstadistica) {
        try {
            modelo.mejorarEstadistica(jugador, TipoEstadistica.values()[numeroEstadistica]);
        } catch (DOPOSoccerException e) {
            RegistroErrores.registrar(e);
            ventana.mostrarError(e.getMessage());
        }
        guardarProgreso();
        ventana.actualizarConfiguracion();
    }

    // ---------- Datos para la vista de configuracion ----------

    /**
     * Realiza la operación de obtener nombres modalidades.
     * @return resultado de obtener nombres modalidades.
     */
    public String[] obtenerNombresModalidades() {
        String[] nombres = new String[modelo.obtenerModalidades().size()];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = modelo.obtenerModalidades().get(i).obtenerNombre();
        }
        return nombres;
    }

    /**
     * Realiza la operación de obtener nombres resoluciones.
     * @return resultado de obtener nombres resoluciones.
     */
    public String[] obtenerNombresResoluciones() {
        ResolucionCancha[] resoluciones = ResolucionCancha.values();
        String[] nombres = new String[resoluciones.length];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = resoluciones[i].obtenerNombre();
        }
        return nombres;
    }

    /**
     * Realiza la operación de obtener nombres personajes.
     * @return resultado de obtener nombres personajes.
     */
    public String[] obtenerNombresPersonajes() {
        String[] nombres = new String[modelo.obtenerPersonajes().size()];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = modelo.obtenerPersonajes().get(i).obtenerPais();
        }
        return nombres;
    }

    /**
     * Realiza la operación de obtener nombres tipos.
     * @return resultado de obtener nombres tipos.
     */
    public String[] obtenerNombresTipos() {
        String[] nombres = new String[modelo.obtenerTiposPartido().size()];
        for (int i = 0; i < nombres.length; i++) {
            nombres[i] = modelo.obtenerTiposPartido().get(i).obtenerNombre();
        }
        return nombres;
    }

    /**
     * Realiza la operación de obtener etiqueta valor.
     * @param tipo valor de tipo.
     * @return resultado de obtener etiqueta valor.
     */
    public String obtenerEtiquetaValor(int tipo) {
        return modelo.obtenerTiposPartido().get(tipo).obtenerEtiquetaValor();
    }

    /**
     * Realiza la operación de obtener valor por defecto.
     * @param tipo valor de tipo.
     * @return resultado de obtener valor por defecto.
     */
    public int obtenerValorPorDefecto(int tipo) {
        FabricaTipoPartido fabrica = modelo.obtenerTiposPartido().get(tipo);
        return fabrica.obtenerValorPorDefecto();
    }

    /**
     * Realiza la operación de obtener texto puntos.
     * @param jugador valor de jugador.
     * @return resultado de obtener texto puntos.
     */
    public String obtenerTextoPuntos(int jugador) {
        return "Puntos disponibles: " + modelo.obtenerPerfil(jugador).obtenerPuntos();
    }

    /**
     * Realiza la operación de obtener texto mejora.
     * @param jugador valor de jugador.
     * @param numeroEstadistica valor de numero estadistica.
     * @return resultado de obtener texto mejora.
     */
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

    /** Nombres de los perfiles disponibles para la maquina. */
    public boolean esModalidadMaquina(int indice) {
        return indice >= 0 && indice < modelo.obtenerModalidades().size()
                && modelo.obtenerModalidades().get(indice) instanceof JugadorVsMaquina;
    }

    public String[] obtenerNombresPerfilesMaquina() {
        PerfilMaquina[] perfiles = PerfilMaquina.values();
        String[] nombres = new String[perfiles.length];
        for (int i = 0; i < perfiles.length; i++) {
            nombres[i] = perfiles[i].obtenerNombre();
        }
        return nombres;
    }

    /** Guarda la partida seleccionada por el usuario. */
    public void guardarPartida() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar partida");
        if (selector.showSaveDialog(ventana) == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            if (!archivo.getName().toLowerCase().endsWith(".dopo")) {
                archivo = new File(archivo.getAbsolutePath() + ".dopo");
            }
            try {
                new AlmacenamientoPartida().guardar(modelo, archivo);
                ventana.mostrarMensaje("Partida guardada", "La partida se guardo correctamente.");
            } catch (IOException | DOPOSoccerException e) {
                RegistroErrores.registrar(e);
                ventana.mostrarError("No se pudo guardar la partida: " + e.getMessage());
            }
        }
    }

    /** Abre una partida guardada y continua desde el estado recuperado. */
    public void cargarPartida() {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Abrir partida");
        if (selector.showOpenDialog(ventana) == JFileChooser.APPROVE_OPTION) {
            try {
                DOPOSoccer modeloCargado = new AlmacenamientoPartida().cargar(selector.getSelectedFile());
                if (modeloCargado.obtenerPartida() == null) {
                    throw new IOException("El archivo no contiene una partida.");
                }
                reloj.stop();
                modelo = modeloCargado;
                modelo.limpiarTeclas();
                teclasPresionadas.clear();
                ventana.mostrarJuego(modelo.obtenerPartida());
                ultimoTiempo = System.nanoTime();
                // Tambien debe funcionar el teclado si la partida se guardo pausada.
                if (modelo.obtenerPartida().obtenerEstado() != EstadoPartida.TERMINADO) {
                    reloj.start();
                }
                guardarProgreso();
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                RegistroErrores.registrar(e);
                ventana.mostrarError("No se pudo abrir la partida: " + e.getMessage());
            }
        }
    }

    /** Guarda puntos y estadisticas sin necesidad de guardar una partida. */
    private void guardarProgreso() {
        try {
            new AlmacenamientoProgreso(new File("progreso.dopo")).guardar(modelo.obtenerPerfiles());
        } catch (IOException error) {
            RegistroErrores.registrar(error);
            if (ventana != null) {
                ventana.mostrarError("No se pudo guardar el progreso: " + error.getMessage());
            }
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

    /**
     * Realiza la operación de pausar oreanudar.
     */
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

    /**
     * Realiza la operación de terminar partida.
     */
    public void terminarPartida() {
        if (modelo.obtenerPartida() == null) return;
        modelo.terminarPartida();
        finalizarPartida();
    }

    /**
     * Realiza la operación de finalizar partida.
     */
    private void finalizarPartida() {
        reloj.stop();
        guardarProgreso();
        ventana.refrescarJuego();
        ventana.mostrarMensaje("Fin de la partida", armarMensajeFinal(modelo.obtenerPartida()));
        ventana.mostrarConfiguracion();
    }

    /**
     * Realiza la operación de armar mensaje final.
     * @param partida valor de partida.
     * @return resultado de armar mensaje final.
     */
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

    /**
     * Realiza la operación de key pressed.
     * @param e valor de e.
     */
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
            return; 
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

    /**
     * Realiza la operación de key released.
     * @param e valor de e.
     */
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

    /**
     * Realiza la operación de key typed.
     * @param e valor de e.
     */
    public void keyTyped(KeyEvent e) {
    }
}
