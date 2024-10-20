package DAO;

import Model.PeliCopia;
import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa el Data Access Object (DAO) para la entidad PeliCopia.
 * Proporciona métodos para realizar operaciones CRUD en la tabla 'Copia' de la base de datos.
 *
 * <p>
 * Esta clase se encarga de gestionar las conexiones a la base de datos y ejecutar
 * consultas SQL para recuperar, insertar, actualizar y eliminar objetos de tipo PeliCopia.
 * </p>
 */
public class CopiaDao implements DAO<PeliCopia> {

    /** Consulta SQL para seleccionar todas las copias. */
    public static final String SELECT_FROM_COPIA = "SELECT * FROM peliculas.Copia";

    /** Consulta SQL para seleccionar una copia específica por ID. */
    public static final String SELECT_FROM_COPIA_WHERE_ID = "SELECT * FROM peliculas.Copia WHERE id=?";

    /** Consulta SQL para insertar una nueva copia. */
    public static final String INSERT_INTO_COPIA = "INSERT INTO peliculas.Copia (estado, soporte, id_pelicula, id_usuario) VALUES (?, ?, ?, ?)";

    /** Consulta SQL para actualizar una copia existente. */
    public static final String UPDATE_COPIA = "UPDATE peliculas.Copia SET estado=?, soporte=? WHERE id=?";

    /** Consulta SQL para eliminar una copia por ID. */
    public static final String DELETE_FROM_COPIA = "DELETE FROM peliculas.Copia WHERE id=?";

    /** Consulta SQL para seleccionar copias por ID de usuario. */
    public static final String SELECT_FROM_COPIA_WHERE_ID_USUARIO = "SELECT * FROM peliculas.Copia WHERE id_usuario=?";

    /** Conexión a la base de datos. */
    private Connection con;

    /**
     * Constructor que inicializa la conexión a la base de datos.
     *
     * @param conect la conexión a la base de datos.
     */
    public CopiaDao(Connection conect) {
        this.con = conect;
    }

    /**
     * Obtiene todas las copias de la base de datos.
     *
     * @return una lista de objetos PeliCopia.
     */
    @Override
    public List<PeliCopia> findAll() {
        List<PeliCopia> lista = new ArrayList<>();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(SELECT_FROM_COPIA)) {
            while (rs.next()) {
                PeliCopia copy = new PeliCopia();
                copy.setId(rs.getInt("id"));
                copy.setEstado(rs.getString("estado"));
                copy.setSoporte(rs.getString("soporte"));
                copy.setIdPelicula(rs.getInt("id_pelicula"));
                copy.setIdUsuario(rs.getInt("id_usuario"));
                lista.add(copy);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    /**
     * Obtiene una copia de la base de datos por su ID.
     *
     * @param id el ID de la copia a buscar.
     * @return el objeto PeliCopia correspondiente o null si no se encuentra.
     */
    @Override
    public PeliCopia findById(Integer id) {
        PeliCopia copy = null;
        try (PreparedStatement ps = con.prepareStatement(SELECT_FROM_COPIA_WHERE_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                copy = new PeliCopia();
                copy.setId(rs.getInt("id"));
                copy.setEstado(rs.getString("estado"));
                copy.setSoporte(rs.getString("soporte"));
                copy.setIdPelicula(rs.getInt("id_pelicula"));
                copy.setIdUsuario(rs.getInt("id_usuario"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return copy;
    }

    /**
     * Guarda una nueva copia en la base de datos.
     *
     * @param copia el objeto PeliCopia a guardar.
     */
    @Override
    public void save(PeliCopia copia) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_INTO_COPIA, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, copia.getEstado());
            ps.setString(2, copia.getSoporte());
            ps.setInt(3, copia.getIdPelicula());
            ps.setInt(4, copia.getIdUsuario());

            if (ps.executeUpdate() == 1) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    Integer copia_id = keys.getInt(1);
                    copia.setId(copia_id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Actualiza una copia existente en la base de datos.
     *
     * @param copia el objeto PeliCopia con los nuevos datos.
     */
    @Override
    public void update(PeliCopia copia) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_COPIA)) {
            ps.setString(1, copia.getEstado());
            ps.setString(2, copia.getSoporte());
            ps.setInt(3, copia.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina una copia de la base de datos.
     *
     * @param copia el objeto PeliCopia a eliminar.
     */
    @Override
    public void delete(PeliCopia copia) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_FROM_COPIA)) {
            ps.setInt(1, copia.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene una lista de copias asociadas a un usuario específico.
     *
     * @param u el objeto User cuyo ID se utiliza para buscar copias.
     * @return una lista de objetos PeliCopia asociados al usuario.
     */
    public List<PeliCopia> findByUser(User u) {
        List<PeliCopia> miLista = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement(SELECT_FROM_COPIA_WHERE_ID_USUARIO)) {
            ps.setInt(1, u.getId());
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                PeliCopia copy = new PeliCopia(
                        result.getInt("id"),
                        result.getString("soporte"),
                        result.getInt("id_usuario"),
                        result.getString("estado"),
                        result.getInt("id_pelicula")
                );
                miLista.add(copy);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return miLista;
    }
}