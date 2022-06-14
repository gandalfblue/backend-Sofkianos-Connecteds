package co.com.sofka.questions.answerusecases;


import co.com.sofka.questions.collections.AnswerToAnswer;
import co.com.sofka.questions.model.AnswerToAnswerDTO;
import co.com.sofka.questions.repositories.AnswerToAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

/**
 * Clase que representa el caso de uso de actualizar una respuesta de la respuesta.
 * @author Sofka
 * @version 1.0
 * @since 1.0
 * @see AnswerToAnswerRepository
 * @see AnswerToAnswerDTO
 * @see AnswerToAnswer
 * @see MapperUtilsAnswer
 */
@Service
@Validated
public class UpdateAnswerToAnswerUseCase implements Function<AnswerToAnswerDTO, Mono<String>> {
    private final AnswerToAnswerRepository answerToAnswerRepository;
    private final MapperUtilsAnswer mapperUtils;

    public UpdateAnswerToAnswerUseCase(AnswerToAnswerRepository answerToAnswerRepository, MapperUtilsAnswer mapperUtils) {
        this.answerToAnswerRepository = answerToAnswerRepository;
        this.mapperUtils = mapperUtils;
    }

    /**
     * Metodo que se encarga de actualizar la respuesta a una respuesta.
     * @param answerToAnswerDTO Objeto que representa la respuesta.
     * @return Mono<String> El Id la respuesta.
     */
    @Override
    public Mono<String> apply(AnswerToAnswerDTO answerToAnswerDTO) {
        Objects.requireNonNull(answerToAnswerDTO.getId(), "Id of the answer is required");
        return answerToAnswerRepository
                .save(mapperUtils.mapperAnswerToAnswerId(answerToAnswerDTO.getId()).apply(answerToAnswerDTO))
                .map(AnswerToAnswer::getId);

    }
}