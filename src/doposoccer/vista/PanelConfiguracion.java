package doposoccer.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import doposoccer.controlador.ControladorDOPOSoccer;

/**
 * Pantalla de entrada (configuracion): modalidad, cancha, tipo de partida, personajes y mejoras.
 * Los datos los pide al controlador y el aspecto lo toma de la clase Estilo.
 */
public class PanelConfiguracion extends JPanel implements ActionListener {

    private static final int NUMERO_ESTADISTICAS = 4;

    /** Tabla de controles: cada fila es una accion con la tecla del jugador 1 y la del jugador 2. */
    private static final String[][] CONTROLES = {
        { "Mover", "A  /  D", "←  /  →" },
        { "Saltar", "W", "↑" },
        { "Patear", "S", "↓" },
        { "Poder", "Q", "Ctrl derecho" }
    };

    private ControladorDOPOSoccer controlador;
    private JComboBox<String> comboModalidad;
    private JComboBox<String> comboResolucion;
    private JComboBox<String> comboTipo;
    private JComboBox<String> comboPersonaje1;
    private JComboBox<String> comboPersonaje2;
    private JLabel etiquetaValor;
    private JSpinner spinnerValor;
    private JLabel[] etiquetasPuntos;
    private JButton[][] botonesMejora;
    private JButton botonIniciar;

