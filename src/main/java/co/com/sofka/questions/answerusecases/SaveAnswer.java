package co.com.sofka.questions.answerusecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * The interface funcional de Save answer.
 * @author Sofka
 * @version 1.0
 * @since 1.0
 * @see SaveAnswer
 * @see AnswerDTO
 * @see QuestionDTO
 * @see Mono
 * @see Valid
 */
@FunctionalInterface
public interface SaveAnswer {

    /**
     * Metodo que permite guardar una respuesta.
     * @param answerDTO the answer dto
     * @return Mono<QuestionDTO>
     */
    Mono<QuestionDTO> apply(@Valid AnswerDTO answerDTO);
}
