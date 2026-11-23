package doposoccer.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Banner de la pantalla de entrada: una cancha de futbol dibujada con el titulo del juego.
 * Solo dibuja; no tiene logica del juego.
 */
public class PanelEncabezado extends JPanel {

    private static final int ALTO = 92;
    private static final int FRANJAS = 12;

    /** Construye el encabezado con su alto fijo. */
    public PanelEncabezado() {
        setPreferredSize(new Dimension(760, ALTO));
    }

    /**
     * Dibuja el cesped, las lineas de la cancha, el balon y el titulo.
     * @param g lapiz de Swing.
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int ancho = getWidth();
        int alto = getHeight();
        dibujarCesped(g2, ancho, alto);
        dibujarLineas(g2, ancho, alto);
        dibujarBalon(g2, ancho / 2 - 190, alto / 2 - 32);
        dibujarTitulo(g2, ancho, alto);
    }

    /** Cesped con franjas claras y oscuras, como una cancha recien cortada. */
    private void dibujarCesped(Graphics2D g2, int ancho, int alto) {
        g2.setPaint(new GradientPaint(0, 0, Estilo.VERDE, 0, alto, Estilo.VERDE_OSCURO));
        g2.fillRect(0, 0, ancho, alto);
        int anchoFranja = ancho / FRANJAS + 1;
        g2.setColor(new Color(255, 255, 255, 18));
        for (int i = 0; i < FRANJAS; i = i + 2) {
            g2.fillRect(i * anchoFranja, 0, anchoFranja, alto);
        }
    }

    /** Linea central, circulo central y areas de los dos arcos. */
    private void dibujarLineas(Graphics2D g2, int ancho, int alto) {
        g2.setColor(new Color(255, 255, 255, 90));
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(ancho / 2, 0, ancho / 2, alto);
        g2.drawOval(ancho / 2 - 45, alto / 2 - 45, 90, 90);
        g2.drawRect(-2, alto / 2 - 35, 60, 70);
        g2.drawRect(ancho - 58, alto / 2 - 35, 60, 70);
    }

    /** Balon blanco con manchas negras. */
    private void dibujarBalon(Graphics2D g2, int x, int y) {
        int diametro = 34;
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillOval(x + 3, y + 4, diametro, diametro);
        g2.setColor(Color.WHITE);
        g2.fillOval(x, y, diametro, diametro);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, diametro, diametro);
        g2.fillOval(x + 11, y + 11, 12, 12);
        g2.fillOval(x + 3, y + 5, 6, 6);
        g2.fillOval(x + 24, y + 22, 6, 6);
    }

    /** Titulo con sombra y subtitulo, centrados. */
    private void dibujarTitulo(Graphics2D g2, int ancho, int alto) {
        g2.setFont(new Font("SansSerif", Font.BOLD, 44));
        FontMetrics medidas = g2.getFontMetrics();
        String titulo = "DOPOSoccer";
        int x = (ancho - medidas.stringWidth(titulo)) / 2 + 20;
        int y = alto / 2 + 2;
        g2.setColor(new Color(0, 0, 0, 90));
        g2.drawString(titulo, x + 3, y + 3);
        g2.setColor(Color.WHITE);
        g2.drawString(titulo, x, y);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
        medidas = g2.getFontMetrics();
        String subtitulo = "Head Soccer en el computador  ·  Proyecto final DOPO 2026-2";
        g2.setColor(new Color(225, 245, 228));
        g2.drawString(subtitulo, (ancho - medidas.stringWidth(subtitulo)) / 2, y + 26);
    }
}
