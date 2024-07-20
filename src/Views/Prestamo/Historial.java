package Views.Prestamo;

import Controllers.PrestamoController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Historial extends JFrame{
    private JPanel mainPanel;
    private JLabel tituloLabel;
    private JTable table1;

    public Historial(){
        String[] columnNames = {"ID", "Cliente", "Libro", "Fecha", "Estado"};

        PrestamoController pc = new PrestamoController();
        Object[][] data = pc.getPrestamosList(1);

        if (data == null) {
            data = new Object[0][columnNames.length];
        }

        DefaultTableModel modelo = new DefaultTableModel(data, columnNames);
        table1.setModel(modelo);
        table1.getTableHeader().setReorderingAllowed(false);


        setTitle("LK-Biblio Historial de Prestamos");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
