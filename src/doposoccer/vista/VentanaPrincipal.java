package doposoccer.vista;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import doposoccer.controlador.ControladorDOPOSoccer;
import doposoccer.modelo.Partida;

/** Ventana principal: muestra la pantalla de configuracion o la del juego. */
public class VentanaPrincipal extends JFrame {

    private PanelConfiguracion panelConfiguracion;
    private PanelJuego panelJuego;

    /**
     * Construye una instancia de VentanaPrincipal.
     * @param controlador valor de controlador.
     */
    public VentanaPrincipal(ControladorDOPOSoccer controlador) {
        setTitle("DOPOSoccer 2026-2");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        panelConfiguracion = new PanelConfiguracion(controlador);
        panelJuego = new PanelJuego(controlador);
        setJMenuBar(crearMenu(controlador));
    }

    /**
     * Realiza la operación de mostrar configuracion.
     */
    private JMenuBar crearMenu(final ControladorDOPOSoccer controlador) {
        JMenuBar barra = new JMenuBar();
        JMenu menuPartida = new JMenu("Partida");
        JMenuItem guardar = new JMenuItem("Guardar partida...");
        JMenuItem abrir = new JMenuItem("Abrir partida...");
        guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                controlador.guardarPartida();
            }
        });
        abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                controlador.cargarPartida();
            }
        });
        menuPartida.add(guardar);
        menuPartida.add(abrir);
        barra.add(menuPartida);
        return barra;
    }

    public void mostrarConfiguracion() {
        panelConfiguracion.actualizar();
        setContentPane(panelConfiguracion);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    /**
     * Realiza la operación de mostrar juego.
     * @param partida valor de partida.
     */
    public void mostrarJuego(Partida partida) {
        panelJuego.prepararPara(partida);
        setContentPane(panelJuego);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        panelJuego.requestFocusInWindow();
    }

    /**
     * Realiza la operación de actualizar configuracion.
     */
    public void actualizarConfiguracion() {
        panelConfiguracion.actualizar();
    }

    /**
     * Realiza la operación de refrescar juego.
     */
    public void refrescarJuego() {
        panelJuego.repaint();
    }

    /**
     * Realiza la operación de mostrar mensaje.
     * @param titulo valor de titulo.
     * @param mensaje valor de mensaje.
     */
    public void mostrarMensaje(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Realiza la operación de mostrar error.
     * @param mensaje valor de mensaje.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
