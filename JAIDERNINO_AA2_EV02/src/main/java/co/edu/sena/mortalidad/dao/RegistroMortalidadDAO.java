package co.edu.sena.mortalidad.dao;

import co.edu.sena.mortalidad.modelo.RegistroMortalidad;
import co.edu.sena.mortalidad.util.ConexionBD;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) con las operaciones CRUD sobre la tabla
 * registro_mortalidad usando JDBC y PreparedStatement.
 */
public class RegistroMortalidadDAO {

    private static final String SQL_INSERTAR =
            "INSERT INTO registro_mortalidad (galpon, fecha, aves_muertas, descartes, observaciones) "
                    + "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_CONSULTAR_TODOS =
            "SELECT * FROM registro_mortalidad ORDER BY fecha DESC, galpon";
    private static final String SQL_CONSULTAR_POR_ID =
            "SELECT * FROM registro_mortalidad WHERE id = ?";
    private static final String SQL_ACTUALIZAR =
            "UPDATE registro_mortalidad SET galpon = ?, fecha = ?, aves_muertas = ?, "
                    + "descartes = ?, observaciones = ? WHERE id = ?";
    private static final String SQL_ELIMINAR =
            "DELETE FROM registro_mortalidad WHERE id = ?";

    /** INSERCION: guarda un registro nuevo y devuelve el id generado (o -1 si falla). */
    public int insertar(RegistroMortalidad registro) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(
                     SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setInt(1, registro.getGalpon());
            sentencia.setDate(2, Date.valueOf(registro.getFecha()));
            sentencia.setInt(3, registro.getAvesMuertas());
            sentencia.setInt(4, registro.getDescartes());
            sentencia.setString(5, registro.getObservaciones());
            sentencia.executeUpdate();

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                return claves.next() ? claves.getInt(1) : -1;
            }
        }
    }

    /** CONSULTA: devuelve todos los registros. */
    public List<RegistroMortalidad> consultarTodos() throws SQLException {
        List<RegistroMortalidad> lista = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_TODOS);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                lista.add(convertirFila(resultado));
            }
        }
        return lista;
    }

    /** CONSULTA: busca un registro por id; devuelve null si no existe. */
    public RegistroMortalidad consultarPorId(int id) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_CONSULTAR_POR_ID)) {

            sentencia.setInt(1, id);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? convertirFila(resultado) : null;
            }
        }
    }

    /** ACTUALIZACION: modifica un registro existente; devuelve true si se actualizo. */
    public boolean actualizar(RegistroMortalidad registro) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ACTUALIZAR)) {

            sentencia.setInt(1, registro.getGalpon());
            sentencia.setDate(2, Date.valueOf(registro.getFecha()));
            sentencia.setInt(3, registro.getAvesMuertas());
            sentencia.setInt(4, registro.getDescartes());
            sentencia.setString(5, registro.getObservaciones());
            sentencia.setInt(6, registro.getId());
            return sentencia.executeUpdate() > 0;
        }
    }

    /** ELIMINACION: borra un registro por id; devuelve true si se elimino. */
    public boolean eliminar(int id) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(SQL_ELIMINAR)) {

            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;
        }
    }

    /** Convierte la fila actual del ResultSet en un objeto RegistroMortalidad. */
    private RegistroMortalidad convertirFila(ResultSet resultado) throws SQLException {
        return new RegistroMortalidad(
                resultado.getInt("id"),
                resultado.getInt("galpon"),
                resultado.getDate("fecha").toLocalDate(),
                resultado.getInt("aves_muertas"),
                resultado.getInt("descartes"),
                resultado.getString("observaciones"));
    }
}
