package Controllers;

import Models.Book;
import Models.Category;
import Models.Prestamo;
import Models.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PrestamoController extends Controller {
    // Create Prestamo
    public boolean createPrestamo(int idUser, int idBook) {
        String sql = "INSERT INTO prestamo (id_user, id_book, inicioDate, status) VALUES (?, ?, ?,1)";
        // Agrego data al sql
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, idUser);
            stmt.setInt(2, idBook);

            // Consigo la fecha actual
            java.util.Date fechaActual = new java.util.Date();
            // la paso para el SQL
            java.sql.Date fechaActualSQL = new java.sql.Date(fechaActual.getTime());
            // mando el dato
            stmt.setDate(3, fechaActualSQL);

            if (stmt.executeUpdate() > 0) {
                // Actualizar el estado de los libros que se agregaron
                BookController bc = new BookController();
                if (bc.editStatusBook(idBook, false)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    // Update PrestamoStatus
    public boolean editPrestamoStatus(int id, boolean newStatus) {
        String sql = "UPDATE prestamo SET status = ? WHERE id_prestamo = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setBoolean(1, newStatus);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                BookController bc = new BookController();
                sql = "Select id_book From prestamo WHERE id_prestamo = ?";
                try (PreparedStatement stmt2 = db.prepareStatement(sql)) {
                    stmt2.setInt(1, id);
                    ResultSet rs = stmt2.executeQuery();
                    if (rs.next()) {
                        return bc.editStatusBook(rs.getInt("id_book"), true);
                    } else {
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }



    public <T> Prestamo getPrestamo(String columna, T value){
        String sql = "SELECT * FROM prestamo WHERE " + columna + " = ?";
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
                    return new Prestamo(rs.getInt("id_prestamo"),rs.getInt("id_user"),rs.getInt("id_book"),rs.getDate("inicioDate"),rs.getBoolean("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Historial (Todos los prestamos)
    public Object[][] getPrestamosList(int option) {
        String sql = "SELECT * FROM prestamo";
        if(option == 0){
            sql += " WHERE status = 1";
        }
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<Object[]> dataList = new ArrayList<>();
                while (rs.next()) {
                    UserController uc = new UserController();
                    String userName = uc.getUser("id_user",rs.getInt("id_user")).getNombre();

                    BookController bc = new BookController();
                    ArrayList<Book> books = bc.getBookWithList("id_book",rs.getInt("id_book"));
                    if (!books.isEmpty()) {
                        Collections.sort(books, Comparator.comparingInt(Book::getId_book));
                        Book book = books.get(0);

                        // Crear la fila de datos para el resultado
                        Object[] row = {
                                rs.getInt("id_prestamo"),
                                userName,
                                book.getTitulo(),
                                rs.getString("inicioDate"),
                                rs.getBoolean("status") ? "Activo" : "Desactivado"
                        };
                        dataList.add(row);
                    }
                }
                return dataList.toArray(new Object[0][0]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Registro (Mostrar: Libro más solicitado, Días con más prestamos, Cliente con más Prestamos)
    public Object[] getRegistros(){
        Object[] resultado = new Object[3];
        // Realizo la consulta, para obtener: Libro más solicitado, Días con más prestamos, Cliente con más Prestamos
        String sql = "SELECT " +
                " (SELECT nombre FROM user WHERE id_user = id_user_max) AS nombre_usuario, " +
                "  (SELECT titulo FROM book WHERE id_book = id_book_max) AS nombre_libro, " +
                " fecha_prestamos" +
                " FROM ( SELECT id_user AS id_user_max,  id_book AS id_book_max, inicioDate AS fecha_prestamos" +
                " FROM prestamo" +
                " GROUP BY id_user, id_book, inicioDate" +
                " ORDER BY COUNT(*) DESC" +
                " LIMIT 1 ) AS subconsulta_maximos";

        try (PreparedStatement stmt = db.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nameUser = rs.getString("nombre_usuario");
                String nameLibro = rs.getString("nombre_libro");
                Date fecha = rs.getDate("fecha_prestamos");

                resultado[0] = nameUser;
                resultado[1] = nameLibro;
                resultado[2] = fecha;
                return resultado;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
