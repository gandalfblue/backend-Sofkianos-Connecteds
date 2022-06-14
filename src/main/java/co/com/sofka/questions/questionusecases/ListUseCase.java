package co.com.sofka.questions.questionusecases;

import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

/**
 * Clase que implementa el caso de uso traer la lista de preguntas.
 * @author Sofka
 * @version 1.0
 * @Since 1.0
 * @see QuestionRepository
 * @see QuestionDTO
 * @see MapperUtils
 */
@Service
@Validated
public class ListUseCase implements Supplier<Flux<QuestionDTO>> {
    private final QuestionRepository questionRepository;
    private final MapperUtils mapperUtils;

    public ListUseCase(MapperUtils mapperUtils, QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
        this.mapperUtils = mapperUtils;
    }

    /**
     * Metodo que trae la lista de preguntas.
     * @return Flux<QuestionDTO> Lista de preguntas.
     */
    @Override
    public Flux<QuestionDTO> get() {
        return questionRepository.findAll()
                .map(mapperUtils.mapEntityToQuestion());
    }
}
