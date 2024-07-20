package Views.Prestamo;

import Controllers.PrestamoController;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Registro extends JFrame{
    private JPanel mainPanel;
    private JLabel userLabel;
    private JLabel libroLabel;
    private JLabel dateLabel;

    public Registro(){
        PrestamoController pc = new PrestamoController();
        Object[] data = pc.getRegistros();

        String user = data[0] != null ? (String) data[0] : "Sin registro";
        String libro = data[1] != null ? (String) data[1] : "Sin registro";
        String fecha = data[2] != null ? formatDate((Date) data[2]) : "Sin registro";

        userLabel.setText("Usuario con más Prestamos: " + user);
        libroLabel.setText("Libro más solicitado: " + libro);
        dateLabel.setText("Día con más prestamos Generados: " + fecha);

        setTitle("LK-Biblio Registros Históricos");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }

    private String formatDate(Date date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(date);
    }

}
