package co.com.sofka.questions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase que contiene el metodo main de la aplicacion.
 * @author Sofka
 * @version 1.0
 * @since 1.0
 * @see SpringBootApplication
 * @see SpringApplication
 */
@SpringBootApplication
public class QuestionsApplication {

    /**
     * Metodo main de la aplicacion.
     * @param args argumentos de entrada.
     */
    public static void main(String[] args) {
        SpringApplication.run(QuestionsApplication.class, args);
    }
}
