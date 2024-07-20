package Views;

import Controllers.UserController;
import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame {
    private JPanel mainPanel;
    private JLabel binvenidaLabel;
    private JTextField userNameField;
    private JLabel userNameLabel;
    private JPasswordField userPasswordField;
    private JLabel userPasswordLabel;
    private JButton ingresarButton;
    private JLabel alertSMGLabel;

    public Login(){
        alertSMGLabel.setVisible(false);
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserController uc = new UserController();
                String pass = new String(userPasswordField.getPassword());
                User userTemp = uc.loginAction(userNameField.getText().trim(),pass);
                if(userTemp != null){

                    if(userTemp.getPermisos() == 2){
                        // Accedo al Home
                        alertSMGLabel.setVisible(false);
                        setVisible(false);

                        // Muestro el Home
                        Home homeView = new Home(userTemp);
                        homeView.setVisible(true);
                        // System.out.println("Accedio al Home");
                    }
                    else{
                        alertSMGLabel.setText("Acceso Denegado");
                        alertSMGLabel.setVisible(true);
                        setSize(300, 270);
                    }
                }
                else{
                    alertSMGLabel.setText("Credenciales inválidas");
                    alertSMGLabel.setVisible(true);
                    setSize(300, 270);
                }
            }
        });

        setTitle("LK-Biblio Login");
        setSize(300, 250); // -> personalizo el tamaño de la ventana a gusto
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // pack(); -> auto ajusta la medida de la ventana, segun el contenido
    }

}
