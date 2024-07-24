package Views;

import Controllers.BookController;
import Controllers.PermisosController;
import Controllers.PrestamoController;
import Controllers.Tables.GenerarBookTable;
import Controllers.Tables.GenerarPrestamoTable;
import Controllers.Tables.GenerarUserTable;
import Controllers.UserController;
import Interface.IActualizar;
import Models.Book;
import Models.Category;
import Models.User;
import Views.Book.Get;
import Views.Prestamo.Historial;
import Views.Prestamo.Registro;
import Views.User.Create;
import Views.User.EditDelete;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Home extends JFrame implements IActualizar {
    private JPanel mainPanel;
    private JLabel userNameLabel;
    private JLabel permisosLabel;
    private JTable prestamosTable;
    private JLabel msgLabel_1;
    private JButton crearPrestamoButton;
    private JButton historialButton;
    private JButton reporteButton;
    private JTable userTable;
    private JLabel msgLabel_2;
    private JButton crearUsuarioButton;
    private JButton editarEliminarButton;
    private JButton editarPrestamoButton;
    private JTable booksTable;
    private JLabel booksLabel;
    private JScrollPane dataUserPanel;
    private JButton editDeleteBookBTN;
    private JButton createBookBTN;
    private JScrollPane dataPresPanel;
    private JScrollPane dataBookPanel;
    private JButton getSpecificBook;

    public Home(User user){
        userNameLabel.setText("Usuario: " + user.getNombre().trim());
        PermisosController pc = new PermisosController();
        permisosLabel.setText("Rol: " + pc.getPermiso("id_permisos",user.getPermisos()).getNombre().trim());

        // ---------------------------- TABLAS START ----------------------------
        GenerarPrestamoTable generarPrestamoTable = new GenerarPrestamoTable(prestamosTable);
        generarPrestamoTable.generarTabla();
        GenerarUserTable generarUserTable = new GenerarUserTable(userTable);
        generarUserTable.generarTabla();
        GenerarBookTable generarBookTable = new GenerarBookTable(booksTable);
        generarBookTable.generarTabla();
        // ---------------------------- TABLAS END ----------------------------

        // ---------------------------- USER START ----------------------------
        crearUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Create createUser = new Create(Home.this);
                createUser.setVisible(true);
            }
        });
        editarEliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditDelete edUser = new EditDelete(Home.this);
                edUser.setVisible(true);
            }
        });

        // ---------------------------- USER END ------------------------------

        // ---------------------------- BOOK START ----------------------------
        createBookBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Views.Book.Create bookCreate = new Views.Book.Create(Home.this);
                bookCreate.setVisible(true);
            }
        });
        editDeleteBookBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Views.Book.EditDelete bookEditDelete = new Views.Book.EditDelete(Home.this);
                bookEditDelete.setVisible(true);
            }
        });
        getSpecificBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Get buscador = new Get();
                buscador.setVisible(true);
            }
        });
        // ---------------------------- BOOK END ------------------------------

        // ---------------------------- Prestamo START ----------------------------
        historialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Historial historial = new Historial();
                historial.setVisible(true);
            }
        });
        reporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro registro = new Registro();
                registro.setVisible(true);
            }
        });
        crearPrestamoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Views.Prestamo.Create createPrestamo = new Views.Prestamo.Create(Home.this);
                createPrestamo.setVisible(true);
            }
        });
        editarPrestamoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Views.Prestamo.Edit editPrestamo = new Views.Prestamo.Edit(Home.this);
                editPrestamo.setVisible(true);
            }
        });

        // ---------------------------- Prestamo END ----------------------------

        setTitle("LK-Biblio Home");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    @Override
    public void actualizar() {
        GenerarPrestamoTable generarPrestamoTable = new GenerarPrestamoTable(prestamosTable);
        generarPrestamoTable.generarTabla();

        GenerarUserTable generarUserTable = new GenerarUserTable(userTable);
        generarUserTable.generarTabla();

        GenerarBookTable generarBookTable = new GenerarBookTable(booksTable);
        generarBookTable.generarTabla();

        setTitle("LK-Biblio Home");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

}