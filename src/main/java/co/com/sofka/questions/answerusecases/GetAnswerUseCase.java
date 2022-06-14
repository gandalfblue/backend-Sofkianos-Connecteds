package co.com.sofka.questions.answerusecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.AnswerToAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

/**
 * Clase que implementa el caso de uso traer una respuesta a una respuesta por el Id.
 * @author Sofka
 * @version 1.0
 * @since 1.0
 * @see AnswerRepository
 * @see AnswerToAnswerRepository
 * @see AnswerDTO
 * @see GetAnswerUseCase
 */
@Service
@Validated
public class GetAnswerUseCase implements Function<String, Mono<AnswerDTO>> {
    private final AnswerRepository answerRepository;
    private final AnswerToAnswerRepository answerToAnswerRepository;
    private final MapperUtilsAnswer mapperUtilsAnswer;

    public GetAnswerUseCase(MapperUtilsAnswer mapperUtilsAnswer, AnswerRepository answerRepository,
                            AnswerToAnswerRepository answerToAnswerRepository) {
        this.answerRepository = answerRepository;
        this.answerToAnswerRepository = answerToAnswerRepository;
        this.mapperUtilsAnswer = mapperUtilsAnswer;
    }

    /**
     * Metodo que se encarga de traer la respuesta por el Id.
     * @param answerId Id de la respuesta.
     * @return Objeto que representa la respuesta.
     */
    @Override
    public Mono<AnswerDTO> apply(String answerId) {
        Objects.requireNonNull(answerId, "answerId is required");
        return answerRepository.findById(answerId)
                .map(mapperUtilsAnswer.mapEntityToAnswer())
                .flatMap(mapAnswerAggregate());
    }

    /**
     * Metodo que se encarga de traer la respuesta con su respectiva de lista de respuestas.
     * @return  Objeto que representa la respuesta.
     */
    private Function<AnswerDTO, Mono<AnswerDTO>> mapAnswerAggregate() {
        return answerDTO ->
                Mono.just(answerDTO).zipWith(
                        answerToAnswerRepository.findAllByAnswerId(answerDTO.getId())
                                .map(mapperUtilsAnswer.mapEntityAnswerToAnswer())
                                .collectList(),
                        (answer, answersList) -> {
                            answer.setAnswersList(answersList);
                            return answer;
                        }
                );
    }
}

