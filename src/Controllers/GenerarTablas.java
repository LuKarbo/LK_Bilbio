package Controllers;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class GenerarTablas {

    // Generar tablas de las vistas
    public abstract void generarTabla();

    // setear la info y propiedades de la tabla
    protected void configurarTabla(JTable table, Object[][] data, String[] columnNames) {
        DefaultTableModel modelo = new DefaultTableModel(data, columnNames);
        table.setModel(modelo);
        table.getTableHeader().setReorderingAllowed(false);
        table.revalidate();
        table.repaint();
    }
}

