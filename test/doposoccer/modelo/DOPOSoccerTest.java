package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

/** Pruebas de la fachada DOPOSoccer, armada igual que en Main. */
public class DOPOSoccerTest {

    /** Arma el juego con los mismos catalogos que usa Main. */
    private DOPOSoccer crearJuego() {
        List<Personaje> personajes = new ArrayList<Personaje>();
        personajes.add(new CoreaDelSur());
        personajes.add(new Camerun());
        personajes.add(new Nigeria());
        personajes.add(new EstadosUnidos());
        List<Modalidad> modalidades = new ArrayList<Modalidad>();
        modalidades.add(new JugadorVsJugador());
        List<FabricaTipoPartido> tipos = new ArrayList<FabricaTipoPartido>();
        tipos.add(new FabricaPorTiempo());
        tipos.add(new FabricaPorGoles());
        return new DOPOSoccer(personajes, modalidades, tipos);
    }

    /** Crea una partida Corea del Sur vs Camerun, por goles, en la cancha mediana. */
    private DOPOSoccer crearJuegoConPartida() throws DOPOSoccerException {
        DOPOSoccer juego = crearJuego();
        juego.nuevaPartida(juego.obtenerModalidades().get(0), ResolucionCancha.MEDIANA,
                juego.obtenerPersonajes().get(0), juego.obtenerPersonajes().get(1),
                juego.obtenerTiposPartido().get(1), 3);
        return juego;
    }

    @Test
    public void guardaLosCatalogosQueRecibe() {
        DOPOSoccer juego = crearJuego();
        assertEquals(4, juego.obtenerPersonajes().size());
        assertEquals(1, juego.obtenerModalidades().size());
        assertEquals(2, juego.obtenerTiposPartido().size());
    }

    @Test
    public void empiezaSinPartidaYConDosPerfilesSinPuntos() {
        DOPOSoccer juego = crearJuego();
        assertNull(juego.obtenerPartida());
        assertEquals("Jugador 1", juego.obtenerPerfil(0).obtenerNombre());
        assertEquals("Jugador 2", juego.obtenerPerfil(1).obtenerNombre());
        assertEquals(0, juego.obtenerPerfil(0).obtenerPuntos());
    }

    @Test
    public void sinPartidaLasOrdenesNoHacenNada() {
        DOPOSoccer juego = crearJuego();
        juego.actualizar(0.016);
        juego.pausar();
        juego.reanudar();
        juego.terminarPartida();
        juego.presionarAccion(0, Accion.DERECHA);
        juego.soltarAccion(0, Accion.DERECHA);
        assertNull(juego.obtenerPartida());
    }

    @Test
    public void creaUnaPartidaConLosDatosElegidos() throws DOPOSoccerException {
        DOPOSoccer juego = crearJuegoConPartida();
        Partida partida = juego.obtenerPartida();
        assertNotNull(partida);
        assertEquals(EstadoPartida.EN_JUEGO, partida.obtenerEstado());
        assertEquals(ResolucionCancha.MEDIANA, partida.obtenerCancha().obtenerResolucion());
        assertEquals("Corea del Sur", partida.obtenerJugadores()[0].obtenerPersonaje().obtenerPais());
        assertEquals("Camerún", partida.obtenerJugadores()[1].obtenerPersonaje().obtenerPais());
        assertEquals("Primero a 3 goles", partida.obtenerTipoPartido().obtenerDescripcion());
    }

    @Test
    public void elJugadorUnoJuegaALaIzquierdaYElDosALaDerecha() throws DOPOSoccerException {
        Partida partida = crearJuegoConPartida().obtenerPartida();
        assertEquals(Lado.IZQUIERDO, partida.obtenerJugadores()[0].obtenerLado());
        assertEquals(Lado.DERECHO, partida.obtenerJugadores()[1].obtenerLado());
    }

