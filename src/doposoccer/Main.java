package doposoccer;

import java.util.ArrayList;
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
import doposoccer.modelo.Modalidad;
import doposoccer.modelo.Nigeria;
import doposoccer.modelo.Personaje;

/**
 * Punto de entrada. Aqui se "arma" el juego: para agregar un personaje, una modalidad o
 * un tipo de partido nuevo solo se agrega una linea en este archivo.
 */
public class Main {

    public static void main(String[] args) {
        List<Personaje> personajes = new ArrayList<>();
        personajes.add(new CoreaDelSur());
        personajes.add(new Camerun());
        personajes.add(new Nigeria());
        personajes.add(new EstadosUnidos());

        List<Modalidad> modalidades = new ArrayList<>();
        modalidades.add(new JugadorVsJugador());

        List<FabricaTipoPartido> tipos = new ArrayList<>();
        tipos.add(new FabricaPorTiempo());
        tipos.add(new FabricaPorGoles());

        final DOPOSoccer modelo = new DOPOSoccer(personajes, modalidades, tipos);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ControladorDOPOSoccer controlador = new ControladorDOPOSoccer(modelo);
                controlador.iniciar();
            }
        });
    }
}
