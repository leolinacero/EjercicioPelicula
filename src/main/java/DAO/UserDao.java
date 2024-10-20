package DAO;

import Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements  DAO<User> {

    public static final String SELECT_FROM_USUARIO = "select * from peliculas.Usuario";
    public static final String SELECT_FROM_USUARIO_WHERE_ID = "select * from peliculas.Usuario where id=?";
    public static final String INSERT_INTO_USUARIO = "insert into Usuario (nombre_usuario, contraseña) values(?,?)";
    public static final String UPDATE_USUARIO = "update peliculas.Usuario set nombre_usuario=?, contraseña=? where id=?";
    public static final String DELETE_FROM_USUARIO = "delete from peliculas.Usuario where id=?";
    public static final String SELECT_USUARIO_PASSWORD_FROM_USUARIO = "select * from peliculas.Usuario where nombre_usuario=? and contraseña=?";
    public static final String SELECT_NOMBRE_USUARIO_FROM_USUARIO = "Select nombre_usuario from peliculas.Usuario where nombre_usuario=?";




    private static Connection con = null;


    public UserDao(Connection conect) {
        con = conect;
    }

    @Override
    public List<User> findAll() {
        var lista = new ArrayList<User>();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(SELECT_FROM_USUARIO);

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNombre_Usuario(rs.getString("nombre_usuario"));
                user.setContraseña(rs.getString("contraseña"));
                lista.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }


    @Override
    public User findById(Integer id) {
        User user = null;

        try (PreparedStatement ps = con.prepareStatement(SELECT_FROM_USUARIO_WHERE_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setNombre_Usuario(rs.getString("nombre_usuario"));
                user.setContraseña(rs.getString("contraseña"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    @Override
    public void save(User usuario) {
        try (PreparedStatement ps = con.prepareStatement(INSERT_INTO_USUARIO, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, usuario.getNombre_Usuario());
            ps.setString(2, usuario.getContraseña());

            if (ps.executeUpdate() == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                usuario.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(User usuario) {
        try (PreparedStatement ps = con.prepareStatement(UPDATE_USUARIO)) {
            ps.setString(1, usuario.getNombre_Usuario());
            ps.setString(2, usuario.getContraseña());
            ps.setInt(3, usuario.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(User usuario) {
        try (PreparedStatement ps = con.prepareStatement(DELETE_FROM_USUARIO)) {
            ps.setInt(1, usuario.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public User validateUser(String user, char[] pass) {
        User usuario = null;

        try (PreparedStatement ps = con.prepareStatement(SELECT_USUARIO_PASSWORD_FROM_USUARIO)) {

            String passString = new String(pass);

            ps.setString(1, user);
            ps.setString(2, passString);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new User();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre_Usuario(rs.getString("nombre_usuario"));
                usuario.setContraseña(rs.getString("contraseña"));

                //usuario.setMicopia((new CopiaDAO(con).findUser(usuario)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }


    public User validateNewUser(String user){
        User usuario = null;
        try(PreparedStatement ps = con.prepareStatement(SELECT_NOMBRE_USUARIO_FROM_USUARIO)){
            ps.setString(1,user);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                usuario = new User();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre_Usuario(rs.getString("nombre_usuario"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }
}
