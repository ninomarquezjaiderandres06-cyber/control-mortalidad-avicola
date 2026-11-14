<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.sena.mortalidad.modelo.RegistroMortalidad" %>
<%!
    private String escapar(String texto) {
        if (texto == null) return "";
        return texto.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
%>
<%
    RegistroMortalidad registro = (RegistroMortalidad) request.getAttribute("registro");
    String error = (String) request.getAttribute("error");
    boolean esEdicion = registro.getId() > 0;
    String fecha = registro.getFecha() == null ? "" : registro.getFecha().toString();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title><%= esEdicion ? "Editar" : "Nuevo" %> registro</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 24px; background: #f4f6f4; }
        h1 { color: #2e5e2e; }
        label { display: block; margin-top: 12px; font-weight: bold; }
        input, select, textarea { padding: 6px; width: 320px; }
        .boton { background: #2e5e2e; color: #fff; padding: 8px 14px; border: 0; text-decoration: none; cursor: pointer; }
        .error { color: #b00020; }
    </style>
</head>
<body>
    <h1><%= esEdicion ? "Editar registro #" + registro.getId() : "Nuevo registro de mortalidad" %></h1>
    <% if (error != null) { %><p class="error"><%= escapar(error) %></p><% } %>

    <%-- Formulario POST: el servlet decide si inserta o actualiza segun el id --%>
    <form method="post" action="registros">
        <input type="hidden" name="accion" value="guardar">
        <input type="hidden" name="id" value="<%= esEdicion ? registro.getId() : "" %>">

        <label for="galpon">Galpon (1-8)</label>
        <input type="number" name="galpon" id="galpon" min="1" max="8" required
               value="<%= registro.getGalpon() == 0 ? "" : registro.getGalpon() %>">

        <label for="fecha">Fecha</label>
        <input type="date" name="fecha" id="fecha" required value="<%= fecha %>">

        <label for="avesMuertas">Aves muertas</label>
        <input type="number" name="avesMuertas" id="avesMuertas" min="0" max="28000" required
               value="<%= registro.getAvesMuertas() %>">

        <label for="descartes">Descartes</label>
        <input type="number" name="descartes" id="descartes" min="0" max="28000" required
               value="<%= registro.getDescartes() %>">

        <label for="observaciones">Observaciones</label>
        <textarea name="observaciones" id="observaciones" maxlength="255" rows="3"><%= escapar(registro.getObservaciones()) %></textarea>

        <br><br>
        <button class="boton" type="submit">Guardar</button>
        <a class="boton" href="registros">Cancelar</a>
    </form>
</body>
</html>
