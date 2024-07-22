package Controllers.Tables;

import Controllers.GenerarTablas;
import Controllers.PrestamoController;

import javax.swing.JTable;

public class GenerarPrestamoTable extends GenerarTablas {

    private JTable prestamosTable;

    public GenerarPrestamoTable(JTable prestamosTable) {
        this.prestamosTable = prestamosTable;
    }

    @Override
    public void generarTabla() {
        String[] columnNames2 = {"ID", "Cliente", "Libro", "Fecha", "Estado"};

        PrestamoController pc = new PrestamoController();
        Object[][] data = pc.getPrestamosList(0);

        if (data == null) {
            data = new Object[0][columnNames2.length];
        }

        configurarTabla(prestamosTable, data, columnNames2);
    }
}
