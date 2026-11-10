# AA2-EV01 - Codificacion de modulos (JDBC) - Control de mortalidad avicola

## Como ejecutarlo
1. Instalar JDK 17+, Maven y MySQL.
2. Ejecutar `sql/mortalidad.sql` en MySQL (Workbench o consola).
3. En `util/ConexionBD.java` poner su usuario y clave de MySQL.
4. Abrir el proyecto en NetBeans/Eclipse/IntelliJ (proyecto Maven) y ejecutar `Principal`,
   o por consola: `mvn compile exec:java -Dexec.mainClass=co.edu.sena.mortalidad.app.Principal`

## Funcionalidades
Insertar, consultar (todos / por ID), actualizar y eliminar registros de mortalidad por galpon.
