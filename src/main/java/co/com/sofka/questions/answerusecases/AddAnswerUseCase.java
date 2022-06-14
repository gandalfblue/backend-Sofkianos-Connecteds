package co.com.sofka.questions.answerusecases;

import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.questionusecases.GetUseCase;
import co.com.sofka.questions.repositories.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Clase que representa el caso de uso de responder una respuesta a una pregunta.
 * @author Sofka
 * @version 1.0
 * @since 1.0
 * @see AnswerRepository
 * @see AnswerDTO
 * @see QuestionDTO
 * @see GetUseCase
 */
@Service
@Validated
public class AddAnswerUseCase implements SaveAnswer {

    private final AnswerRepository answerRepository;
    private final MapperUtilsAnswer mapperUtilsAnswer;
    private final GetUseCase getUseCase;


    public AddAnswerUseCase(MapperUtilsAnswer mapperUtilsAnswer, AnswerRepository answerRepository, GetUseCase getUseCase) {
        this.answerRepository = answerRepository;
        this.mapperUtilsAnswer = mapperUtilsAnswer;
        this.getUseCase = getUseCase;
    }

    /**
     * Metodo que se encarga de guardar la respuesta a una pregunta.
     * @param answerDTO Objeto que representa la respuesta.
     * @return Objeto que representa la respuesta.
     */
    @Override
    public Mono<QuestionDTO> apply(AnswerDTO answerDTO) {
        Objects.requireNonNull(answerDTO.getQuestionId(), "Id of the question is required");
        return getUseCase.apply(answerDTO.getQuestionId())
                .flatMap(question ->
                        answerRepository.save(mapperUtilsAnswer.mapperToAnswer().apply(answerDTO))
                                .map(answer -> {
                                    question.getAnswers().add(
                                            mapperUtilsAnswer.mapEntityToAnswer().apply(answer)
                                    );
                                    return question;
                                })
                );
    }
}
