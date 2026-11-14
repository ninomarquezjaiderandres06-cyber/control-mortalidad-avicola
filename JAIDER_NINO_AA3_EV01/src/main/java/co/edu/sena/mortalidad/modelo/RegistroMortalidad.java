package co.edu.sena.mortalidad.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Entidad JPA mapeada a la tabla registro_mortalidad.
 * Las anotaciones de validacion se revisan automaticamente en el formulario.
 */
@Entity
@Table(name = "registro_mortalidad")
public class RegistroMortalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "El galpon es obligatorio")
    @Min(value = 1, message = "El galpon minimo es 1")
    @Max(value = 8, message = "El galpon maximo es 8")
    private Integer galpon;

    @NotNull(message = "La fecha es obligatoria")
    @PastOrPresent(message = "La fecha no puede ser futura")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fecha;

    @NotNull(message = "Indique las aves muertas")
    @Min(value = 0, message = "No puede ser negativo")
    @Max(value = 28000, message = "Maximo 28000")
    @Column(name = "aves_muertas")
    private Integer avesMuertas = 0;

    @NotNull(message = "Indique los descartes")
    @Min(value = 0, message = "No puede ser negativo")
    @Max(value = 28000, message = "Maximo 28000")
    private Integer descartes = 0;

    @Size(max = 255, message = "Maximo 255 caracteres")
    private String observaciones;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getGalpon() { return galpon; }
    public void setGalpon(Integer galpon) { this.galpon = galpon; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Integer getAvesMuertas() { return avesMuertas; }
    public void setAvesMuertas(Integer avesMuertas) { this.avesMuertas = avesMuertas; }

    public Integer getDescartes() { return descartes; }
    public void setDescartes(Integer descartes) { this.descartes = descartes; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
