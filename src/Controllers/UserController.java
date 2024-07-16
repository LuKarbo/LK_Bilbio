package Controllers;

import Models.User;

import javax.xml.transform.Templates;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserController  extends Controller{

    // login
    public User loginAction(String name, String pass) {
        // hasheo la contraseña que el usuario ingreso
        String passHashed = hashPassword(pass);
        if(passHashed.equals("")){
            return null;
        }

        // Busco al usuario en la BBDD
        String sql = "SELECT * FROM user WHERE nombre = ? AND contrasena = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, passHashed);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // retorno el usuario
                    return new User(rs.getInt("id_user"), rs.getString("nombre"), rs.getInt("id_permisos"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get user List
    public ArrayList<User> getUserList(){
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User(rs.getInt("id_user"), rs.getString("nombre"), rs.getInt("id_permisos"));
                    userList.add(user);
                }
                return userList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get User By X
    public <T> User getUser(String columna,T value) {
        String sql = "SELECT * FROM user WHERE " + columna + " = ?";
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
                    return new User(rs.getInt("id_user"),rs.getString("nombre"),rs.getInt("id_permisos"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Create User
    public boolean createUser(String name, String pass) {
        // Compruebo que no exista ninguno con ese nombre:
        if (getUser("nombre", name) == null) {
            String sql = "INSERT INTO user (nombre, contrasena, id_permisos) VALUES (?, ?, ?)";

            // Hashear la contraseña
            String passHashed = hashPassword(pass);
            if(passHashed.equals("")){
                return false;
            }

            // Agrego data al sql
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, passHashed);
                stmt.setInt(3, 1);

                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    // Update User
    public boolean editUser(int id,String name, int permisos){
        if(getUser("id_user",id)!=null){
            String sql = "UPDATE user SET nombre = ?, id_permisos = ? WHERE id_user = ?";

            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setInt(2, permisos);
                stmt.setInt(3, id);

                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
        else{
            return false;
        }
    }

    // Delete User
    public boolean deleteUser(int id) {
        if (getUser("id_user", id) != null) {
            String sql = "DELETE FROM user WHERE id_user = ?";
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

    // Hasheo de pass
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

}
