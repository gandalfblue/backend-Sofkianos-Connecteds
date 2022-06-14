package co.com.sofka.questions.questionusecases;

import co.com.sofka.questions.model.QuestionDTO;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Interfaz funcional de Save question.
 */
@FunctionalInterface
public interface SaveQuestion {

    /**
     * Metodo que guarda una pregunta.     *
     * @param questionDTO Objeto de tipo QuestionDTO
     * @return Mono<String> El id de la pregunta guardada.
     */
    Mono<String> apply(@Valid QuestionDTO questionDTO);
}
