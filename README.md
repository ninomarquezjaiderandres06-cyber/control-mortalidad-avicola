# Diseño front-end — Control de Mortalidad Avícola

Evidencia GA6-220501096-AA4-EV03 · Diseño front-end que cumpla con los requerimientos del proyecto.
Programa Análisis y Desarrollo de Software (SENA)
Proyecto formativo: Sistema de software para el control y conteo de pollos muertos en galpones avícolas.

## Cómo esta entrega cumple la lista de chequeo

**1. Crea el diseño front-end según los requerimientos del proyecto.**
Se implementaron las 4 interfaces requeridas por el proyecto: inicio de sesión, selección
de galpón (8 galpones, 28.000 aves c/u), registro diario de mortalidad y reportes con
histórico, usando HTML, CSS y JavaScript.

**2. Aplica los componentes seleccionados en la evidencia GA6-220501096-AA3-EV02.**
El diseño visual (paleta de colores, tipografía, tarjetas, formularios, menú de
navegación) corresponde al mockup construido en Figma y documentado en el EV02 de la
actividad AA3. La estructura de pantallas y el flujo de navegación (Login → Galpones →
Registro → Reportes) es el mismo definido en ese mockup.

**3. El diseño front-end es usable.**
Se aplicaron buenas prácticas de usabilidad: objetivos táctiles de al menos 44px, foco de
teclado visible, mensajes de confirmación y error, un único color de alerta reservado
para el dato crítico (mortalidad), y un menú que se adapta a barra inferior en pantallas
móviles.

**4. Aplica el diseño de interfaz realizado anteriormente.**
Reutiliza directamente el sistema de diseño (colores, componentes, layout) generado y
documentado en las evidencias EV02 (mockups de la AA3) y EV02 de componentes front-end
de la AA4, manteniendo consistencia visual en todo el proyecto.

## Tecnologías utilizadas
- HTML5 semántico
- CSS3 (variables/custom properties, Flexbox, CSS Grid, diseño responsivo)
- JavaScript (ES6, vanilla)
- Persistencia local con `localStorage` / `sessionStorage` (datos de demostración)

## Estructura del proyecto
```
index.html              → Inicio de sesión
galpones.html            → Selección de galpón
registro.html            → Registro de mortalidad diaria por galpón
reportes.html             → Tabla y gráfico de mortalidad acumulada
assets/css/styles.css     → Sistema de diseño (colores, tipografía, componentes)
assets/js/app.js          → Datos de galpones y funciones compartidas
```

## Cómo verlo
Abre `index.html` en cualquier navegador moderno. No requiere instalación ni servidor.

Flujo de uso:
1. Inicia sesión con cualquier usuario/contraseña (demo).
2. Selecciona un galpón desde el panel principal.
3. Registra la cantidad de pollos muertos del día.
4. Consulta el resumen y el gráfico en Reportes.
