package co.edu.sena.mortalidad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicacion Spring Boot.
 * Al ejecutarla se levanta el servidor en http://localhost:8080/
 */
@SpringBootApplication
public class MortalidadApplication {

    public static void main(String[] args) {
        SpringApplication.run(MortalidadApplication.class, args);
    }
}
