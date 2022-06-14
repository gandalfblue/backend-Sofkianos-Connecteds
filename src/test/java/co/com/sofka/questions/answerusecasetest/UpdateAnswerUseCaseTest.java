package co.com.sofka.questions.answerusecasetest;

import co.com.sofka.questions.answerusecases.MapperUtilsAnswer;
import co.com.sofka.questions.answerusecases.UpdateAnswerUseCase;
import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UpdateAnswerUseCaseTest {

    @Autowired
    MapperUtilsAnswer mapperUtilsAnswer;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    UpdateAnswerUseCase updateAnswerUseCase;

    @BeforeEach
    public void setUp(){
        answerRepository = mock(AnswerRepository.class);
        mapperUtilsAnswer = new MapperUtilsAnswer();
        updateAnswerUseCase = new UpdateAnswerUseCase(answerRepository, mapperUtilsAnswer);
    }

    @Test
    public void setUpdateAnswerUseCase() {
        Answer answer = new Answer();
        answer.setId("A-001");
        answer.setUserId("U-1234");
        answer.setUserName("Juan");
        answer.setQuestionId("Q-01");
        answer.setAnswer("Is like");
        answer.setPosition(1);

        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId("A-001");
        answerDTO.setUserId("U-1234");
        answerDTO.setUserName("Juan");
        answerDTO.setQuestionId("Q-01");
        answerDTO.setAnswer("Is like");
        answerDTO.setPosition(1);


        when(answerRepository.save(Mockito.any(Answer.class))).thenReturn(Mono.just(answer));

        StepVerifier.create(updateAnswerUseCase.apply(answerDTO))

                .expectNextMatches(answerData -> {

                    assert answerDTO.getId().equalsIgnoreCase(answer.getId());
                    assert answerDTO.getUserId().equalsIgnoreCase("U-1234");
                    assert answerDTO.getUserName().equalsIgnoreCase("Juan");
                    assert answerDTO.getAnswer().equalsIgnoreCase("Is like");
                    assert answerDTO.getQuestionId().equalsIgnoreCase("Q-01");
                    assert answerDTO.getPosition().equals(1);
                    return true;
                }).verifyComplete();

        verify(answerRepository).save(Mockito.any(Answer.class));
    }
}