package Controllers;

import DB.DBConnection;
import Models.Permiso;

import javax.naming.spi.ObjectFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class Controller {
    protected Connection db;
    {
        try {
            db = DBConnection.getDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Get info
    protected <T> T getInfo(String tableName, String columna, Object value, Function<ResultSet, T> result) {
        // Recibo la tabla, columna y valor al que debo buscar, asi tambien el ResultSet con el que debo de conseguir los datos
        String sql = "SELECT * FROM " + tableName + " WHERE " + columna + " = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {

            if (value instanceof String) {
                stmt.setString(1, (String) value);
            } else if (value instanceof Integer) {
                stmt.setInt(1, (Integer) value);
            } else {
                return null;
            }
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // uso mi ResultSet para crear el resultado que necesito segun la clase desde la cual venga
                    return result.apply(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
