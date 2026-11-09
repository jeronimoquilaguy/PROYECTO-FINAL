package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EstadisticasTest {

    @Test
    public void todasEmpiezanEnNivelUno() {
        Estadisticas e = new Estadisticas();
        assertEquals(1, e.obtenerNivel(TipoEstadistica.SPEED));
        assertEquals(100.0, e.obtenerVidaMaxima(), 0.0001);
        assertEquals(1.0, e.obtenerMultiplicadorKick(), 0.0001);
    }

    @Test
    public void vidaMaximaConSurvivalTresEs120() throws DOPOSoccerException {
        Estadisticas e = new Estadisticas();
        e.subirNivel(TipoEstadistica.SURVIVAL);
        e.subirNivel(TipoEstadistica.SURVIVAL);
        assertEquals(120.0, e.obtenerVidaMaxima(), 0.0001);
    }

    @Test
    public void multiplicadorKickConKickCincoEs1punto4() throws DOPOSoccerException {
        Estadisticas e = new Estadisticas();
        for (int i = 0; i < 4; i++) {
            e.subirNivel(TipoEstadistica.KICK);
        }
        assertEquals(1.4, e.obtenerMultiplicadorKick(), 0.0001);
    }

    @Test
    public void elPrecioSeDuplicaConCadaMejora() throws DOPOSoccerException {
        Estadisticas e = new Estadisticas();
        assertEquals(500, e.obtenerPrecioMejora(TipoEstadistica.JUMP));
        e.subirNivel(TipoEstadistica.JUMP);
        assertEquals(1000, e.obtenerPrecioMejora(TipoEstadistica.JUMP));
        e.subirNivel(TipoEstadistica.JUMP);
        assertEquals(2000, e.obtenerPrecioMejora(TipoEstadistica.JUMP));
    }

    @Test
    public void noSePuedePasarDelNivelDiez() throws DOPOSoccerException {
        Estadisticas e = new Estadisticas();
        for (int i = 0; i < 9; i++) {
            e.subirNivel(TipoEstadistica.SPEED);
        }
        assertEquals(10, e.obtenerNivel(TipoEstadistica.SPEED));
        assertThrows(DOPOSoccerException.class, () -> e.subirNivel(TipoEstadistica.SPEED));
    }
}
