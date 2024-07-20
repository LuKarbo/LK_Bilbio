package Views.User;

import Controllers.UserController;
import Views.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Create extends JFrame{
    private JPanel mainPanel;
    private JLabel userNameLabel;
    private JTextField userNameField;
    private JLabel userPassLabel;
    private JPasswordField userPassField;
    private JButton crearButton;
    private JButton cancelarButton;
    private JLabel smgAlert;

    public Create(Home home){
        smgAlert.setVisible(false);
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserController uc = new UserController();
                char[] passwordChars = userPassField.getPassword();
                String password = new String(passwordChars);
                if(uc.createUser(userNameField.getText(),password)){
                    setVisible(false);
                    home.actualizar();
                }
                else{
                    smgAlert.setText("Nombre en uso");
                    smgAlert.setVisible(true);
                    pack();
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setTitle("LK-Biblio Create User");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
