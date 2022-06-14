package co.com.sofka.questions.repositories;

import co.com.sofka.questions.collections.Question;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Representa a una interfaz que hereda de la interfaz ReactiveCrudRepository que tiene SpringBoot, todos sus metodos
 */
@Repository
public interface QuestionRepository extends ReactiveCrudRepository<Question, String> {

    /**
     * Metodo que permite buscar todas las preguntas que esten relacionadas por el id del usuario.
     * @param id
     * @return Flux<Question>
     */
    Flux<Question> findByUserId(String userId);
}