package co.com.sofka.questions.answerusecasetest;

import co.com.sofka.questions.answerusecases.MapperUtilsAnswer;
import co.com.sofka.questions.answerusecases.UpdateAnswerToAnswerUseCase;
import co.com.sofka.questions.collections.AnswerToAnswer;
import co.com.sofka.questions.model.AnswerToAnswerDTO;
import co.com.sofka.questions.repositories.AnswerToAnswerRepository;
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
class UpdateAnswerToAnswerUseCaseTest {

    @Autowired
    MapperUtilsAnswer mapperUtilsAnswer;
    @Autowired
    AnswerToAnswerRepository answerToAnswerRepository;
    @Autowired
    UpdateAnswerToAnswerUseCase updateAnswerToAnswerUseCase;

    @BeforeEach
    public void setUp(){
        answerToAnswerRepository = mock(AnswerToAnswerRepository.class);
        mapperUtilsAnswer = new MapperUtilsAnswer();
        updateAnswerToAnswerUseCase = new UpdateAnswerToAnswerUseCase(answerToAnswerRepository, mapperUtilsAnswer);
    }

    @Test
    public void setUpdateAnswerUseCase() {
        AnswerToAnswer answerToAnswer = new AnswerToAnswer();
        answerToAnswer.setId("ATA-001");
        answerToAnswer.setUserId("U-1234");
        answerToAnswer.setUserName("Juan");
        answerToAnswer.setAnswerId("A-01");
        answerToAnswer.setAnswer("Is like");
        answerToAnswer.setPosition(1);

        AnswerToAnswerDTO answerToAnswerDTO = new AnswerToAnswerDTO();
        answerToAnswerDTO.setId("ATA-001");
        answerToAnswerDTO.setUserId("U-1234");
        answerToAnswerDTO.setUserName("Juan");
        answerToAnswerDTO.setAnswerId("A-01");
        answerToAnswerDTO.setAnswer("Not like");
        answerToAnswerDTO.setPosition(1);


        when(answerToAnswerRepository.save(Mockito.any(AnswerToAnswer.class))).thenReturn(Mono.just(answerToAnswer));

        StepVerifier.create(updateAnswerToAnswerUseCase.apply(answerToAnswerDTO))

                .expectNextMatches(answerData -> {

                    assert answerToAnswerDTO.getId().equalsIgnoreCase(answerToAnswer.getId());
                    assert answerToAnswerDTO.getUserId().equalsIgnoreCase("U-1234");
                    assert answerToAnswerDTO.getUserName().equalsIgnoreCase("Juan");
                    assert answerToAnswerDTO.getAnswer().equalsIgnoreCase("Not like");
                    assert answerToAnswerDTO.getAnswerId().equalsIgnoreCase("A-01");
                    assert answerToAnswerDTO.getPosition().equals(1);
                    return true;
                }).verifyComplete();

        verify(answerToAnswerRepository).save(Mockito.any(AnswerToAnswer.class));
    }
}