package doposoccer.modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/** Escribe los errores en el archivo errores.log para que los programadores los revisen. */
public class RegistroErrores {

    private static final String ARCHIVO = "errores.log";

    public static void registrar(Exception e) {
        try {
            FileWriter escritor = new FileWriter(ARCHIVO, true);
            PrintWriter salida = new PrintWriter(escritor);
            salida.println("[" + new Date() + "] " + e.getClass().getSimpleName() + ": " + e.getMessage());
            e.printStackTrace(salida);
            salida.println();
            salida.close();
        } catch (IOException ex) {
            System.err.println("No se pudo escribir en el log: " + ex.getMessage());
        }
    }
}
