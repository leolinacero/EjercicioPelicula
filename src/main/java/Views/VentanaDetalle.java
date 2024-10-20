package Views;

import DAO.CopiaDao;
import DAO.JdbcUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Views.Session.*;
import static com.google.protobuf.Any.pack;

public class VentanaDetalle extends JDialog {
    private JPanel panel4;
    private JButton eliminarButton;
    private JButton atrasButton;
    private JButton salirButton;
    private JLabel titulo;
    private JLabel genero;
    private JLabel soporte;
    private JLabel estado;
    private JLabel año;
    private JLabel descripcion;
    private JLabel director;



    CopiaDao cdao = new CopiaDao(JdbcUtils.getCon());




    public VentanaDetalle() {
        setContentPane(panel4);
        setModal(true);
        setTitle(peliDTO.getTitulo());
        setLocationRelativeTo(null);
        setResizable(false);


        titulo.setText(peliDTO.getTitulo());
        genero.setText(peliDTO.getGenero());
        año.setText(peliDTO.getAño().toString());
        director.setText(peliDTO.getDirector());
        descripcion.setText(peliDTO.getDescripcion());
        soporte.setText(copySelected.getSoporte());
        estado.setText(copySelected.getEstado());

        pack();

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrar();

            }
        });
        atrasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaAnterior();

            }
        });
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salirALogin();

            }
        });
    }

        private void ventanaAnterior() {
            peliDTO = null;
            var principal = new Principal();
            principal.setVisible(true);
            dispose();
        }


        private void borrar(){
            var resultado = JOptionPane.showConfirmDialog(this,"¿Desea borrar la copia?");
            if (resultado == JOptionPane.YES_OPTION){
                copyDTO.remove(copySelected);
                cdao.delete(copySelected);
                peliDTO = null;
            }
            dispose();
            var principal = new Principal();
            principal.setVisible(true);
        }
        private void salirALogin() {

            peliDTO = null;
            copySelected = null;


            Login login = new Login();
            login.setVisible(true);


            dispose();
        }

    }





