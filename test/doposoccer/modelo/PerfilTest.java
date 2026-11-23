package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PerfilTest {

    @Test
    public void sinPuntosSuficientesNoSeMejora() {
        Perfil p = new Perfil("Ana");
        p.sumarPuntos(100);
        DOPOSoccerException e = assertThrows(DOPOSoccerException.class,
                () -> p.mejorar(TipoEstadistica.KICK));
        assertTrue(e.getMessage().contains("Puntos insuficientes"));
        assertEquals(1, p.obtenerEstadisticas().obtenerNivel(TipoEstadistica.KICK));
        assertEquals(100, p.obtenerPuntos());
    }

    @Test
    public void mejorarDescuentaLosPuntos() throws DOPOSoccerException {
        Perfil p = new Perfil("Ana");
        p.sumarPuntos(600);
        p.mejorar(TipoEstadistica.KICK);
        assertEquals(2, p.obtenerEstadisticas().obtenerNivel(TipoEstadistica.KICK));
        assertEquals(100, p.obtenerPuntos());
    }
}