    /**
     * Construye la pantalla de entrada.
     * @param controlador controlador al que se le avisan los botones y se le piden los datos.
     */
    public PanelConfiguracion(ControladorDOPOSoccer controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());
        setBackground(Estilo.FONDO);
        add(new PanelEncabezado(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(12, 12));
        contenido.setOpaque(false);
        contenido.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));
        contenido.add(crearPanelGeneral(), BorderLayout.NORTH);
        contenido.add(crearPanelJugadores(), BorderLayout.CENTER);
        contenido.add(crearPanelInferior(), BorderLayout.SOUTH);
        add(contenido, BorderLayout.CENTER);
        actualizarTipo();
    }

    /**
     * Tarjeta "Partida": modalidad, cancha, tipo de partido y minutos o goles.
     * @return la tarjeta armada.
     */
    private JPanel crearPanelGeneral() {
        JPanel tarjeta = Estilo.crearTarjeta("Partida");
        tarjeta.setLayout(new GridLayout(2, 4, 12, 8));

        comboModalidad = new JComboBox<String>(controlador.obtenerNombresModalidades());
        comboResolucion = new JComboBox<String>(controlador.obtenerNombresResoluciones());
        comboTipo = new JComboBox<String>(controlador.obtenerNombresTipos());
        comboTipo.addActionListener(this);
        etiquetaValor = Estilo.crearEtiqueta("");
        spinnerValor = new JSpinner(new SpinnerNumberModel(3, 1, 99, 1));
        Estilo.decorar(comboModalidad);
        Estilo.decorar(comboResolucion);
        Estilo.decorar(comboTipo);
        Estilo.decorar(spinnerValor);

        tarjeta.add(Estilo.crearEtiqueta("Modalidad"));
        tarjeta.add(comboModalidad);
        tarjeta.add(Estilo.crearEtiqueta("Cancha"));
        tarjeta.add(comboResolucion);
        tarjeta.add(Estilo.crearEtiqueta("Tipo de partido"));
        tarjeta.add(comboTipo);
        tarjeta.add(etiquetaValor);
        tarjeta.add(spinnerValor);
        return tarjeta;
    }

    /**
     * Las dos tarjetas de los jugadores, una al lado de la otra.
     * @return panel con las dos tarjetas.
     */
    private JPanel crearPanelJugadores() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 14, 0));
        panel.setOpaque(false);
        etiquetasPuntos = new JLabel[2];
        botonesMejora = new JButton[2][NUMERO_ESTADISTICAS];
        comboPersonaje1 = new JComboBox<String>(controlador.obtenerNombresPersonajes());
        comboPersonaje2 = new JComboBox<String>(controlador.obtenerNombresPersonajes());
        if (comboPersonaje2.getItemCount() > 1) {
            comboPersonaje2.setSelectedIndex(1);
        }
        Estilo.decorar(comboPersonaje1);
        Estilo.decorar(comboPersonaje2);
        panel.add(crearPanelJugador(0, comboPersonaje1));
        panel.add(crearPanelJugador(1, comboPersonaje2));
        return panel;
    }

    /**
     * Tarjeta de un jugador: franja de color, personaje, puntos y botones de mejora.
     * @param jugador 0 para el jugador 1, 1 para el jugador 2.
     * @param comboPersonaje lista desplegable de personajes de ese jugador.
     * @return la tarjeta armada.
     */
    private JPanel crearPanelJugador(int jugador, JComboBox<String> comboPersonaje) {
        Color color = Estilo.COLORES_JUGADOR[jugador];
        JPanel tarjeta = Estilo.crearTarjeta("Jugador " + (jugador + 1));
        tarjeta.setLayout(new BorderLayout(6, 8));

        JPanel franja = new JPanel();
        franja.setBackground(color);
        franja.setPreferredSize(new Dimension(6, 6));
        tarjeta.add(franja, BorderLayout.WEST);

        JPanel arriba = new JPanel(new GridLayout(0, 1, 4, 4));
        arriba.setOpaque(false);
        arriba.add(Estilo.crearEtiqueta("Personaje"));
        arriba.add(comboPersonaje);
        etiquetasPuntos[jugador] = new JLabel();
        etiquetasPuntos[jugador].setFont(Estilo.FUENTE_NEGRILLA);
        etiquetasPuntos[jugador].setForeground(color);
        arriba.add(etiquetasPuntos[jugador]);

        JPanel centro = new JPanel(new BorderLayout(0, 8));
        centro.setOpaque(false);
        centro.add(arriba, BorderLayout.NORTH);
        centro.add(crearPanelMejoras(jugador, color), BorderLayout.CENTER);
        tarjeta.add(centro, BorderLayout.CENTER);
        return tarjeta;
    }

    /**
     * Botones para mejorar las estadisticas de un jugador, del color de ese jugador.
     * @param jugador 0 para el jugador 1, 1 para el jugador 2.
     * @param color color del jugador.
     * @return panel con el titulo y los botones.
     */
    private JPanel crearPanelMejoras(int jugador, Color color) {
        JPanel mejoras = new JPanel(new GridLayout(0, 1, 4, 4));
        mejoras.setOpaque(false);
        JLabel tituloMejoras = new JLabel("Mejorar estadísticas (se pagan con puntos)");
        tituloMejoras.setFont(Estilo.FUENTE_PEQUENA);
        tituloMejoras.setForeground(Estilo.TEXTO_SUAVE);
        mejoras.add(tituloMejoras);
        for (int k = 0; k < NUMERO_ESTADISTICAS; k++) {
            botonesMejora[jugador][k] = Estilo.crearBoton("", color);
            botonesMejora[jugador][k].addActionListener(this);
            mejoras.add(botonesMejora[jugador][k]);
        }
        return mejoras;
    }

    /**
     * Parte de abajo: boton para iniciar y la tarjeta de controles.
     * @return panel inferior.
     */
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setOpaque(false);
        botonIniciar = Estilo.crearBotonPrincipal("▶   Iniciar partida");
        botonIniciar.addActionListener(this);
        panel.add(botonIniciar, BorderLayout.NORTH);
        panel.add(crearPanelControles(), BorderLayout.CENTER);
        return panel;
    }

    /**
     * Tarjeta "Controles": una columna por accion y una fila por jugador.
     * Para mostrar una accion nueva basta con agregar una fila a CONTROLES.
     * @return la tarjeta armada.
     */
    private JPanel crearPanelControles() {
        JPanel tarjeta = Estilo.crearTarjeta("Controles");
        tarjeta.setLayout(new BorderLayout(0, 6));

        JPanel tabla = new JPanel(new GridLayout(3, CONTROLES.length + 1, 8, 5));
        tabla.setOpaque(false);
        tabla.add(new JLabel(""));
        for (int i = 0; i < CONTROLES.length; i++) {
            tabla.add(crearEncabezadoTabla(CONTROLES[i][0], Estilo.TEXTO));
        }
        for (int jugador = 0; jugador < 2; jugador++) {
            Color color = Estilo.COLORES_JUGADOR[jugador];
            tabla.add(crearEncabezadoTabla("Jugador " + (jugador + 1), color));
            for (int i = 0; i < CONTROLES.length; i++) {
                tabla.add(Estilo.crearChip(CONTROLES[i][jugador + 1], color));
            }
        }
        tarjeta.add(tabla, BorderLayout.CENTER);

        JLabel ayuda = new JLabel("Durante el juego:   P  pausa / continúa      ESC  termina la partida", JLabel.CENTER);
        ayuda.setFont(Estilo.FUENTE_PEQUENA);
        ayuda.setForeground(Estilo.TEXTO_SUAVE);
        tarjeta.add(ayuda, BorderLayout.SOUTH);
        return tarjeta;
    }

    /**
     * Titulo de una columna de la tabla de controles.
     * @param texto texto del titulo.
     * @param color color del texto.
     * @return etiqueta centrada.
     */
    private JLabel crearEncabezadoTabla(String texto, Color color) {
        JLabel etiqueta = new JLabel(texto, JLabel.CENTER);
        etiqueta.setFont(Estilo.FUENTE_NEGRILLA);
        etiqueta.setForeground(color);
        return etiqueta;
    }

    /** Vuelve a leer del controlador los puntos y los niveles. */
    public void actualizar() {
        for (int j = 0; j < 2; j++) {
            etiquetasPuntos[j].setText(controlador.obtenerTextoPuntos(j));
            for (int k = 0; k < NUMERO_ESTADISTICAS; k++) {
                botonesMejora[j][k].setText(controlador.obtenerTextoMejora(j, k));
            }
        }
    }

    /**
     * Cambia la etiqueta ("Minutos" o "Goles para ganar") y el valor segun el tipo de partido.
     */
    private void actualizarTipo() {
        int tipo = comboTipo.getSelectedIndex();
        if (tipo >= 0) {
            etiquetaValor.setText(controlador.obtenerEtiquetaValor(tipo));
            spinnerValor.setValue(controlador.obtenerValorPorDefecto(tipo));
        }
    }

    /**
     * Atiende los botones y la lista de tipo de partido.
     * @param e evento de Swing con el componente que se oprimio.
     */
    public void actionPerformed(ActionEvent e) {
        Object origen = e.getSource();
        if (origen == botonIniciar) {
            int valor = ((Integer) spinnerValor.getValue()).intValue();
            controlador.iniciarPartida(comboModalidad.getSelectedIndex(),
                    comboResolucion.getSelectedIndex(), comboPersonaje1.getSelectedIndex(),
                    comboPersonaje2.getSelectedIndex(), comboTipo.getSelectedIndex(), valor);
        } else if (origen == comboTipo) {
            actualizarTipo();
        } else {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < NUMERO_ESTADISTICAS; k++) {
                    if (origen == botonesMejora[j][k]) {
                        controlador.mejorarEstadistica(j, k);
                    }
                }
            }
        }
    }
}
