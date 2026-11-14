# AA3-EV01 - Modulo con framework Java (Spring Boot) - Control de mortalidad avicola

## Requisitos
JDK 17+, Maven y MySQL.

## Pasos
1. Ejecutar `sql/mortalidad.sql` en MySQL (crea la base `mortalidad_avicola`).
2. Poner usuario y clave de MySQL en `src/main/resources/application.properties`.
3. Ejecutar `mvn spring-boot:run` (o correr `MortalidadApplication` desde NetBeans/Eclipse/IntelliJ).
4. Abrir http://localhost:8080/

## Arquitectura
- `modelo`: entidad JPA con validaciones (`@NotNull`, `@Min`, `@Max`, `@PastOrPresent`, `@Size`).
- `repositorio`: Spring Data JPA (CRUD sin escribir SQL).
- `controlador`: controlador MVC con listar/filtrar, crear, editar y eliminar.
- `templates`: vistas Thymeleaf (`listado.html`, `formulario.html`).
