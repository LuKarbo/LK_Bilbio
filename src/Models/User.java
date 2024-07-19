package Models;

import Controllers.PermisosController;

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

    public int getPermisos() {
        return this.id_permisos;
    }

    public String getPermisosString() {
        PermisosController pc = new PermisosController();
        return pc.getPermiso("id_permisos",this.id_permisos).getNombre();
    }
}
