package Model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PeliCopia implements Serializable {
    private Integer id;
    private Integer idPelicula;
    private Integer idUsuario;
    private String estado;
    private String soporte;


    private User u;

    private Pelicula peli;

    public PeliCopia(){

    }

    public PeliCopia(Integer id, String soporte, Integer idUsuario, String estado, Integer idPelicula) {
        this.id = id;
        this.soporte = soporte;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.idPelicula = idPelicula;
    }

    @Override
    public String toString() {
        return "PeliCopia{" +
                "id=" + id +
                ", idPelicula=" + idPelicula +
                ", idUsuario=" + idUsuario +
                ", estado='" + estado + '\'' +
                ", soporte='" + soporte + '\'' +
                '}';
    }
}