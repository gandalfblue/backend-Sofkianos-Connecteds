package co.com.sofka.questions.answerusecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.AnswerToAnswerDTO;
import co.com.sofka.questions.repositories.AnswerToAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Clase que representa el caso de uso de responder a una respuesta.
 * @version 1.0
 * @since 1.0
 * @see AnswerToAnswerRepository
 * @see AnswerToAnswerDTO
 * @see AnswerDTO
 * @see GetAnswerUseCase
 */
@Service
@Validated
public class AddAnswerToAnswerUseCase implements SaveAnswerToAnswer {

    private final AnswerToAnswerRepository answerToAnswerRepository;
    private final MapperUtilsAnswer mapperUtilsAnswer;
    private final GetAnswerUseCase getAnswerUseCase;

    public AddAnswerToAnswerUseCase(MapperUtilsAnswer mapperUtilsAnswer,
                                    AnswerToAnswerRepository answerToAnswerRepository, GetAnswerUseCase getAnswerUseCase) {
        this.answerToAnswerRepository = answerToAnswerRepository;
        this.getAnswerUseCase = getAnswerUseCase;
        this.mapperUtilsAnswer = mapperUtilsAnswer;
    }

    /**
     * Metodo que se encarga de guardar la respuesta a una respuesta.
     * @param answerToAnswerDTO Objeto que representa la respuesta a la respuesta.
     * @return Objeto que representa la respuesta a la respuesta.
     */
    public Mono<AnswerDTO> apply(AnswerToAnswerDTO answerToAnswerDTO) {
        Objects.requireNonNull(answerToAnswerDTO.getAnswerId(), "Id of the answer is required");
        return getAnswerUseCase.apply(answerToAnswerDTO.getAnswerId())
                .flatMap(answer ->
                        answerToAnswerRepository.save(mapperUtilsAnswer.mapperAnswerToAnswer().apply(answerToAnswerDTO))
                                .map(answerList -> {
                                    answerToAnswerDTO.setId(answerList.getId());
                                    answer.getAnswersList().add(answerToAnswerDTO);
                                    return answer;
                                })
                );
    }
}
