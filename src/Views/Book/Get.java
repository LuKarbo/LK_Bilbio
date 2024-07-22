package Views.Book;

import Controllers.BookController;
import Models.Book;
import Views.Home;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Get extends JFrame{
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTextField buscadorField;
    private JButton buscarBTN;
    private JTable dataTable;

    public Get() {
        generateBookTable(""); // Iniciar con todos los libros
        buscarBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = buscadorField.getText().trim();
                generateBookTable(searchTerm);
            }
        });

        setTitle("LK-Biblio Buscador");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }

    public void generateBookTable(String searchTerm) {
        String[] columnNames = {"ID", "Titulo", "Autor", "Precio", "Categorias", "Estado"};
        DefaultTableModel bookDataModel;
        BookController bc = new BookController();

        ArrayList<Book> data = bc.getBookWithList("titulo",searchTerm);

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
        bookDataModel = new DefaultTableModel(tableData, columnNames);

        dataTable.setModel(bookDataModel);
        dataTable.getTableHeader().setReorderingAllowed(false);
        dataTable.revalidate();
        dataTable.repaint();
    }
}
