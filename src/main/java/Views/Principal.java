package Views;


import DAO.JdbcUtils;
import DAO.PeliDao;
import Model.PeliCopia;
import Model.Pelicula;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Views.Session.*;

public class Principal extends JFrame {



    private JPanel panel3;
    private JTable table1;
    private JButton añadirButton;
    private JButton salirButton;
    private JButton volverButton;
    private DefaultTableModel model;
    private PeliDao daoPeli = new PeliDao(JdbcUtils.getCon());

    public Principal(){

        String [] campos ={"Título","Estado","Soporte"};
        model = new DefaultTableModel(campos,0);

        table1.setModel(model);

        var columnaTituloPeli = table1.getColumnModel().getColumn(0);
        columnaTituloPeli.setPreferredWidth(300);


        mostrarListaCopias();

        setContentPane(panel3);
        setTitle("Listado de Películas - "+userSelected.getNombre_Usuario());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);

        table1.getSelectionModel().addListSelectionListener(this::detalleCopia);


        añadirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaAñadir();


            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                paramsnotnull();
                dispose();


                userSelected = null;
                copyDTO = null;

            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverLogin();

            }
        });
    }



    private void mostrarListaCopias() {
        for(PeliCopia c : copyDTO){
            PeliDao daoPeli = new PeliDao(JdbcUtils.getCon());
            Pelicula peli = daoPeli.findById(c.getIdPelicula());
            c.setPeli(peli);
            Object[] fila ={peli.getTitulo(),c.getEstado(), c.getSoporte()};
            model.addRow(fila);
        }
    }


    private void volverLogin() {
        paramsnotnull();
        userSelected = null; // Limpiar manualmente los datos de sesión
        copyDTO = null;
        dispose();
        Login ventanaAntes = new Login();
        ventanaAntes.setVisible(true);
    }
    private void ventanaAñadir() {
        peliDTO = null;
        copySelected = null;
        var añadidCopia = new Añadir();
        añadidCopia.setVisible(true);
        dispose();
    }


    private void detalleCopia(ListSelectionEvent e) {
        PeliDao peliDao = new PeliDao(JdbcUtils.getCon());
        if (e.getValueIsAdjusting()) return;

        int select = table1.getSelectedRow();
        if (select >= 0) {
            copySelected = copyDTO.get(select);
            var idPeli = copySelected.getIdPelicula();
            peliDTO = daoPeli.findById(idPeli);

            var detalle = new VentanaDetalle();
            detalle.setVisible(true);
            dispose();
        } else {

            System.out.println("No hay selección en la tabla.");
        }
    }

}

