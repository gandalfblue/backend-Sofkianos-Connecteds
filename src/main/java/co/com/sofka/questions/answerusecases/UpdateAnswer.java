package co.com.sofka.questions.answerusecases;

import co.com.sofka.questions.model.AnswerDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * The interface funcional de Update answer.
 */
@FunctionalInterface
public interface UpdateAnswer {

    /**
     * Metodo que actualiza una respuesta.
     * @param answerDTO Objeto que contiene la respuesta a actualizar.
     * @return Mono<String>.
     */
    Mono<String> apply(@Valid AnswerDTO answerDTO);
}