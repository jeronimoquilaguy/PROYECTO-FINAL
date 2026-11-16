package doposoccer.vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 * Colores, fuentes y componentes con el aspecto de DOPOSoccer.
 * Todas las pantallas usan esta clase, asi el aspecto se cambia en un solo lugar
 * sin tocar la logica de los paneles.
 */
public class Estilo {

    /** Fondo general de la pantalla de configuracion. */
    public static final Color FONDO = new Color(236, 242, 236);
    /** Fondo de las tarjetas. */
    public static final Color TARJETA = Color.WHITE;
    /** Verde cesped (titulos y boton principal). */
    public static final Color VERDE = new Color(30, 130, 60);
    /** Verde oscuro (encabezado y bordes). */
    public static final Color VERDE_OSCURO = new Color(18, 82, 40);
    /** Color del texto normal. */
    public static final Color TEXTO = new Color(40, 45, 50);
    /** Color del texto secundario. */
    public static final Color TEXTO_SUAVE = new Color(105, 112, 118);
    /** Color de los bordes de las tarjetas. */
    public static final Color BORDE = new Color(205, 215, 205);
    /** Color de cada jugador: rojo para el jugador 1 y azul para el jugador 2. */
    public static final Color[] COLORES_JUGADOR = { new Color(200, 40, 50), new Color(35, 90, 200) };

    /** Fuente de los titulos de las tarjetas. */
    public static final Font FUENTE_TITULO = new Font("SansSerif", Font.BOLD, 15);
    /** Fuente normal. */
    public static final Font FUENTE_NORMAL = new Font("SansSerif", Font.PLAIN, 13);
    /** Fuente en negrilla. */
    public static final Font FUENTE_NEGRILLA = new Font("SansSerif", Font.BOLD, 13);
    /** Fuente pequena (ayudas). */
    public static final Font FUENTE_PEQUENA = new Font("SansSerif", Font.PLAIN, 12);

    /**
     * Crea una tarjeta: un panel blanco con borde suave y un titulo arriba.
     * @param titulo texto del titulo de la tarjeta.
     * @return panel listo para agregarle componentes.
     */
    public static JPanel crearTarjeta(String titulo) {
        JPanel tarjeta = new JPanel();
        tarjeta.setBackground(TARJETA);
        Border linea = BorderFactory.createLineBorder(BORDE, 1, true);
        TitledBorder titulado = BorderFactory.createTitledBorder(linea, "  " + titulo + "  ");
        titulado.setTitleFont(FUENTE_TITULO);
        titulado.setTitleColor(VERDE_OSCURO);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(titulado,
                BorderFactory.createEmptyBorder(6, 10, 10, 10)));
        return tarjeta;
    }

    /**
     * Crea una etiqueta con la fuente y el color normales.
     * @param texto texto de la etiqueta.
     * @return etiqueta creada.
     */
    public static JLabel crearEtiqueta(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setFont(FUENTE_NEGRILLA);
        etiqueta.setForeground(TEXTO);
        return etiqueta;
    }

    /**
     * Crea un boton de color, con letra blanca y sin el recuadro de foco.
     * @param texto texto del boton.
     * @param color color de fondo del boton.
     * @return boton creado.
     */
    public static JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(FUENTE_NEGRILLA);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setOpaque(true);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return boton;
    }

    /**
     * Crea el boton grande para empezar la partida.
     * @param texto texto del boton.
     * @return boton creado.
     */
    public static JButton crearBotonPrincipal(String texto) {
        JButton boton = crearBoton(texto, VERDE);
        boton.setFont(new Font("SansSerif", Font.BOLD, 18));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return boton;
    }

    /**
     * Le da a una lista desplegable la fuente y el fondo del juego.
     * @param combo lista desplegable a decorar.
     */
    public static void decorar(JComboBox<String> combo) {
        combo.setFont(FUENTE_NORMAL);
        combo.setBackground(Color.WHITE);
        combo.setForeground(TEXTO);
    }

    /**
     * Le da a un selector de numeros la fuente del juego.
     * @param spinner selector a decorar.
     */
    public static void decorar(JSpinner spinner) {
        spinner.setFont(FUENTE_NORMAL);
    }

    /**
     * Hace transparente un componente para que se vea el fondo de la tarjeta.
     * @param componente componente a volver transparente.
     */
    public static void transparente(JComponent componente) {
        componente.setOpaque(false);
    }

    /**
     * Crea un "chip": una etiqueta con fondo de color y bordes, usada para mostrar teclas.
     * @param texto texto del chip.
     * @param color color del borde y del texto.
     * @return etiqueta con aspecto de tecla.
     */
    public static JLabel crearChip(String texto, Color color) {
        JLabel chip = new JLabel(texto, JLabel.CENTER);
        chip.setFont(FUENTE_NEGRILLA);
        chip.setForeground(color);
        chip.setOpaque(true);
        chip.setBackground(new Color(248, 250, 248));
        chip.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(color, 1, true),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)));
        return chip;
    }
}
