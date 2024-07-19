package Controllers;

import Models.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryController  extends Controller{

    // Get Category List
    public ArrayList<Category> getCategoryList(){
        ArrayList<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category(rs.getInt("id_category"), rs.getString("nombre"));
                    categoryList.add(category);
                }
                return categoryList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get Category
    public <T> Category getCategory(String columna,T value){
        String sql = "SELECT * FROM category WHERE " + columna + " = ?";
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
                    return new Category(rs.getInt("id_category"),rs.getString("nombre"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Create Category
    public boolean createCategory(String name) {
        // Compruebo que no exista ninguno con ese nombre:
        if (getCategory("nombre", name) == null) {
            String sql = "INSERT INTO category (nombre) VALUES (?)";
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

    // Update Category
    public boolean editCategory(int id, String name) {
        if (getCategory("id_category", id) != null) {
            String sql = "UPDATE category SET nombre = ? WHERE id_category = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setInt(2, id);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    // Delete Category
    public boolean deleteCategory(int id) {
        if (getCategory("id_category", id) != null) {
            String sql = "DELETE FROM category WHERE id_category = ?";
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

    // Add Category a Book
    public boolean setCategoriesForBook(ArrayList<Integer> ids, int bookId){
        String sql = "INSERT INTO category_book (id_book, id_category) VALUES (?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            for (Integer categoryId : ids) {
                stmt.setInt(1, bookId);
                stmt.setInt(2, categoryId);
                // los agrupo
                stmt.addBatch();
            }
            // ejecuto los insert
            int[] resultados = stmt.executeBatch();
            return resultados.length > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Buscar Category de un libro
    public ArrayList<String> getCategoriesForBook(int bookId) {
        // busco las categorias del libre
        String sql = "SELECT id_category FROM category_book WHERE id_book = ?";
        // creo la lista para pasarsela al Book
        ArrayList<String> categoryNames = new ArrayList<>();
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int categoryId = rs.getInt("id_category");
                // Busco la categoria para guardar su nombre en la lista
                Category category = getCategory("id_category", categoryId);
                if (category != null) {
                    categoryNames.add(category.getNombre());
                }
            }
            return categoryNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryNames;
    }

    // Sacar una categoria a un libro
    public boolean removeCategoryForBook(int bookId, int categoryId) {
        String sql = "DELETE FROM category_book WHERE id_book = ? AND id_category = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, categoryId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
