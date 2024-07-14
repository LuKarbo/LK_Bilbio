package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/lk-bilbio";
    private static final String userName = "root";
    private static final String userPass = "";
    private static Connection db;

    private DBConnection(){}

    public static Connection getDB() throws SQLException {
        if (db == null || db.isClosed()) {
            try {
                db = DriverManager.getConnection(url, userName, userPass);
                db.setAutoCommit(false);
            } catch (SQLException e) {
                throw new SQLException("Error al conectar a la base de datos", e);
            }
        }
        return db;
    }
}
