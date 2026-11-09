package doposoccer.vista;

import java.awt.BorderLayout;
import java.awt.Font;
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

/** Pantalla de configuracion: modalidad, cancha, personajes, tipo de partida y mejoras. */
public class PanelConfiguracion extends JPanel implements ActionListener {

    private static final int NUMERO_ESTADISTICAS = 4;
    private static final String[] AYUDA_TECLAS = {
        "Jugador 1: A / D mover, W saltar, S patear, Q poder",
        "Jugador 2: ← → mover, ↑ saltar, ↓ patear, Ctrl derecho poder"
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

    public PanelConfiguracion(ControladorDOPOSoccer controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titulo = new JLabel("DOPOSoccer", JLabel.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(titulo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.add(crearPanelGeneral(), BorderLayout.NORTH);
        centro.add(crearPanelJugadores(), BorderLayout.CENTER);
        add(centro, BorderLayout.CENTER);

        add(crearPanelInferior(), BorderLayout.SOUTH);
        actualizarTipo();
    }

    private JPanel crearPanelGeneral() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Partida"));

        comboModalidad = new JComboBox<String>(controlador.obtenerNombresModalidades());
        comboResolucion = new JComboBox<String>(controlador.obtenerNombresResoluciones());
        comboTipo = new JComboBox<String>(controlador.obtenerNombresTipos());
        comboTipo.addActionListener(this);
        etiquetaValor = new JLabel();
        spinnerValor = new JSpinner(new SpinnerNumberModel(3, 1, 99, 1));

        panel.add(new JLabel("Modalidad:"));
        panel.add(comboModalidad);
        panel.add(new JLabel("Resolución de la cancha:"));
        panel.add(comboResolucion);
        panel.add(new JLabel("Tipo de partido:"));
        panel.add(comboTipo);
        panel.add(etiquetaValor);
        panel.add(spinnerValor);
        return panel;
    }

    private JPanel crearPanelJugadores() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 15, 0));
        etiquetasPuntos = new JLabel[2];
        botonesMejora = new JButton[2][NUMERO_ESTADISTICAS];
        comboPersonaje1 = new JComboBox<String>(controlador.obtenerNombresPersonajes());
        comboPersonaje2 = new JComboBox<String>(controlador.obtenerNombresPersonajes());
        if (comboPersonaje2.getItemCount() > 1) {
            comboPersonaje2.setSelectedIndex(1);
        }
        panel.add(crearPanelJugador(0, comboPersonaje1));
        panel.add(crearPanelJugador(1, comboPersonaje2));
        return panel;
    }

    private JPanel crearPanelJugador(int jugador, JComboBox<String> comboPersonaje) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Jugador " + (jugador + 1)));
        panel.add(new JLabel("Personaje:"));
        panel.add(comboPersonaje);
        etiquetasPuntos[jugador] = new JLabel();
        panel.add(etiquetasPuntos[jugador]);
        for (int k = 0; k < NUMERO_ESTADISTICAS; k++) {
            botonesMejora[jugador][k] = new JButton();
            botonesMejora[jugador][k].addActionListener(this);
            panel.add(botonesMejora[jugador][k]);
        }
        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        botonIniciar = new JButton("Iniciar partida");
        botonIniciar.setFont(new Font("SansSerif", Font.BOLD, 16));
        botonIniciar.addActionListener(this);
        panel.add(botonIniciar);
        panel.add(new JLabel(armarTextoAyuda(), JLabel.CENTER));
        panel.add(new JLabel("Durante el juego: P pausa, ESC termina la partida", JLabel.CENTER));
        return panel;
    }

    private String armarTextoAyuda() {
        return AYUDA_TECLAS[0] + "   |   " + AYUDA_TECLAS[1];
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

    private void actualizarTipo() {
        int tipo = comboTipo.getSelectedIndex();
        if (tipo >= 0) {
            etiquetaValor.setText(controlador.obtenerEtiquetaValor(tipo) + ":");
            spinnerValor.setValue(controlador.obtenerValorPorDefecto(tipo));
        }
    }

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
