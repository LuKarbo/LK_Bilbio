package Controllers.Tables;

import Controllers.BookController;
import Controllers.GenerarTablas;
import Models.Book;

import javax.swing.JTable;
import java.util.ArrayList;

public class GenerarGetBookTable extends GenerarTablas {

    private JTable table;
    private String searchTerm;

    public GenerarGetBookTable(JTable table, String searchTerm) {
        this.table = table;
        this.searchTerm = searchTerm;
    }

    @Override
    public void generarTabla() {
        String[] columnNames = {"ID", "Titulo", "Autor", "Precio", "Categorias", "Estado"};

        BookController bc = new BookController();
        ArrayList<Book> data = bc.getBookWithList("titulo", searchTerm);

        Object[][] tableData = new Object[data.size()][6];

        for (int i = 0; i < data.size(); i++) {
            Book book = data.get(i);
            tableData[i][0] = book.getId_book();
            tableData[i][1] = book.getTitulo();
            tableData[i][2] = book.getAutor();
            tableData[i][3] = book.getPrice();

            StringBuilder categories = new StringBuilder();
            for (String categoryName : book.getCategories()) {
                categories.append(categoryName).append(", ");
            }
            if (categories.length() > 0) {
                categories.setLength(categories.length() - 2);
            }
            tableData[i][4] = categories.toString();

            tableData[i][5] = book.getStatus() ? "Disponible" : "No Disponible";
        }

        configurarTabla(table, tableData, columnNames);
    }
}
