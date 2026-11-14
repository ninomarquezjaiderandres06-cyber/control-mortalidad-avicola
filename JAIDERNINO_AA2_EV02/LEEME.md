# AA2-EV02 - Modulo web con Servlets y JSP - Control de mortalidad avicola

## Requisitos
JDK 17+, Maven, MySQL, Apache Tomcat 10.1 (usa `jakarta.servlet`; Tomcat 9 NO sirve con este codigo).

## Pasos
1. Ejecutar `sql/mortalidad.sql` en MySQL.
2. Poner usuario y clave en `util/ConexionBD.java`.
3. `mvn clean package` -> genera `target/mortalidad.war`.
4. Copiar el WAR a la carpeta `webapps` de Tomcat (o desplegarlo desde NetBeans/Eclipse) e iniciar Tomcat.
5. Abrir http://localhost:8080/mortalidad/

## Elementos que cumple la evidencia
- Formularios HTML con servlet (`RegistroMortalidadServlet`, ruta `/registros`).
- Metodos GET (listar, filtrar, abrir formularios) y POST (guardar, eliminar).
- Elementos JSP: directivas (`<%@ page %>`), declaraciones (`<%! %>`), scriptlets (`<% %>`), expresiones (`<%= %>`) y comentarios JSP.
- CRUD completo con validaciones en el servidor.
