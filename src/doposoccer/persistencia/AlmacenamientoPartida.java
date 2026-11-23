package doposoccer.persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import doposoccer.modelo.DOPOSoccer;
import doposoccer.modelo.DOPOSoccerException;

/** Guarda y recupera el estado completo del modelo usando serializacion de Java. */
public class AlmacenamientoPartida {

    public void guardar(DOPOSoccer modelo, File archivo) throws IOException, DOPOSoccerException {
        if (modelo == null || modelo.obtenerPartida() == null) {
            throw new DOPOSoccerException("No hay una partida para guardar.");
        }
        if (archivo == null) throw new IOException("Debe elegir un archivo.");
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(archivo))) {
            salida.writeObject(modelo);
        }
    }

    public DOPOSoccer cargar(File archivo) throws IOException, ClassNotFoundException {
        if (archivo == null || !archivo.isFile()) throw new IOException("No existe el archivo seleccionado.");
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(archivo))) {
            Object objeto = entrada.readObject();
            if (!(objeto instanceof DOPOSoccer)) throw new IOException("El archivo no es una partida de DOPOSoccer.");
            return (DOPOSoccer) objeto;
        }
    }
}