    @Test
    public void faltanDatosLanzaExcepcionConMensajeClaro() {
        DOPOSoccer juego = crearJuego();
        Modalidad modalidad = juego.obtenerModalidades().get(0);
        Personaje personaje = juego.obtenerPersonajes().get(0);
        FabricaTipoPartido fabrica = juego.obtenerTiposPartido().get(0);
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class,
                () -> juego.nuevaPartida(null, ResolucionCancha.MEDIANA, personaje, personaje, fabrica, 3));
        assertEquals(DOPOSoccerException.DATOS_INCOMPLETOS, e.getMessage());
        assertThrows(DOPOSoccerException.class,
                () -> juego.nuevaPartida(modalidad, null, personaje, personaje, fabrica, 3));
        assertThrows(DOPOSoccerException.class,
                () -> juego.nuevaPartida(modalidad, ResolucionCancha.MEDIANA, null, personaje, fabrica, 3));
        assertThrows(DOPOSoccerException.class,
                () -> juego.nuevaPartida(modalidad, ResolucionCancha.MEDIANA, personaje, null, fabrica, 3));
        assertThrows(DOPOSoccerException.class,
                () -> juego.nuevaPartida(modalidad, ResolucionCancha.MEDIANA, personaje, personaje, null, 3));
        assertNull(juego.obtenerPartida());
    }

    @Test
    public void unValorInvalidoNoCreaLaPartida() {
        DOPOSoccer juego = crearJuego();
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class,
                () -> juego.nuevaPartida(juego.obtenerModalidades().get(0), ResolucionCancha.MEDIANA,
                        juego.obtenerPersonajes().get(0), juego.obtenerPersonajes().get(1),
                        juego.obtenerTiposPartido().get(0), 0));
        assertEquals(DOPOSoccerException.MINUTOS_INVALIDOS, e.getMessage());
        assertNull(juego.obtenerPartida());
    }

    @Test
    public void elTecladoLlegaSoloAlJugadorIndicado() throws DOPOSoccerException {
        DOPOSoccer juego = crearJuegoConPartida();
        Jugador j1 = juego.obtenerPartida().obtenerJugadores()[0];
        Jugador j2 = juego.obtenerPartida().obtenerJugadores()[1];
        double x1 = j1.obtenerX();
        double x2 = j2.obtenerX();
        juego.presionarAccion(0, Accion.DERECHA);
        juego.actualizar(0.1);
        assertTrue(j1.obtenerX() > x1);
        assertEquals(x2, j2.obtenerX(), 0.0001);
    }

    @Test
    public void alSoltarLaTeclaElJugadorSeDetiene() throws DOPOSoccerException {
        DOPOSoccer juego = crearJuegoConPartida();
        Jugador j1 = juego.obtenerPartida().obtenerJugadores()[0];
        juego.presionarAccion(0, Accion.DERECHA);
        juego.actualizar(0.1);
        juego.soltarAccion(0, Accion.DERECHA);
        juego.actualizar(0.1);
        double xDetenido = j1.obtenerX();
        juego.actualizar(0.1);
        assertEquals(xDetenido, j1.obtenerX(), 0.0001);
    }

    @Test
    public void sePuedePausarReanudarYTerminar() throws DOPOSoccerException {
        DOPOSoccer juego = crearJuegoConPartida();
        juego.pausar();
        assertEquals(EstadoPartida.PAUSADO, juego.obtenerPartida().obtenerEstado());
        juego.reanudar();
        assertEquals(EstadoPartida.EN_JUEGO, juego.obtenerPartida().obtenerEstado());
        juego.terminarPartida();
        assertEquals(EstadoPartida.TERMINADO, juego.obtenerPartida().obtenerEstado());
        assertNull(juego.obtenerPartida().obtenerGanador());
    }

    @Test
    public void mejorarUnaEstadisticaUsaLosPuntosDelJugador() throws DOPOSoccerException {
        DOPOSoccer juego = crearJuego();
        juego.obtenerPerfil(1).sumarPuntos(500);
        juego.mejorarEstadistica(1, TipoEstadistica.SURVIVAL);
        assertEquals(2, juego.obtenerPerfil(1).obtenerEstadisticas().obtenerNivel(TipoEstadistica.SURVIVAL));
        assertEquals(0, juego.obtenerPerfil(1).obtenerPuntos());
    }

    @Test
    public void mejorarSinPuntosLanzaExcepcion() {
        DOPOSoccer juego = crearJuego();
        assertThrows(DOPOSoccerException.class, () -> juego.mejorarEstadistica(0, TipoEstadistica.KICK));
    }

    @Test
    public void lasMejorasSeNotanEnLaSiguientePartida() throws DOPOSoccerException {
        DOPOSoccer juego = crearJuego();
        juego.obtenerPerfil(1).sumarPuntos(500);
        juego.mejorarEstadistica(1, TipoEstadistica.SURVIVAL);
        juego.nuevaPartida(juego.obtenerModalidades().get(0), ResolucionCancha.PEQUENA,
                juego.obtenerPersonajes().get(2), juego.obtenerPersonajes().get(3),
                juego.obtenerTiposPartido().get(0), 1);
        assertEquals(110.0, juego.obtenerPartida().obtenerJugadores()[1].obtenerVidaMaxima(), 0.0001);
        assertEquals(100.0, juego.obtenerPartida().obtenerJugadores()[0].obtenerVidaMaxima(), 0.0001);
    }

    @Test
    public void losPuntosGanadosSeGuardanEnElPerfil() throws DOPOSoccerException {
        DOPOSoccer juego = crearJuegoConPartida();
        Partida partida = juego.obtenerPartida();
        for (int gol = 0; gol < 3; gol++) {
            partida.obtenerBalon().fijarPosicion(partida.obtenerCancha().obtenerAncho() - 35,
                    partida.obtenerCancha().obtenerSuelo() - 40);
            partida.obtenerBalon().fijarVelocidad(0, 0);
            for (int i = 0; i < 100; i++) {
                juego.actualizar(0.016);
            }
        }
        assertEquals(EstadoPartida.TERMINADO, partida.obtenerEstado());
        assertEquals(100, juego.obtenerPerfil(0).obtenerPuntos());
        assertEquals(25, juego.obtenerPerfil(1).obtenerPuntos());
    }
}
