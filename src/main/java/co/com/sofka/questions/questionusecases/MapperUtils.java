package co.com.sofka.questions.questionusecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Clase que se encarga de mapear una pregunta de la base de datos a una respuesta del
 * modelo de la aplicacion o viceversa.
 * @author Sofka
 * @version 1.0
 * @see Question
 * @see QuestionDTO
 * @see Function
 */
@Component
public class MapperUtils {

    /**
     * Metodo que se encarga de mapear un DTO de question a la colección para que se guarde en la bse de datos.
     * @return Objeto question de la colección desde la base de datos.
     */
    public Function<QuestionDTO, Question> mapperToQuestion(String id) {
        return updateQuestion -> {
            Question question = new Question();
            question.setId(id);
            question.setUserId(updateQuestion.getUserId());
            question.setUserName(updateQuestion.getUserName());
            question.setCategory(updateQuestion.getCategory());
            question.setQuestion(updateQuestion.getQuestion());
            question.setDescription(updateQuestion.getDescription());
            return question;
        };
    }

    /**
     * Metodo que se encarga de mapear desde la colección pregunta para transferir los datos al DTO.
     * @return Objeto question de la colección desde la base de datos.
     */
    public Function<Question, QuestionDTO> mapEntityToQuestion() {
        return entity -> new QuestionDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getUserName(),
                entity.getQuestion(),
                entity.getDescription(),
                entity.getCategory());
    }
}