package Models;

public class User {
    private int id_user;
    private String nombre;
    private String password;
    private int id_permisos;

    public User(int id, String n,int permisos){
        this.id_user = id;
        this.nombre = n;
        this.id_permisos = permisos;
    }

    public int getId_user() {
        return id_user;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPermisos() {
        // PermisosController.getPermByID(id) -> devuelve el modelo Permiso -> con getNombre() tenemos el nombre
        return "";
    }
}
