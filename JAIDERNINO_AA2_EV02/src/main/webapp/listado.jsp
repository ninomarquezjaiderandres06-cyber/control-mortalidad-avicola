<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, co.edu.sena.mortalidad.modelo.RegistroMortalidad" %>
<%!
    /** Declaracion JSP: escapa caracteres HTML para evitar inyeccion de codigo. */
    private String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
%>
<%
    // Scriptlet: recupera la lista enviada por el servlet
    List<RegistroMortalidad> registros = (List<RegistroMortalidad>) request.getAttribute("registros");
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
    int totalMuertas = 0;
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Control de mortalidad avicola</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 24px; background: #f4f6f4; }
        h1 { color: #2e5e2e; }
        table { border-collapse: collapse; width: 100%; background: #fff; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; }
        th { background: #2e5e2e; color: #fff; }
        .ok { color: #1b6e1b; } .error { color: #b00020; }
        .boton { background: #2e5e2e; color: #fff; padding: 6px 12px; border: 0; text-decoration: none; cursor: pointer; }
        .peligro { background: #b00020; }
        form.en-linea { display: inline; }
    </style>
</head>
<body>
    <h1>Control de mortalidad avicola</h1>

    <% if (mensaje != null) { %><p class="ok"><%= escapar(mensaje) %></p><% } %>
    <% if (error != null) { %><p class="error"><%= escapar(error) %></p><% } %>

    <%-- Formulario GET: filtra la lista por galpon --%>
    <form method="get" action="registros">
        <label for="galpon">Filtrar por galpon:</label>
        <select name="galpon" id="galpon">
            <option value="">Todos</option>
            <% for (int i = 1; i <= 8; i++) { %>
                <option value="<%= i %>"><%= i %></option>
            <% } %>
        </select>
        <button class="boton" type="submit">Filtrar</button>
        <a class="boton" href="registros?accion=nuevo">+ Nuevo registro</a>
    </form>
    <br>

    <table>
        <tr>
            <th>ID</th><th>Galpon</th><th>Fecha</th><th>Aves muertas</th>
            <th>Descartes</th><th>Observaciones</th><th>Acciones</th>
        </tr>
        <% if (registros == null || registros.isEmpty()) { %>
            <tr><td colspan="7">No hay registros.</td></tr>
        <% } else {
               for (RegistroMortalidad r : registros) {
                   totalMuertas += r.getAvesMuertas(); %>
            <tr>
                <td><%= r.getId() %></td>
                <td><%= r.getGalpon() %></td>
                <td><%= r.getFecha() %></td>
                <td><%= r.getAvesMuertas() %></td>
                <td><%= r.getDescartes() %></td>
                <td><%= escapar(r.getObservaciones()) %></td>
                <td>
                    <a class="boton" href="registros?accion=editar&id=<%= r.getId() %>">Editar</a>
                    <%-- Formulario POST: elimina el registro --%>
                    <form class="en-linea" method="post" action="registros"
                          onsubmit="return confirm('Eliminar el registro <%= r.getId() %>?');">
                        <input type="hidden" name="accion" value="eliminar">
                        <input type="hidden" name="id" value="<%= r.getId() %>">
                        <button class="boton peligro" type="submit">Eliminar</button>
                    </form>
                </td>
            </tr>
        <%     }
           } %>
    </table>
    <p><strong>Total aves muertas (lista mostrada): <%= totalMuertas %></strong></p>
</body>
</html>
