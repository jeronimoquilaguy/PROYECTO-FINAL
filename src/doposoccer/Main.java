package doposoccer;

import java.util.ArrayList;
import java.io.File;
import doposoccer.persistencia.AlmacenamientoProgreso;
import doposoccer.modelo.RegistroErrores;
import java.util.List;
import javax.swing.SwingUtilities;

import doposoccer.controlador.ControladorDOPOSoccer;
import doposoccer.modelo.Camerun;
import doposoccer.modelo.CoreaDelSur;
import doposoccer.modelo.DOPOSoccer;
import doposoccer.modelo.EstadosUnidos;
import doposoccer.modelo.FabricaPorGoles;
import doposoccer.modelo.FabricaPorTiempo;
import doposoccer.modelo.FabricaTipoPartido;
import doposoccer.modelo.JugadorVsJugador;
import doposoccer.modelo.JugadorVsMaquina;
import doposoccer.modelo.Modalidad;
import doposoccer.modelo.Nigeria;
import doposoccer.modelo.Personaje;

/**
 * Punto de entrada. Aqui se "arma" el juego: para agregar un personaje, una modalidad o
 * un tipo de partido nuevo solo se agrega una linea en este archivo.
 */
public class Main {

    /**
     * Realiza la operación de main.
     * @param args valor de args.
     */
    public static void main(String[] args) {
        List<Personaje> personajes = new ArrayList<>();
        personajes.add(new CoreaDelSur());
        personajes.add(new Camerun());
        personajes.add(new Nigeria());
        personajes.add(new EstadosUnidos());

        List<Modalidad> modalidades = new ArrayList<>();
        modalidades.add(new JugadorVsJugador());
        modalidades.add(new JugadorVsMaquina());

        List<FabricaTipoPartido> tipos = new ArrayList<>();
        tipos.add(new FabricaPorTiempo());
        tipos.add(new FabricaPorGoles());

        final DOPOSoccer modelo = new DOPOSoccer(personajes, modalidades, tipos);

        // Recupera las mejoras aun cuando no se abre una partida anterior.
        File archivoProgreso = new File("progreso.dopo");
        if (archivoProgreso.exists()) {
            try {
                modelo.restaurarPerfiles(new AlmacenamientoProgreso(archivoProgreso).cargar());
            } catch (Exception error) {
                RegistroErrores.registrar(error);
                System.err.println("No se pudo recuperar el progreso: " + error.getMessage());
            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            /**
             * Realiza la operación de run.
             */
            public void run() {
                ControladorDOPOSoccer controlador = new ControladorDOPOSoccer(modelo);
                controlador.iniciar();
            }
        });
    }
}
