package co.com.sofka.questions.repositories;

import co.com.sofka.questions.collections.AnswerToAnswer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * Representa a una interfaz que hereda de la interfaz ReactiveCrudRepository que tiene SpringBoot, todos sus metodos
 */
@Repository
public interface AnswerToAnswerRepository extends ReactiveCrudRepository<AnswerToAnswer, String> {

    /**
     * Metodo que permite buscar todas las respuestas que esten relacionadas por el id de otra respuesta.
     * @param id
     * @return Flux<AnswerToAnswer>
     */
    Flux<AnswerToAnswer> findAllByAnswerId(String id);

}