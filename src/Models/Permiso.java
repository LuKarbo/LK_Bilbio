package Models;

public class Permiso {
    private int id_permisos;
    private String nombre;

    public Permiso(int id, String n) {
        this.id_permisos = id;
        this.nombre = n;
    }

    public int getId_permisos() {
        return id_permisos;
    }

    public String getNombre() {
        return nombre;
    }
}
