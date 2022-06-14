package co.com.sofka.questions.questionusecases;

import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

/**
 * Clase que representa el caso de uso de eliminar una pregunta por el Id.
 * @author Sofka
 * @version 1.0
 * @since 1.0
 * @See QuestionRepository
 * @See AnswerRepository
 */
@Service
@Validated
public class DeleteUseCase implements Function<String, Mono<Void>> {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public DeleteUseCase(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * Metodo que elimina una pregunta por el Id.
     * @param id Id de la pregunta a eliminar.
     * @return Mono<Void>
     */
    @Override
    public Mono<Void> apply(String id) {
        Objects.requireNonNull(id, "Id is required");
        return questionRepository.deleteById(id)
                .switchIfEmpty(Mono.defer(() -> answerRepository.deleteByQuestionId(id)));
    }
}
