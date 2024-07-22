package Controllers.Tables;


import Controllers.GenerarTablas;
import Controllers.PrestamoController;

import javax.swing.JTable;

public class GenerarHistorialTable extends GenerarTablas {

    private JTable table;

    public GenerarHistorialTable(JTable table) {
        this.table = table;
    }

    @Override
    public void generarTabla() {
        String[] columnNames = {"ID", "Cliente", "Libro", "Fecha", "Estado"};

        PrestamoController pc = new PrestamoController();
        Object[][] data = pc.getPrestamosList(1);

        if (data == null) {
            data = new Object[0][columnNames.length];
        }

        configurarTabla(table, data, columnNames);
    }
}