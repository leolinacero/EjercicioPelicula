package Views;

import DAO.CopiaDao;
import DAO.JdbcUtils;
import DAO.PeliDao;
import Model.PeliCopia;
import Model.Pelicula;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static Views.Session.*;

public class Añadir extends JDialog {
    private JPanel panel5;
    private JComboBox<String> sopor;
    private JComboBox<String> peli;
    private JRadioButton estadoMaloRadioButton;
    private JRadioButton estadoBuenoRadioButton;
    private JButton guardarButton;
    private JButton cancelarButton;
    private JLabel warning;



    private PeliDao pDao = new PeliDao(JdbcUtils.getCon());

    private CopiaDao cDao = new CopiaDao(JdbcUtils.getCon());

    PeliDao peliDao = new PeliDao(JdbcUtils.getCon());


    public Añadir(){
        setContentPane(panel5);
        setModal(true);
        setTitle("Añadir Copia");
        setLocationRelativeTo(null);
        setSize(300,250);
        setResizable(false);

        setearOpcionesSoporte();
        setearOpcionesPeliculas(peliDao);


        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCopia();

            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();

            }
        });
    }


    private void setearOpcionesPeliculas(PeliDao peliDao) {
        var opcionesPeliculas = new DefaultComboBoxModel<String>();
        peli.setModel(opcionesPeliculas);
        List<Pelicula> listaPelis = peliDao.findAll();
        for (Pelicula peli : listaPelis){
            opcionesPeliculas.addElement(peli.getTitulo());
        }
    }


    private void setearOpcionesSoporte() {
        var opcionesSoporte = new DefaultComboBoxModel<String>();
        sopor.setModel(opcionesSoporte);
        opcionesSoporte.addElement("DVD");
        opcionesSoporte.addElement("Blu-ray");
    }


    private void guardarCopia() {
        copySelected = new PeliCopia();
        String titulo = (String) peli.getSelectedItem();
        peliDTO = pDao.findByTitle(titulo);

        if ( peliDTO != null && soportePeli() && estadoPeli()) {
            soportePeli();
            estadoPeli();


            PeliCopia nuevaCopia = new PeliCopia();
            nuevaCopia.setEstado(copySelected.getEstado());
            nuevaCopia.setSoporte(copySelected.getSoporte());
            nuevaCopia.setIdPelicula(peliDTO.getId());
            nuevaCopia.setIdUsuario(userSelected.getId());
            nuevaCopia.setPeli(peliDTO);
            nuevaCopia.setU(userSelected);


            copyDTO.add(nuevaCopia);

            cDao.save(nuevaCopia);

            dispose();
            var principal = new Principal();
            principal.setVisible(true);

        } else{
            warning.setText("Introduce todos los valores");
        }
    }

    private boolean soportePeli(){
        boolean flag = false;
        if (sopor.getSelectedItem() != null){
            copySelected.setSoporte((String)sopor.getSelectedItem());
            flag = true;
        }
        return flag;
    }


    private boolean estadoPeli() {
        boolean flag = false;
        if (estadoBuenoRadioButton.isSelected()){
            copySelected.setEstado("bueno");
            flag = true;
        } else if (estadoMaloRadioButton.isSelected()) {
            copySelected.setEstado("dañado");
            flag = true;
        }
        return flag;
    }


    private void cancelar() {
        dispose();
        var principal = new Principal();
        principal.setVisible(true);
    }
}

