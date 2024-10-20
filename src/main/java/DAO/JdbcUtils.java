package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de utilidad para gestionar la conexión a la base de datos.
 *
 * <p>
 * Esta clase establece una conexión a la base de datos MySQL utilizando
 * JDBC. Proporciona un método estático para obtener la conexión
 * y maneja la inicialización de la conexión en un bloque estático.
 * </p>
 */
public class JdbcUtils {

    /** La conexión a la base de datos. */
    private static Connection con;

    static {
        String url = "jdbc:mysql://localhost:3307/peliculas";
        String user = "root";
        String password = "qwe";
        try {
            // Establece la conexión a la base de datos
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // Lanza una RuntimeException en caso de error al conectar
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene la conexión a la base de datos.
     *
     * @return la conexión a la base de datos.
     */
    public static Connection getCon() {
        return con;
    }
}