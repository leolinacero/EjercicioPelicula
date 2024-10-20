package Model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data

public class User implements Serializable {
    private Integer id;
    private String nombre_Usuario;
    private String contraseña;

    private List<PeliCopia> miCopia = new ArrayList<>(0);


    public User() {}

    public User(Integer id, String nombre_Usuario, String contraseña) {
        this.id = id;
        this.nombre_Usuario = nombre_Usuario;
        this.contraseña = contraseña;
    }
}
