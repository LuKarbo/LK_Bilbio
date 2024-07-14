package Controllers;

import DB.DBConnection;

import java.sql.Connection;
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
