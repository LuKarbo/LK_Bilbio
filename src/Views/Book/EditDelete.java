package Views.Book;

import Controllers.BookController;
import Controllers.CategoryController;
import Models.Book;
import Models.Category;
import Views.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EditDelete extends JFrame{
    private JPanel mainPanel;
    private JLabel tituloLabel;
    private JLabel autorLabel;
    private JLabel precioLabel;
    private JLabel categoriasLabel;
    private JTextField tituloField;
    private JTextField autorField;
    private JTextField precioField;
    private JScrollPane categoryScrollPanel;
    private JButton editarButton;
    private JButton eliminarButton;
    private JLabel bookLabel;
    private JComboBox libroBox;
    private JLabel smgAlert;
    private JCheckBox statusCheckBox;
    private ArrayList<Category> categories;
    private ArrayList<Integer> selectedCategoryIds;

    public EditDelete(Home home){
        smgAlert.setVisible(false);
        BookController bc = new BookController();

        DefaultComboBoxModel<String> bookModel = new DefaultComboBoxModel<>();
        ArrayList<Book> books = bc.getBookList();
        String data = "Seleccionar un libro";
        bookModel.addElement(data);
        for (Book book : books) {
            data = book.getId_book() + " - " + book.getTitulo();
            bookModel.addElement(data);
        }
        libroBox.setModel(bookModel);

        libroBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) libroBox.getSelectedItem();
                if(selectedValue != "Seleccionar un libro"){
                    String[] parts = selectedValue.split(" - ");

                    if (parts.length > 0) {
                        ArrayList<Book> books = bc.getBookWithList("id_book",parts[0]);
                        Book book = books.get(0);

                        tituloField.setText(book.getTitulo().trim());
                        autorField.setText(book.getAutor().trim());
                        precioField.setText(String.valueOf(book.getPrice()));
                        statusCheckBox.setSelected(book.getStatus());

                        categories = new ArrayList<>();
                        ArrayList<Integer> selectedCategoryIds = new ArrayList<>();

                        CategoryController cc = new CategoryController();
                        categories = cc.getCategoryList();

                        JPanel categoryPanel = new JPanel();
                        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
                        categoryScrollPanel.setViewportView(categoryPanel);

                        // Categorias que tiene el libro
                        ArrayList<String> categoriasName = book.getCategories();

                        for (Category category : categories) {
                            JCheckBox checkBox = new JCheckBox(category.getNombre());
                            checkBox.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (checkBox.isSelected()) {
                                        selectedCategoryIds.add(category.getId_category());
                                    } else {
                                        selectedCategoryIds.remove(Integer.valueOf(category.getId_category()));
                                    }
                                }
                            });

                            if(categoriasName.contains(category.getNombre())){
                                selectedCategoryIds.add(category.getId_category());
                                checkBox.setSelected(true);
                            }

                            categoryPanel.add(checkBox);
                        }

                    }
                }
                else{
                    tituloField.setText("");
                    autorField.setText("");
                    precioField.setText("");
                    statusCheckBox.setSelected(false);
                    categories = new ArrayList<>();
                    CategoryController cc = new CategoryController();
                    categories = cc.getCategoryList();

                    JPanel categoryPanel = new JPanel();
                    categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
                    categoryScrollPanel.setViewportView(categoryPanel);

                    for (Category category : categories) {
                        JCheckBox checkBox = new JCheckBox(category.getNombre());
                        checkBox.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (checkBox.isSelected()) {
                                    selectedCategoryIds.add(category.getId_category());
                                } else {
                                    selectedCategoryIds.remove(Integer.valueOf(category.getId_category()));
                                }
                            }
                        });

                        categoryPanel.add(checkBox);
                    }
                }
                pack();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookController bc = new BookController();
                String selectedValue = (String) libroBox.getSelectedItem();
                if(selectedValue != "Seleccionar un libro"){
                    String[] parts = selectedValue.split(" - ");
                    if(bc.editBook(Integer.parseInt(parts[0]),tituloField.getText().trim(),autorField.getText().trim(),Double.parseDouble(precioField.getText().trim().replace(',', '.')),statusCheckBox.isSelected())){
                        setVisible(false);
                        home.actualizar();
                    }
                    else{
                        smgAlert.setText("Error al editar el Libro");
                        smgAlert.setVisible(true);
                        pack();
                    }
                }
                else{
                    smgAlert.setText("Seleccione un Libro");
                    smgAlert.setVisible(true);
                    pack();
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookController bc = new BookController();
                String selectedValue = (String) libroBox.getSelectedItem();
                if(selectedValue != "Seleccionar un libro"){
                    String[] parts = selectedValue.split(" - ");
                    if(bc.deleteBook(Integer.parseInt(parts[0]))){
                        setVisible(false);
                        home.actualizar();
                    }
                    else{
                        smgAlert.setText("Error al Eliminar");
                        smgAlert.setVisible(true);
                    }
                }
                else{
                    smgAlert.setText("Seleccione un Libro");
                    smgAlert.setVisible(true);
                }
            }
        });

        setTitle("LK-Biblio Editar | Eliminar Libro");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
