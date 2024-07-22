package Controllers.Tables;

import Controllers.GenerarTablas;
import Controllers.UserController;

import javax.swing.JTable;

public class GenerarUserTable extends GenerarTablas {

    private JTable userTable;

    public GenerarUserTable(JTable userTable) {
        this.userTable = userTable;
    }

    @Override
    public void generarTabla() {
        String[] columnNames = {"ID", "Cliente", "Prestamos Activos", "Prestamos Totales"};

        UserController uc = new UserController();
        Object[][] data = uc.getUserList();

        configurarTabla(userTable, data, columnNames);
    }
}

