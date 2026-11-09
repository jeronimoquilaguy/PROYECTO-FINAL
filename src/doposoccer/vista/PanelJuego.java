package doposoccer.vista;

import doposoccer.controlador.ControladorDOPOSoccer;
import doposoccer.modelo.Arco;
import doposoccer.modelo.Balon;
import doposoccer.modelo.Cancha;
import doposoccer.modelo.Efecto;
import doposoccer.modelo.Estadisticas;
import doposoccer.modelo.EstadoPartida;
import doposoccer.modelo.Jugador;
import doposoccer.modelo.Partida;
import doposoccer.modelo.TipoEstadistica;
import doposoccer.modelo.TipoPartido;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/** Pantalla del juego: dibuja la cancha, los jugadores, el balon y la informacion de la partida. */
public class PanelJuego extends JPanel {

  
    private static final int[][] SENUELOS = {
        { -140, -80 }, { 120, -120 }, { -60, 70 }, { 200, 30 }, { -230, 10 }, { 60, -200 }, { 150, 110 }
    };

    private Partida partida;

    public PanelJuego(ControladorDOPOSoccer controlador) {
        setFocusable(true);
        addKeyListener(controlador);
    }

    public void prepararPara(Partida nuevaPartida) {
        partida = nuevaPartida;
        Cancha cancha = partida.obtenerCancha();
        setPreferredSize(new Dimension((int) cancha.obtenerAncho(), (int) cancha.obtenerAlto()));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (partida == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        dibujarCancha(g2);
        Arco[] arcos = partida.obtenerArcos();
        for (int i = 0; i < arcos.length; i++) {
            dibujarArco(g2, arcos[i]);
        }
        Jugador[] jugadores = partida.obtenerJugadores();
        for (int i = 0; i < jugadores.length; i++) {
            dibujarJugador(g2, jugadores[i]);
        }
        dibujarBalon(g2);
        dibujarInformacion(g2);
        dibujarMensajes(g2);
    }

    // ---------- Cancha ----------

    private void dibujarCancha(Graphics2D g2) {
        Cancha cancha = partida.obtenerCancha();
        int ancho = (int) cancha.obtenerAncho();
        int alto = (int) cancha.obtenerAlto();
        int suelo = (int) cancha.obtenerSuelo();
        g2.setColor(new Color(135, 206, 235));
        g2.fillRect(0, 0, ancho, suelo);
        g2.setColor(new Color(34, 139, 34));
        g2.fillRect(0, suelo, ancho, alto - suelo);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(0, suelo, ancho, suelo);
        g2.drawLine(ancho / 2, suelo, ancho / 2, (int) (suelo - 60 * cancha.obtenerEscala()));
    }

    private void dibujarArco(Graphics2D g2, Arco arco) {
        int x = (int) arco.obtenerX();
        int y = (int) arco.obtenerYTravesano();
        int ancho = (int) arco.obtenerAncho();
        int alto = (int) arco.obtenerAlto();
        g2.setColor(new Color(255, 255, 255, 70));
        g2.fillRect(x, y, ancho, alto);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(x, y, ancho, alto);
        for (int i = 1; i < 6; i++) {
            g2.drawLine(x, y + i * alto / 6, x + ancho, y + i * alto / 6);
        }
        g2.setStroke(new BasicStroke(1));
        g2.fillRect(x, y, ancho, (int) arco.obtenerGrosor());
    }

    // ---------- Jugadores ----------

    private void dibujarJugador(Graphics2D g2, Jugador j) {
        int x = (int) j.obtenerX();
        int y = (int) j.obtenerY();
        int ancho = (int) j.obtenerAncho();
        int alto = (int) j.obtenerAlto();
        Color colorPersonaje = new Color(j.obtenerPersonaje().obtenerColorRgb());
        int frente = j.obtenerLado().obtenerDireccionFrente();

       
        if (j.tienePoderActivo()) {
            Color colorAura = new Color(j.obtenerPoderActivo().obtenerColorRgb());
            g2.setColor(new Color(colorAura.getRed(), colorAura.getGreen(), colorAura.getBlue(), 110));
            g2.fillOval(x - ancho / 2, y - alto / 4, ancho * 2, (int) (alto * 1.5));
            g2.setColor(colorAura);
            g2.setStroke(new BasicStroke(3));
            g2.drawOval(x - ancho / 2, y - alto / 4, ancho * 2, (int) (alto * 1.5));
        }

   
        g2.setColor(colorPersonaje);
        g2.fillRoundRect(x, y + ancho / 2, ancho, alto - ancho / 2, 12, 12);
        g2.setColor(new Color(255, 224, 189));
        g2.fillOval(x, y, ancho, ancho);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, ancho, ancho);
        int ojoX = x + ancho / 2 + frente * ancho / 6 - ancho / 10;
        g2.setColor(Color.WHITE);
        g2.fillOval(ojoX, y + ancho / 4, ancho / 4, ancho / 4);
        g2.setColor(Color.BLACK);
        g2.fillOval(ojoX + frente * ancho / 20 + ancho / 16, y + ancho / 4 + ancho / 16, ancho / 10, ancho / 10);

      
        if (j.estaPateando()) {
            double[] zona = j.obtenerZonaPie();
            g2.setColor(Color.DARK_GRAY);
            g2.fillRoundRect((int) zona[0], (int) (zona[1] + zona[3] * 0.5), (int) zona[2],
                    (int) (zona[3] * 0.4), 8, 8);
        }

       
        Efecto efecto = j.obtenerEfecto();
        if (efecto != null) {
            g2.setColor(new Color(255, 0, 0, 200));
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(x - 3, y - 3, ancho + 6, alto + 6, 14, 14);
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            g2.drawString(efecto.obtenerNombre() + " " + String.format("%.1f", efecto.obtenerRestante()) + "s",
                    x - 10, y - 8);
        }
    }

