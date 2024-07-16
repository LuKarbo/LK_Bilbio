package Controllers;

import Models.Book;
import Models.Book;
import Models.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    // Get Book
    public <T> ArrayList<Book> getBookWithList(String columna, T value) {
        String sql = "SELECT * FROM category WHERE " + columna + " = ?";
        ArrayList<Book> bookList = new ArrayList<>();
        try (PreparedStatement stmt = db.prepareStatement(sql)) {

            if (value instanceof String) {
                stmt.setString(1, (String) value);
            } else if (value instanceof Integer) {
                stmt.setInt(1, (Integer) value);
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
    public boolean createBook(String title,String autor, double price) {
        String sql = "INSERT INTO book (titulo,autor,price,status) VALUES (?,?,?,1)";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, autor);
            stmt.setDouble(3, price);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update Book
    public boolean editBook(int id, String name,String title,String autor, double price) {
        ArrayList<Book> books = getBookWithList("id_book", id);
        if (books != null && !books.isEmpty()) {
            String sql = "UPDATE book SET titulo = ?,autor = ?,price = ? WHERE id_book = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, title);
                stmt.setString(2, autor);
                stmt.setDouble(3, price);
                stmt.setInt(4, id);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }
        return false;
    }

    // Update Status Book
    public boolean editBook(int id, boolean status) {
        ArrayList<Book> books = getBookWithList("id_book", id);
        if (books != null && !books.isEmpty()) {
            String sql = "UPDATE book SET status = ? WHERE id_book = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setBoolean(1, status);
                stmt.setInt(4, id);
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
    public boolean deleteCategory(int id) {
        ArrayList<Book> books = getBookWithList("id_book", id);
        if (books != null && !books.isEmpty()) {
            for (Book book:books) {
                // verifico que este disponible, ya que si esta en un prestamo o no disponible, no se debe de poder borrar
                if(book.getId_book() == id && book.getStatus()){
                    String sql = "DELETE FROM book WHERE id_book = ?";
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

    // Create a Book List


}
