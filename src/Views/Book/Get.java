package Views.Book;

import Controllers.Tables.GenerarGetBookTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        GenerarGetBookTable generarLibrosTable = new GenerarGetBookTable(dataTable, searchTerm);
        generarLibrosTable.generarTabla();
    }
}
