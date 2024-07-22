package Controllers;

import Models.Book;
import Models.Prestamo;
import Models.User;

import java.sql.*;
import java.util.ArrayList;

public class BookController extends Controller{
    // Get Book List
    public ArrayList<Book> getBookList(){
        ArrayList<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                CategoryController cc = new CategoryController();
                while (rs.next()) {
                    Book book = new Book(rs.getInt("id_book"), rs.getString("titulo"), rs.getString("autor"), rs.getDouble("price"), rs.getBoolean("status"));
                    book.setCategories(cc.getCategoriesForBook(book.getId_book()));
                    bookList.add(book);
                }
                return bookList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get Book por columna y valor
    public <T> ArrayList<Book> getBookWithList(String columna, T value) {
        String sql = "SELECT * FROM book WHERE " + columna + " = ? OR " + columna + " LIKE ?";
        ArrayList<Book> bookList = new ArrayList<>();
        try (PreparedStatement stmt = db.prepareStatement(sql)) {

            if (value instanceof String) {
                stmt.setString(1, (String) value);
                stmt.setString(2, "%" + value + "%");
                System.out.println(sql);
            } else if (value instanceof Integer) {
                stmt.setInt(1, (Integer) value);
                stmt.setInt(2, (Integer) value);
                System.out.println(sql);
            } else {
                return null;
            }

            try (ResultSet rs = stmt.executeQuery()) {
                CategoryController cc = new CategoryController();
                while (rs.next()) {
                    Book book = new Book(rs.getInt("id_book"), rs.getString("titulo"), rs.getString("autor"), rs.getDouble("price"), rs.getBoolean("status"));
                    book.setCategories(cc.getCategoriesForBook(book.getId_book()));
                    bookList.add(book);
                }
                return bookList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Create Book
    public boolean createBook(String title, String autor, double price, ArrayList<Integer> categorias) {

        String sql = "INSERT INTO book (titulo, autor, price, status) VALUES (?, ?, ?, 1)";

        try (PreparedStatement stmt = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, title);
            stmt.setString(2, autor);
            stmt.setDouble(3, price);

            // Ejecutar la consulta de inserción
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Consigo el id que se genero
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int bookId = generatedKeys.getInt(1);
                    CategoryController cc = new CategoryController();
                    return cc.setCategoriesForBook(categorias, bookId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update Book
    public boolean editBook(int id, String title, String autor, double price, boolean status) {
        PrestamoController pc = new PrestamoController();
        Prestamo prestamo = pc.getPrestamo("id_book", id);
        if( prestamo != null && prestamo.isStatus()){
            return false;
        }
        else{
            ArrayList<Book> books = getBookWithList("id_book", id);
            if (books != null && !books.isEmpty()) {
                String sql = "UPDATE book SET titulo = ?,autor = ?,price = ? WHERE id_book = ?";
                try (PreparedStatement stmt = db.prepareStatement(sql)) {
                    stmt.setString(1, title);
                    stmt.setString(2, autor);
                    stmt.setDouble(3, price);
                    stmt.setInt(4, id);
                    if(stmt.executeUpdate() > 0){
                        return editStatusBook(id,status);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else {
                return false;
            }
        }
        return false;
    }

    // Update Status Book
    public boolean editStatusBook(int id, boolean status) {
        ArrayList<Book> books = getBookWithList("id_book", id);
        if (books != null && !books.isEmpty()) {
            String sql = "UPDATE book SET status = ? WHERE id_book = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setBoolean(1, status);
                stmt.setInt(2, id);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }
        return false;
    }

    // Delete Book
    public boolean deleteBook(int id) {
        ArrayList<Book> books = getBookWithList("id_book", id);
        if (books != null && !books.isEmpty()) {
            for (Book book:books) {
                // verifico que este disponible, ya que si esta en un prestamo o no disponible, no se debe de poder borrar
                if(book.getId_book() == id && book.getStatus()){
                    String sql = "DELETE FROM book WHERE id_book = ?"; // preparar para eliminar la foren cuando se elimina
                    try (PreparedStatement stmt = db.prepareStatement(sql)) {
                        stmt.setInt(1, id);
                        return stmt.executeUpdate() > 0;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            return false;
        }
        return false;
    }

}
