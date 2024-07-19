package Views.User;

import Controllers.PermisosController;
import Controllers.UserController;
import Models.Permiso;
import Models.User;
import Views.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class EditDelete extends JFrame{
    private JPanel mainPanel;
    private JLabel userNameLabel;
    private JButton editarButton;
    private JButton eliminarButton;
    private JLabel permisosLabel;
    private JComboBox permisosBox;
    private JTextField userNameField;
    private JLabel smgAlert;
    private JComboBox clientSelector;
    private int user_id;

    public EditDelete(Home home){

        smgAlert.setVisible(false);
        UserController uc = new UserController();

        DefaultComboBoxModel<String> userModel = new DefaultComboBoxModel<>();
        Object[][] users = uc.getUserList();
        for (int i = 0; i < users.length; i++) {
            String data = users[i][0] + " - " + users[i][1];
            userModel.addElement(data);
        }
        clientSelector.setModel(userModel);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        PermisosController pc = new PermisosController();
        ArrayList<Permiso> permisos = pc.getPermisoList();
        for (Permiso permiso : permisos) {
            String item = permiso.getId_permisos() + " - " + permiso.getNombre();
            model.addElement(item);
        }
        permisosBox.setModel(model);

        clientSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) clientSelector.getSelectedItem();

                String[] parts = selectedValue.split(" - ");

                if (parts.length > 0) {
                    User user = uc.getUser("id_user",parts[0]);
                    user_id = user.getId_user();
                    userNameField.setText(user.getNombre());
                    // selecciono por default el que ya tiene el usuario
                    for (int i = 0; i < permisos.size(); i++) {
                        Permiso permiso = permisos.get(i);
                        if (Objects.equals(permiso.getNombre(), pc.getPermiso("id_permisos", user.getPermisos()).getNombre())) {
                            permisosBox.setSelectedIndex(i);
                            break;
                        }
                    }

                }
            }
        });

        // Edit boton
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserController uc = new UserController();
                String selectedItem = (String) permisosBox.getSelectedItem();
                String[] selectedItemSeparado = selectedItem.split(" - ");
                int idPerm = Integer.parseInt(selectedItemSeparado[0].trim());

                if(uc.editUser(user_id,userNameField.getText(),idPerm)){
                    setVisible(false);
                    home.actualizar();
                }
                else{
                    smgAlert.setText("Nombre en uso");
                    smgAlert.setVisible(true);
                }
            }
        });

        // Delete boton
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserController uc = new UserController();
                if(uc.deleteUser(user_id)){
                    setVisible(false);
                    home.actualizar();
                }
                else{
                    smgAlert.setText("Error al Eliminar");
                    smgAlert.setVisible(true);
                }
            }
        });

        setTitle("LK-Biblio Edit/Delete User");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
