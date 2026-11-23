package doposoccer.modelo;

/** Perfiles de comportamiento disponibles para el jugador controlado por la maquina. */
public enum PerfilMaquina {
    TORPE("Torpe"),
    OFENSIVA("Ofensiva"),
    DEFENSIVA("Defensiva"),
    EXPERTA("Experta");

    private String nombre;

    PerfilMaquina(String nombre) {
        this.nombre = nombre;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public String toString() {
        return nombre;
    }
}
