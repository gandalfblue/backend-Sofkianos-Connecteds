package co.com.sofka.questions.answerusecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.AnswerToAnswerDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * The interface funcional de Save answerToAnswer.
 * @author Sofka
 * @version 1.0
 * @since 1.0
 * @see SaveAnswerToAnswer
 * @see AnswerToAnswerDTO
 * @see AnswerDTO
 * @see Mono
 * @see Valid
 */
@FunctionalInterface
public interface SaveAnswerToAnswer {

    /**
     * Save answerToAnswer.
     * @param answerToAnswerDTO the answerToAnswerDTO
     * @return Mono<AnswerDTO>
     */
    Mono<AnswerDTO> apply(@Valid AnswerToAnswerDTO answerToAnswerDTO);
}