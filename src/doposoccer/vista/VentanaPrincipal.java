package doposoccer.vista;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import doposoccer.controlador.ControladorDOPOSoccer;
import doposoccer.modelo.Partida;

/** Ventana principal: muestra la pantalla de configuracion o la del juego. */
public class VentanaPrincipal extends JFrame {

    private PanelConfiguracion panelConfiguracion;
    private PanelJuego panelJuego;

    public VentanaPrincipal(ControladorDOPOSoccer controlador) {
        setTitle("DOPOSoccer 2026-2");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        panelConfiguracion = new PanelConfiguracion(controlador);
        panelJuego = new PanelJuego(controlador);
    }

    public void mostrarConfiguracion() {
        panelConfiguracion.actualizar();
        setContentPane(panelConfiguracion);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }

    public void mostrarJuego(Partida partida) {
        panelJuego.prepararPara(partida);
        setContentPane(panelJuego);
        pack();
        setLocationRelativeTo(null);
        revalidate();
        panelJuego.requestFocusInWindow();
    }

    public void actualizarConfiguracion() {
        panelConfiguracion.actualizar();
    }

    public void refrescarJuego() {
        panelJuego.repaint();
    }

    public void mostrarMensaje(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
