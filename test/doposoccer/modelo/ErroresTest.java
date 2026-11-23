package doposoccer.modelo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;

/** Pruebas de la excepcion propia del juego y del log de errores. */
public class ErroresTest {

    @Test
    public void laExcepcionGuardaElMensaje() {
        DOPOSoccerException e = new DOPOSoccerException(DOPOSoccerException.NIVEL_MAXIMO);
        assertEquals(DOPOSoccerException.NIVEL_MAXIMO, e.getMessage());
    }

    @Test
    public void laExcepcionPuedeGuardarLaCausa() {
        Exception causa = new IllegalStateException("causa");
        DOPOSoccerException e = new DOPOSoccerException("mensaje", causa);
        assertSame(causa, e.getCause());
    }

    @Test
    public void elLogGuardaElErrorEnElArchivo() throws Exception {
        String mensaje = "Prueba del log " + System.nanoTime();
        RegistroErrores.registrar(new DOPOSoccerException(mensaje));
        File archivo = new File("errores.log");
        assertTrue(archivo.exists());
        String contenido = new String(Files.readAllBytes(archivo.toPath()), "UTF-8");
        assertTrue(contenido.contains(mensaje));
        assertTrue(contenido.contains("DOPOSoccerException"));
    }

    @Test
    public void losTiposDePartidoSinRelojNoTienenMuerteSubita() throws DOPOSoccerException {
        TipoPartido tipo = new PorGoles(3);
        Cancha cancha = new Cancha(ResolucionCancha.MEDIANA);
        Jugador a = new Jugador(Lado.IZQUIERDO, new Camerun(), new Perfil("A"), new ControlHumano(), cancha);
        Jugador b = new Jugador(Lado.DERECHO, new Nigeria(), new Perfil("B"), new ControlHumano(), cancha);
        assertFalse(tipo.estaEnMuerteSubita(new Marcador(a, b)));
        tipo.actualizar(100);
        assertFalse(tipo.haTerminado(new Marcador(a, b)));
    }
}
