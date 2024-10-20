package Views;

import DAO.JdbcUtils;
import DAO.UserDao;
import Model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import static Views.Session.copyDTO;
import static Views.Session.userSelected;

public class AgregarUsuario extends JDialog {
    private JPanel panel2;
    private JPasswordField passwordField1;
    private JTextField textField2;
    private JPasswordField passwordField2;
    private JButton cancelarButton;
    private JButton guardarButton;
    private JLabel newUSu;

    UserDao userDao = new UserDao(JdbcUtils.getCon());

    public AgregarUsuario() {
        setContentPane(panel2);
        setModal(true);
        setTitle("Registrar nuevo usuario");
        setLocationRelativeTo(null);
        setSize(350,250);
        setResizable(false);
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelarButton();

            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarUsu();

            }
        });

    }
    private void cancelarButton(){
        dispose();
        var login = new Login();
        login.setVisible(true);

    }
    private void validarUsu(){
        User nuevoUsuario = userDao.validateNewUser(newUSu.getText());
        if (nuevoUsuario == null){
            if (Arrays.equals(passwordField1.getPassword(), passwordField2.getPassword())){
                nuevoUsuario = new User();
                nuevoUsuario.setNombre_Usuario(newUSu.getText());
                String passString = new String(passwordField1.getPassword());
                nuevoUsuario.setContraseña(passString);

                userSelected = nuevoUsuario;
                copyDTO = new ArrayList<>();
                userSelected.setMiCopia(copyDTO);
                userDao.save(userSelected);

                dispose();
                var principal = new Principal();
                principal.setVisible(true);
            }else {
                JOptionPane.showMessageDialog(this,"La contraseña no coincide");
            }
        }else {
            JOptionPane.showMessageDialog(this,"Ese nombre de usuario no está disponible");
        }


    }



}
