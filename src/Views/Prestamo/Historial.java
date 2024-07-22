package Views.Prestamo;

import Controllers.PrestamoController;
import Controllers.Tables.GenerarHistorialTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Historial extends JFrame{
    private JPanel mainPanel;
    private JLabel tituloLabel;
    private JTable table1;

    public Historial(){
        GenerarHistorialTable generarHistorialTable = new GenerarHistorialTable(table1);
        generarHistorialTable.generarTabla();

        setTitle("LK-Biblio Historial de Prestamos");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
