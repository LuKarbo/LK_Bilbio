package Controllers;

import DB.DBConnection;
import Models.Permiso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {
    protected Connection db;
    {
        try {
            db = DBConnection.getDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
