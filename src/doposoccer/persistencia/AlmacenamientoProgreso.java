package doposoccer.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import doposoccer.modelo.Perfil;

/** Almacena puntos y mejoras independientemente de una partida guardada. */
public class AlmacenamientoProgreso {
    private File archivo;

    public AlmacenamientoProgreso(File archivo) { this.archivo = archivo; }

    public void guardar(Perfil[] perfiles) throws IOException {
        if (perfiles == null || perfiles.length != 2 || perfiles[0] == null || perfiles[1] == null) {
            throw new IOException("Los perfiles de jugador no son validos.");
        }
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
            salida.writeObject(perfiles);
        }
    }

    public Perfil[] cargar() throws IOException, ClassNotFoundException {
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
            Object leido = entrada.readObject();
            if (!(leido instanceof Perfil[])) throw new IOException("Formato de progreso incorrecto.");
            Perfil[] perfiles = (Perfil[]) leido;
            if (perfiles.length != 2 || perfiles[0] == null || perfiles[1] == null) {
                throw new IOException("El archivo no tiene los dos perfiles.");
            }
            return perfiles;
        }
    }
}
