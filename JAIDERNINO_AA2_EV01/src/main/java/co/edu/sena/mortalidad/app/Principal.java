package co.edu.sena.mortalidad.app;

import co.edu.sena.mortalidad.dao.RegistroMortalidadDAO;
import co.edu.sena.mortalidad.modelo.RegistroMortalidad;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicacion de consola (stand-alone) para gestionar la mortalidad avicola.
 * Muestra un menu con las 4 funcionalidades: insertar, consultar, actualizar y eliminar.
 */
public class Principal {

    private static final int MAX_GALPONES = 8;
    private static final RegistroMortalidadDAO DAO = new RegistroMortalidadDAO();
    private static final Scanner TECLADO = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ", 0, 5);
            try {
                switch (opcion) {
                    case 1 -> insertarRegistro();
                    case 2 -> listarRegistros();
                    case 3 -> consultarRegistro();
                    case 4 -> actualizarRegistro();
                    case 5 -> eliminarRegistro();
                    default -> System.out.println("Hasta luego.");
                }
            } catch (SQLException e) {
                System.out.println("Error de base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== CONTROL DE MORTALIDAD AVICOLA ===");
        System.out.println("1. Insertar registro");
        System.out.println("2. Listar todos los registros");
        System.out.println("3. Consultar registro por ID");
        System.out.println("4. Actualizar registro");
        System.out.println("5. Eliminar registro");
        System.out.println("0. Salir");
    }

    private static void insertarRegistro() throws SQLException {
        RegistroMortalidad registro = pedirDatos(new RegistroMortalidad());
        int id = DAO.insertar(registro);
        System.out.println("Registro guardado con ID " + id);
    }

    private static void listarRegistros() throws SQLException {
        List<RegistroMortalidad> lista = DAO.consultarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay registros.");
            return;
        }
        lista.forEach(System.out::println);
    }

    private static void consultarRegistro() throws SQLException {
        int id = leerEntero("ID del registro: ", 1, Integer.MAX_VALUE);
        RegistroMortalidad registro = DAO.consultarPorId(id);
        System.out.println(registro == null ? "No existe el registro " + id : registro);
    }

    private static void actualizarRegistro() throws SQLException {
        int id = leerEntero("ID del registro a actualizar: ", 1, Integer.MAX_VALUE);
        RegistroMortalidad registro = DAO.consultarPorId(id);
        if (registro == null) {
            System.out.println("No existe el registro " + id);
            return;
        }
        pedirDatos(registro);
        System.out.println(DAO.actualizar(registro) ? "Registro actualizado." : "No se pudo actualizar.");
    }

    private static void eliminarRegistro() throws SQLException {
        int id = leerEntero("ID del registro a eliminar: ", 1, Integer.MAX_VALUE);
        System.out.println(DAO.eliminar(id) ? "Registro eliminado." : "No existe el registro " + id);
    }

    /** Pide los datos por consola validando cada campo. */
    private static RegistroMortalidad pedirDatos(RegistroMortalidad registro) {
        registro.setGalpon(leerEntero("Galpon (1-" + MAX_GALPONES + "): ", 1, MAX_GALPONES));
        registro.setFecha(leerFecha("Fecha (AAAA-MM-DD): "));
        registro.setAvesMuertas(leerEntero("Aves muertas: ", 0, 28000));
        registro.setDescartes(leerEntero("Descartes: ", 0, 28000));
        System.out.print("Observaciones: ");
        registro.setObservaciones(TECLADO.nextLine().trim());
        return registro;
    }

    /** Lee un entero dentro de un rango; repite la pregunta si el dato no es valido. */
    private static int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            System.out.print(mensaje);
            try {
                int valor = Integer.parseInt(TECLADO.nextLine().trim());
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
                System.out.println("Debe estar entre " + minimo + " y " + maximo + ".");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido.");
            }
        }
    }

    /** Lee una fecha en formato AAAA-MM-DD y no permite fechas futuras. */
    private static LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                LocalDate fecha = LocalDate.parse(TECLADO.nextLine().trim());
                if (!fecha.isAfter(LocalDate.now())) {
                    return fecha;
                }
                System.out.println("La fecha no puede ser futura.");
            } catch (DateTimeParseException e) {
                System.out.println("Formato invalido. Use AAAA-MM-DD.");
            }
        }
    }
}
