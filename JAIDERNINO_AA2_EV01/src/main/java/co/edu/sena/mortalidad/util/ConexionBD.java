package co.edu.sena.mortalidad.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria que entrega la conexion JDBC a MySQL.
 * Cambie USUARIO y CLAVE segun su instalacion de MySQL.
 */
public final class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/mortalidad_avicola?useSSL=false&serverTimezone=America/Bogota";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    /** Evita que se creen instancias (solo metodos estaticos). */
    private ConexionBD() {
    }

    /**
     * Abre una conexion nueva a la base de datos.
     *
     * @return conexion lista para usar
     * @throws SQLException si no se puede conectar
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
