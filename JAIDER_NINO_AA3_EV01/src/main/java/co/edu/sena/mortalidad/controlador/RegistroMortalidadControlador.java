package co.edu.sena.mortalidad.controlador;

import co.edu.sena.mortalidad.modelo.RegistroMortalidad;
import co.edu.sena.mortalidad.repositorio.RegistroMortalidadRepositorio;

import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador MVC del modulo de mortalidad (CRUD completo).
 */
@Controller
public class RegistroMortalidadControlador {

    private final RegistroMortalidadRepositorio repositorio;

    /** Spring inyecta el repositorio por el constructor. */
    public RegistroMortalidadControlador(RegistroMortalidadRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    /** CONSULTA: lista todos los registros o filtra por galpon. */
    @GetMapping("/")
    public String listar(@RequestParam(required = false) Integer galpon, Model modelo) {
        if (galpon == null) {
            modelo.addAttribute("registros",
                    repositorio.findAll(Sort.by(Sort.Direction.DESC, "fecha")));
        } else {
            modelo.addAttribute("registros", repositorio.findByGalponOrderByFechaDesc(galpon));
        }
        modelo.addAttribute("galponFiltro", galpon);
        return "listado";
    }

    /** Muestra el formulario vacio para un registro nuevo. */
    @GetMapping("/nuevo")
    public String nuevo(Model modelo) {
        modelo.addAttribute("registro", new RegistroMortalidad());
        return "formulario";
    }

    /** Muestra el formulario con los datos de un registro existente. */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model modelo, RedirectAttributes mensajes) {
        return repositorio.findById(id).map(registro -> {
            modelo.addAttribute("registro", registro);
            return "formulario";
        }).orElseGet(() -> {
            mensajes.addFlashAttribute("error", "El registro no existe");
            return "redirect:/";
        });
    }

    /** INSERCION / ACTUALIZACION: save() inserta si no hay id y actualiza si ya existe. */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("registro") RegistroMortalidad registro,
                          BindingResult resultado, RedirectAttributes mensajes) {
        if (resultado.hasErrors()) {
            return "formulario"; // vuelve al formulario mostrando los errores de validacion
        }
        boolean esNuevo = registro.getId() == null;
        repositorio.save(registro);
        mensajes.addFlashAttribute("mensaje", esNuevo ? "Registro guardado" : "Registro actualizado");
        return "redirect:/";
    }

    /** ELIMINACION: borra el registro por id. */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes mensajes) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            mensajes.addFlashAttribute("mensaje", "Registro eliminado");
        } else {
            mensajes.addFlashAttribute("error", "El registro no existe");
        }
        return "redirect:/";
    }
}
