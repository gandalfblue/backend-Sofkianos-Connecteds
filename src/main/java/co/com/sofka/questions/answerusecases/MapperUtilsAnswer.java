package co.com.sofka.questions.answerusecases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.AnswerToAnswer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.AnswerToAnswerDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * Clase que se encarga de mapear una respuesta de la base de datos a una respuesta
 * del modelo de la aplicacion o viceversa.
 * @author Sofka
 * @version 1.0
 * @see Answer
 * @see AnswerDTO
 * @see AnswerToAnswer
 * @see AnswerToAnswerDTO
 * @see Function
 * @since 1.0
 */
@Component
public class MapperUtilsAnswer {

    /**
     * Metodo que se encarga de mapear un DTO de answer a la colección para que se guarde en la bse de datos.
     *
     * @return Objeto respuesta de la colección desde la base de datos.
     */
    public Function<AnswerDTO, Answer> mapperToAnswer() {
        return updateAnswer -> {
            Answer answer = new Answer();

            answer.setPosition(updateAnswer.getPosition());
            answer.setQuestionId(updateAnswer.getQuestionId());
            answer.setUserId(updateAnswer.getUserId());
            answer.setUserName(updateAnswer.getUserName());
            answer.setAnswer(updateAnswer.getAnswer());
            answer.setCreatedAt(updateAnswer.getCreatedAt());
            answer.setUpdatedAt(updateAnswer.getUpdatedAt());
            return answer;
        };
    }

    /**
     * Metodo que se encarga de mapear un DTO de answer por el ID a la colección para que se guarde en la bse de datos.
     *
     * @return Objeto respuesta de la colección desde la base de datos.
     * @Param id ID de la respuesta.
     */
    public Function<AnswerDTO, Answer> mapperToAnswerId(String id) {
        return updateAnswer -> {
            Answer answer = new Answer();
            answer.setId(id);
            answer.setPosition(updateAnswer.getPosition());
            answer.setQuestionId(updateAnswer.getQuestionId());
            answer.setUserId(updateAnswer.getUserId());
            answer.setUserName(updateAnswer.getUserName());
            answer.setAnswer(updateAnswer.getAnswer());
            answer.setCreatedAt(updateAnswer.getCreatedAt());
            answer.setUpdatedAt(updateAnswer.getUpdatedAt());
            return answer;
        };
    }

    /**
     * Metodo que se encarga de mapear un DTO de answerToAnswer a la colección para que se guarde en la base de datos.
     *
     * @return Objeto de AnswerToAnswer de la colección desde la base de datos.
     */
    public Function<AnswerToAnswerDTO, AnswerToAnswer> mapperAnswerToAnswer() {
        return updateAnswer -> {
            AnswerToAnswer answer = new AnswerToAnswer();

            answer.setPosition(updateAnswer.getPosition());
            answer.setUserId(updateAnswer.getUserId());
            answer.setUserName(updateAnswer.getUserName());
            answer.setAnswer(updateAnswer.getAnswer());
            answer.setAnswerId(updateAnswer.getAnswerId());
            answer.setCreatedAt(updateAnswer.getCreatedAt());
            answer.setUpdatedAt(updateAnswer.getUpdatedAt());
            return answer;
        };
    }

    /**
     * Metodo que se encarga de mapear un DTO de answerToAnswer por el ID a la colección para que se guarde en la bse de datos.
     *
     * @return
     * @Param id ID de la respuesta.
     */

    public Function<AnswerToAnswerDTO, AnswerToAnswer> mapperAnswerToAnswerId(String id) {
        return updateAnswer -> {
            AnswerToAnswer answer = new AnswerToAnswer();
            answer.setId(id);
            answer.setPosition(updateAnswer.getPosition());
            answer.setUserId(updateAnswer.getUserId());
            answer.setUserName(updateAnswer.getUserName());
            answer.setAnswer(updateAnswer.getAnswer());
            answer.setAnswerId(updateAnswer.getAnswerId());
            answer.setCreatedAt(updateAnswer.getCreatedAt());
            answer.setUpdatedAt(updateAnswer.getUpdatedAt());
            return answer;
        };
    }

    /**
     * Metodo que se encarga de mapear desde la colección answer para transferir los datos al DTO.
     *
     * @return Objeto de AnswerDTO.
     */
    public Function<Answer, AnswerDTO> mapEntityToAnswer() {
        return entity -> new AnswerDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getUserName(),
                entity.getQuestionId(),
                entity.getAnswer(),
                entity.getPosition(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    /**
     * Metodo que se encarga de mapear desde la colección answerToAnswer para transferir los datos al DTO.
     * @return Objeto de AnswerToAnswerDTO.
     */
    public Function<AnswerToAnswer, AnswerToAnswerDTO> mapEntityAnswerToAnswer() {
        return entity -> new AnswerToAnswerDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getUserName(),
                entity.getAnswerId(),
                entity.getAnswer(),
                entity.getPosition(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}