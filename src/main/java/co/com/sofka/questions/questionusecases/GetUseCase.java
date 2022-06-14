package co.com.sofka.questions.questionusecases;

import co.com.sofka.questions.answerusecases.MapperUtilsAnswer;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

/**
 * Clase que implementa el caso de uso traer una pregunta por su Id.
 *
 * @author Sofka
 * @version 1.0
 * @see QuestionRepository
 * @see QuestionDTO
 * @see AnswerRepository
 * @see MapperUtilsAnswer
 * @since 1.0
 */
@Service
@Validated
public class GetUseCase implements Function<String, Mono<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final MapperUtils mapperUtils;
    private final MapperUtilsAnswer mapperUtilsAnswer;

    public GetUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository, AnswerRepository answerRepository, MapperUtilsAnswer mapperUtilsAnswer) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.mapperUtils = mapperUtils;
        this.mapperUtilsAnswer = mapperUtilsAnswer;
    }

    /**
     * Metodo que trae una pregunta por su Id.
     * @param id Id de la pregunta a traer.
     * @return Mono<QuestionDTO> Objeto pregunta traida por su Id.
     */
    @Override
    public Mono<QuestionDTO> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return questionRepository.findById(id)
                .map(mapperUtils.mapEntityToQuestion())
                .flatMap(mapQuestionAggregate());
    }

    /**
     * Metodo que trae las respuestas de una pregunta por su Id.
     * @return Mono<QuestionDTO> Objeto pregunta traida por su Id.
     */
    private Function<QuestionDTO, Mono<QuestionDTO>> mapQuestionAggregate() {
        return questionDTO ->
                Mono.just(questionDTO).zipWith(
                        answerRepository.findAllByQuestionId(questionDTO.getId())
                                .map(mapperUtilsAnswer.mapEntityToAnswer())
                                .collectList(),
                        (question, answers) -> {
                            question.setAnswers(answers);
                            return question;
                        }
                );
    }
}