    // ---------- Balon ----------

    private void dibujarBalon(Graphics2D g2) {
        Balon balon = partida.obtenerBalon();
        double escala = partida.obtenerCancha().obtenerEscala();
        boolean confusion = false;
        Jugador[] jugadores = partida.obtenerJugadores();
        for (int i = 0; i < jugadores.length; i++) {
            if (jugadores[i].estaConfundido()) {
                confusion = true;
            }
        }
        if (confusion) {
            // Los 8 balones se ven iguales: el rival no sabe cual es el real
            for (int i = 0; i < SENUELOS.length; i++) {
                dibujarUnBalon(g2, balon.obtenerX() + SENUELOS[i][0] * escala,
                        balon.obtenerY() + SENUELOS[i][1] * escala, balon.obtenerRadio(), null);
            }
        }
        Color estela = null;
        if (balon.tienePoderEnVuelo()) {
            estela = new Color(balon.obtenerPoder().obtenerColorRgb());
        }
        dibujarUnBalon(g2, balon.obtenerX(), balon.obtenerY(), balon.obtenerRadio(), estela);
    }

    private void dibujarUnBalon(Graphics2D g2, double x, double y, double radio, Color estela) {
        int diametro = (int) (radio * 2);
        if (estela != null) {
            g2.setColor(new Color(estela.getRed(), estela.getGreen(), estela.getBlue(), 120));
            g2.fillOval((int) x - diametro / 2, (int) y - diametro / 2, diametro * 2, diametro * 2);
        }
        g2.setColor(Color.WHITE);
        g2.fillOval((int) x, (int) y, diametro, diametro);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawOval((int) x, (int) y, diametro, diametro);
        g2.fillOval((int) (x + radio * 0.6), (int) (y + radio * 0.6), (int) (radio * 0.8), (int) (radio * 0.8));
    }

    // ---------- Informacion ----------

