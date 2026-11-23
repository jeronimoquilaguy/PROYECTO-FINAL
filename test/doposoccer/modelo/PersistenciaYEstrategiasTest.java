package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import doposoccer.persistencia.AlmacenamientoPartida;
import doposoccer.persistencia.AlmacenamientoProgreso;

/** Pruebas adicionales del ciclo 2: progreso, partida e interfaces extensibles. */
public class PersistenciaYEstrategiasTest {
    private DOPOSoccer crear() {
        List<Personaje> personajes = new ArrayList<Personaje>();
        personajes.add(new CoreaDelSur());
        personajes.add(new Camerun());
        List<Modalidad> modalidades = new ArrayList<Modalidad>();
        modalidades.add(new JugadorVsMaquina());
        List<FabricaTipoPartido> tipos = new ArrayList<FabricaTipoPartido>();
        tipos.add(new FabricaPorGoles());
        return new DOPOSoccer(personajes, modalidades, tipos);
    }

    private DOPOSoccer crearPartida(PerfilMaquina perfil) throws Exception {
        DOPOSoccer modelo = crear();
        modelo.nuevaPartida(new JugadorVsMaquina(perfil), ResolucionCancha.MEDIANA,
                new CoreaDelSur(), new Camerun(), new FabricaPorGoles(), 5);
        return modelo;
    }

    @Test
    public void progresoPersisteSinPartida() throws Exception {
        DOPOSoccer modelo = crear();
        modelo.obtenerPerfil(0).sumarPuntos(700);
        modelo.mejorarEstadistica(0, TipoEstadistica.SPEED);
        File archivo = File.createTempFile("dopo-progreso", ".dopo");
        try {
            AlmacenamientoProgreso deposito = new AlmacenamientoProgreso(archivo);
            deposito.guardar(modelo.obtenerPerfiles());
            DOPOSoccer otroInicio = crear();
            otroInicio.restaurarPerfiles(deposito.cargar());
            assertEquals(200, otroInicio.obtenerPerfil(0).obtenerPuntos());
            assertEquals(2, otroInicio.obtenerPerfil(0).obtenerEstadisticas().obtenerNivel(TipoEstadistica.SPEED));
            assertNull(otroInicio.obtenerPartida());
        } finally { archivo.delete(); }
    }

    @Test
    public void cargaPerfilesRechazaArchivoIncorrecto() throws Exception {
        File archivo = File.createTempFile("dopo-incorrecto", ".dopo");
        try {
            new AlmacenamientoPartida().guardar(crearPartida(PerfilMaquina.TORPE), archivo);
            assertThrows(IOException.class, () -> new AlmacenamientoProgreso(archivo).cargar());
        } finally { archivo.delete(); }
    }

    @Test
    public void partidaPausadaSeRecuperaPausada() throws Exception {
        DOPOSoccer modelo = crearPartida(PerfilMaquina.DEFENSIVA);
        modelo.pausar();
        File archivo = File.createTempFile("dopo-pausa", ".dopo");
        try {
            AlmacenamientoPartida almacenamiento = new AlmacenamientoPartida();
            almacenamiento.guardar(modelo, archivo);
            DOPOSoccer cargado = almacenamiento.cargar(archivo);
            assertEquals(EstadoPartida.PAUSADO, cargado.obtenerPartida().obtenerEstado());
            cargado.reanudar();
            assertEquals(EstadoPartida.EN_JUEGO, cargado.obtenerPartida().obtenerEstado());
        } finally { archivo.delete(); }
    }

    @Test
    public void guardarPartidaSinPartidaMuestraError() throws Exception {
        File archivo = File.createTempFile("dopo-vacio", ".dopo");
        try {
            assertThrows(DOPOSoccerException.class, () -> new AlmacenamientoPartida().guardar(crear(), archivo));
        } finally { archivo.delete(); }
    }

    @Test
    public void cuatroEstrategiasPuedenJugar() throws Exception {
        for (PerfilMaquina perfil : PerfilMaquina.values()) {
            DOPOSoccer modelo = crearPartida(perfil);
            for (int i = 0; i < 50; i++) modelo.actualizar(0.016);
            assertTrue(Double.isFinite(modelo.obtenerPartida().obtenerJugadores()[1].obtenerX()));
        }
    }

    @Test
    public void expertoSeDefiendeSiVaGanando() throws Exception {
        DOPOSoccer modelo = crearPartida(PerfilMaquina.EXPERTA);
        Partida partida = modelo.obtenerPartida();
        Jugador maquina = partida.obtenerJugadores()[1];
        partida.obtenerMarcador().sumarGol(maquina);
        partida.obtenerBalon().fijarPosicion(100, 100);
        Set<Accion> acciones = maquina.obtenerControl().obtenerAcciones(maquina, partida);
        assertEquals(new EstrategiaDefensiva().decidir(maquina, partida), acciones);
    }

    @Test
    public void sePuedeAgregarEstrategiaSinCambiarElMotor() throws Exception {
        EstrategiaMaquina nueva = new EstrategiaMaquina() {
            public Set<Accion> decidir(Jugador jugador, Partida partida) {
                Set<Accion> acciones = new HashSet<Accion>();
                acciones.add(Accion.SALTAR);
                return acciones;
            }
        };
        DOPOSoccer modelo = crearPartida(PerfilMaquina.EXPERTA);
        Jugador jugador = modelo.obtenerPartida().obtenerJugadores()[1];
        ControlMaquina control = new ControlMaquina(PerfilMaquina.EXPERTA, nueva);
        assertTrue(control.obtenerAcciones(jugador, modelo.obtenerPartida()).contains(Accion.SALTAR));
    }
}
