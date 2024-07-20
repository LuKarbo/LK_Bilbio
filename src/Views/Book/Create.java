package Views.Book;

import Controllers.BookController;
import Controllers.CategoryController;
import Models.Category;
import Views.Home;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Create extends JFrame {
    private JLabel tituloLabel;
    private JLabel autorLabel;
    private JLabel precioLabel;
    private JLabel categoriaLabel;
    private JTextField tituloField;
    private JTextField autorField;
    private JTextField precioField;
    private JScrollPane categoryScrollPanel;
    private JButton crearButton;
    private JPanel mainPanel;
    private JLabel smgAlert;

    private ArrayList<Category> categories;
    private ArrayList<Integer> selectedCategoryIds;

    public Create(Home home) {
        smgAlert.setVisible(false);

        categories = new ArrayList<>();
        ArrayList<Integer> selectedCategoryIds = new ArrayList<>();

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

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookController bc = new BookController();
                if (bc.createBook(tituloField.getText().trim(), autorField.getText().trim(),
                        Double.parseDouble(precioField.getText().trim().replace(',', '.')), selectedCategoryIds)) {
                    setVisible(false);
                    home.actualizar();
                } else {
                    smgAlert.setText("Error al crear el libro");
                    smgAlert.setVisible(true);
                    pack();
                }
            }
        });

        setTitle("LK-Biblio Crear Libro");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
