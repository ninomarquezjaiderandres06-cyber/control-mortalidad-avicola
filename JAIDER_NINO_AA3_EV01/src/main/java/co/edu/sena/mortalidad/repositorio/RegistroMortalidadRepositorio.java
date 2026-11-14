package co.edu.sena.mortalidad.repositorio;

import co.edu.sena.mortalidad.modelo.RegistroMortalidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio Spring Data JPA: ya trae insertar, consultar, actualizar y eliminar
 * (save, findAll, findById, deleteById) sin escribir SQL.
 */
public interface RegistroMortalidadRepositorio extends JpaRepository<RegistroMortalidad, Integer> {

    /** Consulta derivada del nombre del metodo: filtra por galpon. */
    List<RegistroMortalidad> findByGalponOrderByFechaDesc(Integer galpon);
}