    private void dibujarInformacion(Graphics2D g2) {
        int ancho = getWidth();
        Jugador[] jugadores = partida.obtenerJugadores();
        dibujarDatosJugador(g2, jugadores[0], 10);
        dibujarDatosJugador(g2, jugadores[1], ancho - 240);

        TipoPartido tipo = partida.obtenerTipoPartido();
        String marcador = partida.obtenerMarcador().obtenerGoles(jugadores[0]) + "  -  "
                + partida.obtenerMarcador().obtenerGoles(jugadores[1]);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 30));
        centrarTexto(g2, marcador, ancho / 2, 32);

        g2.setFont(new Font("SansSerif", Font.BOLD, 16));
        String detalle;
        if (tipo.usaTiempo()) {
            int segundos = (int) Math.ceil(tipo.obtenerTiempoRestante());
            detalle = String.format("%02d:%02d", segundos / 60, segundos % 60);
            if (tipo.estaEnMuerteSubita(partida.obtenerMarcador())) {
                detalle = "MUERTE SÚBITA";
            }
        } else {
            detalle = tipo.obtenerDescripcion();
        }
        centrarTexto(g2, detalle, ancho / 2, 56);
    }

    private void dibujarDatosJugador(Graphics2D g2, Jugador j, int x) {
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        g2.drawString(j.obtenerPerfil().obtenerNombre() + " - " + j.obtenerPersonaje().obtenerPais(), x, 16);

        dibujarBarra(g2, x, 22, 220, 14, j.obtenerVida() / j.obtenerVidaMaxima(), new Color(220, 40, 40));
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        g2.drawString("Vida " + (int) Math.ceil(j.obtenerVida()) + "/" + (int) j.obtenerVidaMaxima(), x + 6, 33);

        Color colorBarra = new Color(70, 130, 220);
        if (j.obtenerBarraPoder() >= 1.0) {
            colorBarra = new Color(255, 200, 0);
        }
        dibujarBarra(g2, x, 40, 220, 10, j.obtenerBarraPoder(), colorBarra);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        String poder = "Poder: cargando";
        if (j.tienePoderActivo()) {
            poder = "Poder activo: " + j.obtenerPoderActivo().obtenerNombre();
        } else if (j.obtenerBarraPoder() >= 1.0) {
            poder = "Poder listo";
        }
        g2.drawString(poder, x, 64);

        Estadisticas e = j.obtenerPerfil().obtenerEstadisticas();
        String datos = "Goles " + partida.obtenerMarcador().obtenerGoles(j)
                + "  Vel " + e.obtenerNivel(TipoEstadistica.SPEED)
                + " Sal " + e.obtenerNivel(TipoEstadistica.JUMP)
                + " Pat " + e.obtenerNivel(TipoEstadistica.KICK)
                + " Vid " + e.obtenerNivel(TipoEstadistica.SURVIVAL)
                + "  Pts " + j.obtenerPerfil().obtenerPuntos();
        g2.drawString(datos, x, 78);
    }

    private void dibujarBarra(Graphics2D g2, int x, int y, int ancho, int alto, double fraccion, Color color) {
        g2.setColor(new Color(60, 60, 60));
        g2.fillRect(x, y, ancho, alto);
        g2.setColor(color);
        g2.fillRect(x, y, (int) (ancho * Math.max(0, Math.min(1, fraccion))), alto);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(x, y, ancho, alto);
    }

    private void centrarTexto(Graphics2D g2, String texto, int centroX, int y) {
        FontMetrics medidas = g2.getFontMetrics();
        g2.drawString(texto, centroX - medidas.stringWidth(texto) / 2, y);
    }

    // ---------- Mensajes grandes ----------

    private void dibujarMensajes(Graphics2D g2) {
        int ancho = getWidth();
        int alto = getHeight();
        if (partida.obtenerEstado() == EstadoPartida.PAUSADO) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, ancho, alto);
            g2.setComposite(AlphaComposite.SrcOver);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 48));
            centrarTexto(g2, "PAUSA", ancho / 2, alto / 2);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 18));
            centrarTexto(g2, "P para continuar - ESC para terminar", ancho / 2, alto / 2 + 36);
        } else if (partida.hayCelebracionGol()) {
            g2.setColor(new Color(255, 215, 0));
            g2.setFont(new Font("SansSerif", Font.BOLD, 72));
            centrarTexto(g2, "¡GOL!", ancho / 2, alto / 2);
        }
    }
}
