package co.com.sofka.questions.questionusecases;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Function;

/**
 * Clase que implementa el caso de uso de traer todas las preguntas asociadas a un usuario por el id del usuario.
 * @author Sofka
 * @version 1.0
 * @since 1.0
 * @see QuestionRepository
 * @see QuestionDTO
 * @see MapperUtils
 */
@Service
@Validated
public class OwnerListUseCase implements Function<String, Flux<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;

    public OwnerListUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
    }

    /**
     * Metodo que implementa el caso de uso de traer todas las preguntas asociadas a un usuario por el id del usuario.
     * @param userId the function argument
     * @return
     */
    @Override
    public Flux<QuestionDTO> apply(String userId) {
        return questionRepository.findByUserId(userId)
                .map(mapperUtils.mapEntityToQuestion());
    }
}
