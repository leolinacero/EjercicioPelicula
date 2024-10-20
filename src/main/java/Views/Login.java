package Views;


import DAO.CopiaDao;
import DAO.JdbcUtils;

import DAO.UserDao;
import Model.PeliCopia;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static Views.Session.paramsnotnull;

public class Login extends JFrame {
    private JPanel panel1;

    private JPasswordField password;
    private JButton cerrarButton;
    private JButton iniciarButton;
    private JButton agregarUsuarioButton;
    private JTextField user;
    private JLabel usuariola;
    private JLabel contralabe;


    public Login() {


        setContentPane(panel1);
        setTitle("login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);



        agregarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paramsnotnull();
                AgregarUsuario nuevoUsuario = new AgregarUsuario();
                nuevoUsuario.setVisible(true);
                dispose();


            }
        });
        cerrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sesionConectada();

            }
        });

    }



    private void sesionConectada() {
        UserDao daoUser = new UserDao(JdbcUtils.getCon());
        CopiaDao daoCopia = new CopiaDao(JdbcUtils.getCon());

        User u = daoUser.validateUser(user.getText(), password.getPassword());

        if (u != null) {
            List<PeliCopia> copi = daoCopia.findByUser(u);
            u.setMiCopia(copi);

            Session.copyDTO = copi;
            Session.userSelected = u;

            Principal principal = new Principal();
            principal.setVisible(true);
            dispose();

        } else {
            JOptionPane.showMessageDialog(this, "Error al ingresar");
            user.setText("");
            password.setText("");
        }
        
    }
}