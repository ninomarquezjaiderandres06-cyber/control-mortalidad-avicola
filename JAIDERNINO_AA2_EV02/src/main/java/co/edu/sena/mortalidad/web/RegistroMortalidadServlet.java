package co.edu.sena.mortalidad.web;

import co.edu.sena.mortalidad.dao.RegistroMortalidadDAO;
import co.edu.sena.mortalidad.modelo.RegistroMortalidad;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Servlet controlador del modulo de mortalidad.
 * GET  /registros                -> lista (opcional ?galpon=N para filtrar)
 * GET  /registros?accion=nuevo   -> formulario vacio
 * GET  /registros?accion=editar&id=N -> formulario con datos
 * POST /registros (accion=guardar | eliminar) -> inserta, actualiza o elimina
 */
@WebServlet("/registros")
public class RegistroMortalidadServlet extends HttpServlet {

    private static final int MAX_GALPONES = 8;
    private static final int MAX_AVES = 28000;
    private final RegistroMortalidadDAO dao = new RegistroMortalidadDAO();

    /** Metodo GET: consultas y formularios. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        try {
            if ("nuevo".equals(accion)) {
                request.setAttribute("registro", new RegistroMortalidad());
                request.getRequestDispatcher("/formulario.jsp").forward(request, response);
            } else if ("editar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                RegistroMortalidad registro = dao.consultarPorId(id);
                if (registro == null) {
                    response.sendRedirect("registros?mensaje=El+registro+no+existe");
                    return;
                }
                request.setAttribute("registro", registro);
                request.getRequestDispatcher("/formulario.jsp").forward(request, response);
            } else {
                listar(request, response);
            }
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("error", "Error al procesar la solicitud: " + e.getMessage());
            request.getRequestDispatcher("/listado.jsp").forward(request, response);
        }
    }

    /** Metodo POST: inserta, actualiza o elimina. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        try {
            if ("eliminar".equals(accion)) {
                int id = Integer.parseInt(request.getParameter("id"));
                boolean eliminado = dao.eliminar(id);
                response.sendRedirect("registros?mensaje=" + (eliminado ? "Registro+eliminado" : "No+existe+el+registro"));
            } else {
                guardar(request, response);
            }
        } catch (SQLException | NumberFormatException e) {
            request.setAttribute("error", "Error al procesar la solicitud: " + e.getMessage());
            request.getRequestDispatcher("/listado.jsp").forward(request, response);
        }
    }

    /** Carga la lista (con filtro opcional por galpon) y la envia al JSP. */
    private void listar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<RegistroMortalidad> lista = dao.consultarTodos();
        String filtro = request.getParameter("galpon");
        if (filtro != null && !filtro.isBlank()) {
            int galpon = Integer.parseInt(filtro);
            lista = lista.stream().filter(r -> r.getGalpon() == galpon).toList();
        }
        request.setAttribute("registros", lista);
        request.setAttribute("mensaje", request.getParameter("mensaje"));
        request.getRequestDispatcher("/listado.jsp").forward(request, response);
    }

    /** Valida los datos del formulario y los inserta o actualiza segun exista el id. */
    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        RegistroMortalidad registro = new RegistroMortalidad();
        String idTexto = request.getParameter("id");
        if (idTexto != null && !idTexto.isBlank()) {
            registro.setId(Integer.parseInt(idTexto));
        }

        String error = null;
        try {
            registro.setGalpon(Integer.parseInt(request.getParameter("galpon")));
            registro.setFecha(LocalDate.parse(request.getParameter("fecha")));
            registro.setAvesMuertas(Integer.parseInt(request.getParameter("avesMuertas")));
            registro.setDescartes(Integer.parseInt(request.getParameter("descartes")));
            registro.setObservaciones(request.getParameter("observaciones").trim());

            if (registro.getGalpon() < 1 || registro.getGalpon() > MAX_GALPONES) {
                error = "El galpon debe estar entre 1 y " + MAX_GALPONES + ".";
            } else if (registro.getFecha().isAfter(LocalDate.now())) {
                error = "La fecha no puede ser futura.";
            } else if (registro.getAvesMuertas() < 0 || registro.getAvesMuertas() > MAX_AVES
                    || registro.getDescartes() < 0 || registro.getDescartes() > MAX_AVES) {
                error = "Las cantidades deben estar entre 0 y " + MAX_AVES + ".";
            } else if (registro.getObservaciones().length() > 255) {
                error = "Las observaciones admiten maximo 255 caracteres.";
            }
        } catch (NumberFormatException | DateTimeParseException | NullPointerException e) {
            error = "Revise los datos: hay campos vacios o con formato invalido.";
        }

        if (error != null) {
            request.setAttribute("error", error);
            request.setAttribute("registro", registro);
            request.getRequestDispatcher("/formulario.jsp").forward(request, response);
            return;
        }

        if (registro.getId() > 0) {
            dao.actualizar(registro);
            response.sendRedirect("registros?mensaje=Registro+actualizado");
        } else {
            dao.insertar(registro);
            response.sendRedirect("registros?mensaje=Registro+guardado");
        }
    }
}
