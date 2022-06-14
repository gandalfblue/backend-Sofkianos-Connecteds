package co.com.sofka.questions.repositories;

import co.com.sofka.questions.collections.Answer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Representa a una interfaz que hereda de la interfaz ReactiveCrudRepository que tiene SpringBoot, todos sus metodos.
 */
@Repository
public interface AnswerRepository extends ReactiveCrudRepository<Answer, String> {

    /**
     * Metodo que permite buscar todas las respuestas que esten relacionadas por el id de la pregunta.
     * @param id
     * @return
     */
    Flux<Answer> findAllByQuestionId(String id);

    /**
     * Metodo que permite eliminar todas las respuestas que esten relacionadas por el id de la pregunta.
     * @param questionId
     * @return  Mono<Void>
     */
    Mono<Void> deleteByQuestionId(String questionId);
}