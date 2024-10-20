package Model;

import lombok.Data;

import java.io.Serializable;
@Data

public class Pelicula implements Serializable {
    private Integer id;
    private String titulo;
    private String genero;
    private Integer año;
    private String descripcion;
    private String director;

    public Pelicula() {
    }


    public Pelicula(Integer id, String titulo, String genero, Integer año, String descripcion, String director) {
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.año = año;
        this.descripcion = descripcion;
        this.director = director;
    }
}




