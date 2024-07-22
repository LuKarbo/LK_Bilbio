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

    // Get Permiso
    public <T> Permiso getPermiso(String columna,T value){
        return getInfo("permisos", columna, value, rs -> {  // buscar más sobre "Expresión Lambda"
            try {
                return new Permiso(rs.getInt("id_permisos"), rs.getString("nombre"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
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
