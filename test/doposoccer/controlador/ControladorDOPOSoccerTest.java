package doposoccer.controlador;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import doposoccer.modelo.Camerun;
import doposoccer.modelo.CoreaDelSur;
import doposoccer.modelo.DOPOSoccer;
import doposoccer.modelo.FabricaPorGoles;
import doposoccer.modelo.FabricaPorTiempo;
import doposoccer.modelo.FabricaTipoPartido;
import doposoccer.modelo.JugadorVsJugador;
import doposoccer.modelo.Modalidad;
import doposoccer.modelo.Personaje;
import doposoccer.modelo.TipoEstadistica;

/**
 * Pruebas de los datos que el controlador le entrega a la pantalla de configuracion.
 * No abren ninguna ventana: solo usan los metodos que leen del modelo.
 */
public class ControladorDOPOSoccerTest {

    private DOPOSoccer crearModelo() {
        List<Personaje> personajes = new ArrayList<Personaje>();
        personajes.add(new CoreaDelSur());
        personajes.add(new Camerun());
        List<Modalidad> modalidades = new ArrayList<Modalidad>();
        modalidades.add(new JugadorVsJugador());
        List<FabricaTipoPartido> tipos = new ArrayList<FabricaTipoPartido>();
        tipos.add(new FabricaPorTiempo());
        tipos.add(new FabricaPorGoles());
        return new DOPOSoccer(personajes, modalidades, tipos);
    }

    @Test
    public void entregaLosNombresDeLosCatalogos() {
        ControladorDOPOSoccer controlador = new ControladorDOPOSoccer(crearModelo());
        assertEquals("Jugador vs Jugador", controlador.obtenerNombresModalidades()[0]);
        assertEquals(3, controlador.obtenerNombresResoluciones().length);
        assertEquals("Corea del Sur", controlador.obtenerNombresPersonajes()[0]);
        assertEquals("Camerún", controlador.obtenerNombresPersonajes()[1]);
        assertEquals("Por tiempo", controlador.obtenerNombresTipos()[0]);
        assertEquals("Por goles", controlador.obtenerNombresTipos()[1]);
    }

    @Test
    public void entregaLaEtiquetaYElValorDeCadaTipoDePartido() {
        ControladorDOPOSoccer controlador = new ControladorDOPOSoccer(crearModelo());
        assertEquals("Minutos", controlador.obtenerEtiquetaValor(0));
        assertEquals(3, controlador.obtenerValorPorDefecto(0));
        assertEquals("Goles para ganar", controlador.obtenerEtiquetaValor(1));
        assertEquals(5, controlador.obtenerValorPorDefecto(1));
    }

    @Test
    public void muestraLosPuntosDeCadaJugador() {
        DOPOSoccer modelo = crearModelo();
        modelo.obtenerPerfil(0).sumarPuntos(125);
        ControladorDOPOSoccer controlador = new ControladorDOPOSoccer(modelo);
        assertTrue(controlador.obtenerTextoPuntos(0).contains("125"));
        assertTrue(controlador.obtenerTextoPuntos(1).contains("0"));
    }

    @Test
    public void muestraElNivelYElPrecioDeLaMejora() {
        ControladorDOPOSoccer controlador = new ControladorDOPOSoccer(crearModelo());
        String texto = controlador.obtenerTextoMejora(0, 0);
        assertTrue(texto.contains("1"));
        assertTrue(texto.contains("500"));
    }

    @Test
    public void avisaCuandoLaEstadisticaEstaEnElMaximo() throws Exception {
        DOPOSoccer modelo = crearModelo();
        for (int i = 0; i < 9; i++) {
            modelo.obtenerPerfil(1).obtenerEstadisticas().subirNivel(TipoEstadistica.SPEED);
        }
        ControladorDOPOSoccer controlador = new ControladorDOPOSoccer(modelo);
        String texto = controlador.obtenerTextoMejora(1, TipoEstadistica.SPEED.ordinal());
        assertTrue(texto.contains("10"));
        assertTrue(texto.contains("maximo") || texto.contains("máximo"));
    }
}
