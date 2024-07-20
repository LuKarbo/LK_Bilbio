package Views.Prestamo;

import Controllers.PrestamoController;
import Models.Permiso;
import Models.Prestamo;
import Models.User;
import Views.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Edit extends JFrame{
    private JPanel mainPanel;
    private JComboBox prestamoSelect;
    private JCheckBox activoDestildarParaFinalizarloCheckBox;
    private JLabel statusLabel;
    private JLabel smgAlert;
    private JLabel prestamoLabel;
    private JLabel titleLabel;
    private JButton confirmarButton;

    public Edit(Home home){
        smgAlert.setVisible(false);
        PrestamoController pc = new PrestamoController();

        DefaultComboBoxModel<String> prestamoModel = new DefaultComboBoxModel<>();
        Object[][] data = pc.getPrestamosList(0);
        String content = "Seleccionar un Prestamo Activo";
        prestamoModel.addElement(content);
        for (int i = 0; i < data.length; i++) {
            content = data[i][0] + " - " + data[i][1] + " - " + data[i][2];
            prestamoModel.addElement(content);
        }
        prestamoSelect.setModel(prestamoModel);

        prestamoSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) prestamoSelect.getSelectedItem();

                String[] parts = selectedValue.split(" - ");

                if (selectedValue != "Seleccionar un Prestamo Activo") {
                    Prestamo prestamo = pc.getPrestamo("id_prestamo",parts[0]);
                    if(prestamo != null){
                        activoDestildarParaFinalizarloCheckBox.setSelected(prestamo.isStatus());
                        pack();
                    }
                }
            }
        });

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) prestamoSelect.getSelectedItem();

                String[] parts = selectedValue.split(" - ");

                if (selectedValue != "Seleccionar un Prestamo Activo") {
                    if(pc.editPrestamoStatus(Integer.parseInt(parts[0]),activoDestildarParaFinalizarloCheckBox.isSelected())){
                        home.actualizar();
                        setVisible(false);
                    }
                    else{
                        smgAlert.setText("Error al editar el Prestamo");
                        smgAlert.setVisible(true);
                        pack();
                    }
                }
                else{
                    smgAlert.setText("Recuerde seleccionar un Prestamo");
                    smgAlert.setVisible(true);
                    pack();
                }
            }
        });

        setTitle("LK-Biblio Editar prestamo");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
