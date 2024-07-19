package Controllers;

import Models.Permiso;
import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PermisosController  extends Controller{
    // Get Lista de permisos
    public ArrayList<Permiso> getPermisoList(){
        ArrayList<Permiso> permisoList = new ArrayList<>();
        String sql = "SELECT * FROM permisos";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Permiso permiso = new Permiso(rs.getInt("id_permisos"), rs.getString("nombre"));
                    permisoList.add(permiso);
                }
                return permisoList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get Permiso por ID [PermisosController.getPermByID(id) -> devuelve permiso (Model: Permiso)]
    public <T> Permiso getPermiso(String columna,T value){
        String sql = "SELECT * FROM permisos WHERE " + columna + " = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            // Verifico que es lo que se va a buscar
            if (value instanceof String) {
                stmt.setString(1, (String) value);
            } else if (value instanceof Integer) {
                stmt.setInt(1, (Integer) value);
            } else {
                return null;
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Permiso(rs.getInt("id_permisos"),rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Create Permiso
    public boolean createPermiso(String name) {
        // Compruebo que no exista ninguno con ese nombre:
        if (getPermiso("nombre", name) == null) {
            String sql = "INSERT INTO permisos (nombre) VALUES (?)";
            // Agrego data al sql
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, name);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    // Delete Permiso
    public boolean deletePermiso(int id) {
        if (getPermiso("id_permisos", id) != null) {
            String sql = "DELETE FROM permisos WHERE id_permisos = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }
}
