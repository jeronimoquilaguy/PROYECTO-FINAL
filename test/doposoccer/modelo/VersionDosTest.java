package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import doposoccer.persistencia.AlmacenamientoPartida;

public class VersionDosTest {

    private DOPOSoccer crearModelo() {
        List<Personaje> personajes = new ArrayList<Personaje>();
        personajes.add(new CoreaDelSur());
        personajes.add(new Camerun());
        List<Modalidad> modalidades = new ArrayList<Modalidad>();
        modalidades.add(new JugadorVsJugador());
        modalidades.add(new JugadorVsMaquina());
        List<FabricaTipoPartido> tipos = new ArrayList<FabricaTipoPartido>();
        tipos.add(new FabricaPorTiempo());
        tipos.add(new FabricaPorGoles());
        return new DOPOSoccer(personajes, modalidades, tipos);
    }

    @Test
    public void creaLaModalidadConElPerfilDeMaquinaSeleccionado() throws Exception {
        DOPOSoccer modelo = crearModelo();
        modelo.nuevaPartida(new JugadorVsMaquina(PerfilMaquina.EXPERTA),
                ResolucionCancha.MEDIANA, new CoreaDelSur(), new Camerun(),
                new FabricaPorGoles(), 3);
        Jugador[] jugadores = modelo.obtenerPartida().obtenerJugadores();
        assertTrue(jugadores[0].obtenerControl() instanceof ControlHumano);
        assertTrue(jugadores[1].obtenerControl() instanceof ControlMaquina);
        assertEquals(PerfilMaquina.EXPERTA,
                ((ControlMaquina) jugadores[1].obtenerControl()).obtenerPerfil());
        for (int i = 0; i < 20; i++) {
            modelo.actualizar(0.016);
        }
        assertTrue(Double.isFinite(jugadores[1].obtenerX()));
    }

    @Test
    public void guardaYAbreElModeloConLaPartida() throws Exception {
        DOPOSoccer modelo = crearModelo();
        modelo.nuevaPartida(new JugadorVsMaquina(PerfilMaquina.DEFENSIVA),
                ResolucionCancha.MEDIANA, new CoreaDelSur(), new Camerun(),
                new FabricaPorGoles(), 3);
        modelo.obtenerPerfil(0).sumarPuntos(75);
        modelo.obtenerPartida().obtenerBalon().fijarPosicion(111, 222);
        File archivo = File.createTempFile("dopo-partida", ".dopo");
        new AlmacenamientoPartida().guardar(modelo, archivo);
        DOPOSoccer cargado = new AlmacenamientoPartida().cargar(archivo);
        assertNotNull(cargado.obtenerPartida());
        assertEquals(75, cargado.obtenerPerfil(0).obtenerPuntos());
        assertEquals(111, cargado.obtenerPartida().obtenerBalon().obtenerX(), 0.001);
        assertEquals(222, cargado.obtenerPartida().obtenerBalon().obtenerY(), 0.001);
        assertEquals(PerfilMaquina.DEFENSIVA,
                ((ControlMaquina) cargado.obtenerPartida().obtenerJugadores()[1]
                        .obtenerControl()).obtenerPerfil());
        archivo.delete();
    }
    @Test
    public void losCuatroPerfilesEstanDisponibles() {
        assertEquals(4, PerfilMaquina.values().length);
        assertEquals("Torpe", PerfilMaquina.TORPE.obtenerNombre());
        assertEquals("Ofensiva", PerfilMaquina.OFENSIVA.obtenerNombre());
        assertEquals("Defensiva", PerfilMaquina.DEFENSIVA.obtenerNombre());
        assertEquals("Experta", PerfilMaquina.EXPERTA.obtenerNombre());
    }

}
