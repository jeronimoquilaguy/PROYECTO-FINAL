package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Pruebas de los cuatro tiros de poder del Anexo 1 y de los personajes que los crean. */
public class PoderesTest {

    private Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);

    /** Crea un balon quieto en el centro de la cancha mediana. */
    private Balon crearBalon() {
        return new Balon(cancha);
    }

    @Test
    public void auraAzulTieneNombreDanoYColor() {
        Poder poder = new AuraAzul();
        assertEquals("Aura Azul", poder.obtenerNombre());
        assertEquals(40.0, poder.obtenerDanoTiro(), 0.0001);
        assertEquals(0x1E90FF, poder.obtenerColorRgb());
    }

    @Test
    public void auraAzulSaleRectaYMuyRapida() {
        Balon balon = crearBalon();
        new AuraAzul().lanzar(balon, 1, cancha);
        assertEquals(1400.0, balon.obtenerVelX(), 0.0001);
        assertEquals(0.0, balon.obtenerVelY(), 0.0001);
    }

    @Test
    public void auraAzulProduceEmpujeHaciaElArcoDelRival() {
        Efecto efecto = new AuraAzul().crearEfecto(-1);
        assertEquals("Empujado", efecto.obtenerNombre());
        assertEquals(1.5, efecto.obtenerRestante(), 0.0001);
    }

    @Test
    public void rayoTieneNombreDanoYColor() {
        Poder poder = new Rayo();
        assertEquals("Rayo", poder.obtenerNombre());
        assertEquals(60.0, poder.obtenerDanoTiro(), 0.0001);
        assertEquals(0xFFD700, poder.obtenerColorRgb());
    }

    @Test
    public void rayoSaleRectoHaciaLaIzquierdaSiLoLanzaElJugadorDerecho() {
        Balon balon = crearBalon();
        new Rayo().lanzar(balon, -1, cancha);
        assertEquals(-1000.0, balon.obtenerVelX(), 0.0001);
        assertEquals(0.0, balon.obtenerVelY(), 0.0001);
    }

    @Test
    public void rayoProduceElectrocutado() {
        assertEquals("Electrocutado", new Rayo().crearEfecto(1).obtenerNombre());
    }

    @Test
    public void tornadoTieneNombreDanoYColor() {
        Poder poder = new Tornado();
        assertEquals("Tornado", poder.obtenerNombre());
        assertEquals(40.0, poder.obtenerDanoTiro(), 0.0001);
        assertEquals(0xB0B0B0, poder.obtenerColorRgb());
    }

    @Test
    public void tornadoBajaElBalonAlSueloYLoLanzaRasante() {
        Balon balon = crearBalon();
        new Tornado().lanzar(balon, 1, cancha);
        assertEquals(cancha.obtenerSuelo(), balon.obtenerY() + balon.obtenerAlto(), 0.0001);
        assertEquals(800.0, balon.obtenerVelX(), 0.0001);
        assertTrue(balon.obtenerVelY() < 0);
    }

    @Test
    public void tornadoProduceInconsciente() {
        assertEquals("Inconsciente", new Tornado().crearEfecto(1).obtenerNombre());
    }

    @Test
    public void ilusionTieneNombreDanoYColor() {
        Poder poder = new Ilusion();
        assertEquals("Ilusion", poder.obtenerNombre());
        assertEquals(30.0, poder.obtenerDanoTiro(), 0.0001);
        assertEquals(0xFF69B4, poder.obtenerColorRgb());
    }

    @Test
    public void ilusionSaleHaciaArribaYDespacio() {
        Balon balon = crearBalon();
        new Ilusion().lanzar(balon, 1, cancha);
        assertEquals(600.0, balon.obtenerVelX(), 0.0001);
        assertEquals(-200.0, balon.obtenerVelY(), 0.0001);
    }

    @Test
    public void ilusionProduceConfusion() {
        Efecto efecto = new Ilusion().crearEfecto(1);
        assertEquals("Confundido", efecto.obtenerNombre());
        assertTrue(efecto.esConfusion());
    }

    @Test
    public void elTiroDePoderEsMasRapidoEnUnaCanchaMasGrande() {
        Cancha grande = new Cancha(ResolucionCancha.GRANDE);
        Balon balon = new Balon(grande);
        new Rayo().lanzar(balon, 1, grande);
        assertEquals(1000.0 * 1.2, balon.obtenerVelX(), 0.0001);
    }

    @Test
    public void cadaPersonajeCreaSuPoder() {
        assertEquals("Aura Azul", new CoreaDelSur().crearPoder().obtenerNombre());
        assertEquals("Rayo", new Camerun().crearPoder().obtenerNombre());
        assertEquals("Tornado", new Nigeria().crearPoder().obtenerNombre());
        assertEquals("Ilusion", new EstadosUnidos().crearPoder().obtenerNombre());
    }

    @Test
    public void cadaPersonajeTieneSuPaisYColor() {
        assertEquals("Corea del Sur", new CoreaDelSur().obtenerPais());
        assertEquals("Camerún", new Camerun().obtenerPais());
        assertEquals("Nigeria", new Nigeria().obtenerPais());
        assertEquals("Estados Unidos", new EstadosUnidos().obtenerPais());
        assertEquals(0xD2042D, new CoreaDelSur().obtenerColorRgb());
        assertEquals(0x3C3B6E, new EstadosUnidos().obtenerColorRgb());
    }

    @Test
    public void cadaVezQueSeActivaSeCreaUnPoderNuevo() {
        Personaje personaje = new Camerun();
        assertNotSame(personaje.crearPoder(), personaje.crearPoder());
    }
}
