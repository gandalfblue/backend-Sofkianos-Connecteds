package co.com.sofka.questions.answerusecases;


import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Clase que representa el caso de uso de actualizar una responder de la pregunta.
 * @author Sofka
 * @version 1.0
 * @since 1.0
 * @see AnswerRepository
 * @see AnswerDTO
 * @see Answer
 * @see MapperUtilsAnswer
 */
@Service
@Validated
public class UpdateAnswerUseCase implements UpdateAnswer {
    private final AnswerRepository answerRepository;
    private final MapperUtilsAnswer mapperUtils;

    public UpdateAnswerUseCase(AnswerRepository answerRepository, MapperUtilsAnswer mapperUtils) {
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
    }

    /**
     * Metodo que se encarga de actualizar la respuesta a una pregunta.
     * @param answerDTO Objeto que representa la respuesta.
     * @return Mono<String> Id de la respuesta.
     */
    @Override
    public Mono<String> apply(AnswerDTO answerDTO) {
        Objects.requireNonNull(answerDTO.getId(), "Id of the answer is required");
        return answerRepository
                .save(mapperUtils.mapperToAnswerId(answerDTO.getId()).apply(answerDTO))
                .map(Answer::getId);

    }
}