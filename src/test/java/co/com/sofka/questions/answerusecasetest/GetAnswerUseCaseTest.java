package co.com.sofka.questions.answerusecasetest;

import co.com.sofka.questions.answerusecases.GetAnswerUseCase;
import co.com.sofka.questions.answerusecases.MapperUtilsAnswer;
import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.AnswerToAnswer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.AnswerToAnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.AnswerToAnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetAnswerUseCaseTest {

    @Autowired
    AnswerToAnswerRepository answerToAnswerRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    GetAnswerUseCase getAnswerUseCase;
    @Autowired
    MapperUtilsAnswer mapperUtilsAnswer;

    @BeforeEach
    public void setup() {

        answerToAnswerRepository = mock(AnswerToAnswerRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getAnswerUseCase = new GetAnswerUseCase(mapperUtilsAnswer, answerRepository, answerToAnswerRepository);
    }

    @Test
    void getValidationTest() {
        Answer answer = new Answer();
        answer.setId("A-001");
        answer.setUserId("U-1234");
        answer.setUserName("Juan");
        answer.setQuestionId("Q-01");
        answer.setAnswer("Is like");
        answer.setPosition(1);

        AnswerToAnswer answerToAnswer = new AnswerToAnswer();
        answerToAnswer.setId("ATA-01");
        answerToAnswer.setUserId("U-1234");
        answerToAnswer.setUserName("Juan");
        answerToAnswer.setAnswer("Not like");
        answerToAnswer.setAnswerId("A-001");
        answerToAnswer.setPosition(1);

        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setId(answer.getId());
        answerDTO.setUserId(answer.getUserId());
        answerDTO.setUserName(answer.getUserName());
        answerDTO.setAnswer(answer.getAnswer());
        answerDTO.setQuestionId(answer.getQuestionId());
        answerDTO.setPosition(answer.getPosition());

        AnswerToAnswerDTO answerToAnswerDTO = mapperUtilsAnswer.mapEntityAnswerToAnswer().apply(answerToAnswer);

        when(answerRepository.findById(Mockito.any(String.class))).thenReturn(Mono.just(answer));
        when(answerToAnswerRepository.findAllByAnswerId(answer.getId())).thenReturn(Flux.just(answerToAnswer));

        StepVerifier.create(getAnswerUseCase.apply(answer.getId()))
                .expectNextMatches(answerData -> {
                    assert answerData.getId().equals(answerDTO.getId());
                    assert answerData.getUserId().equals(answerDTO.getUserId());
                    assert answerData.getUserName().equals(answerDTO.getUserName());
                    assert answerData.getAnswer().equals(answerDTO.getAnswer());
                    assert answerData.getQuestionId().equals(answerDTO.getQuestionId());
                    assert answerData.getPosition().equals(answerDTO.getPosition());
                    return true;
                }).verifyComplete();
        verify(answerRepository).findById(answer.getId());
        verify(answerToAnswerRepository).findAllByAnswerId(answer.getId());
    }
}