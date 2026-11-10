package co.edu.sena.mortalidad.modelo;

import java.time.LocalDate;

/**
 * Clase de modelo que representa un registro diario de mortalidad
 * de un galpon (tabla registro_mortalidad).
 */
public class RegistroMortalidad {

    private int id;
    private int galpon;
    private LocalDate fecha;
    private int avesMuertas;
    private int descartes;
    private String observaciones;

    /** Constructor vacio. */
    public RegistroMortalidad() {
    }

    /** Constructor completo (usado al leer desde la base de datos). */
    public RegistroMortalidad(int id, int galpon, LocalDate fecha, int avesMuertas,
                              int descartes, String observaciones) {
        this.id = id;
        this.galpon = galpon;
        this.fecha = fecha;
        this.avesMuertas = avesMuertas;
        this.descartes = descartes;
        this.observaciones = observaciones;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getGalpon() { return galpon; }
    public void setGalpon(int galpon) { this.galpon = galpon; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public int getAvesMuertas() { return avesMuertas; }
    public void setAvesMuertas(int avesMuertas) { this.avesMuertas = avesMuertas; }

    public int getDescartes() { return descartes; }
    public void setDescartes(int descartes) { this.descartes = descartes; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "ID: " + id + " | Galpon: " + galpon + " | Fecha: " + fecha
                + " | Muertas: " + avesMuertas + " | Descartes: " + descartes
                + " | Obs: " + (observaciones == null ? "" : observaciones);
    }
}
