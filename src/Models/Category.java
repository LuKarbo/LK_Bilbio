package Models;

public class Category {
    private int id_category;
    private String nombre;

    public Category(int id_category, String nombre) {
        this.id_category = id_category;
        this.nombre = nombre;
    }

    public int getId_category() {
        return id_category;
    }

    public String getNombre() {
        return nombre;
    }
}
