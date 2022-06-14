package co.com.sofka.questions.answerusecasetest;

import co.com.sofka.questions.answerusecases.AddAnswerToAnswerUseCase;
import co.com.sofka.questions.answerusecases.GetAnswerUseCase;
import co.com.sofka.questions.answerusecases.MapperUtilsAnswer;
import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.AnswerToAnswer;
import co.com.sofka.questions.model.AnswerToAnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.AnswerToAnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddAnswerToAnswerUseCaseTest {

    @Autowired
    AnswerToAnswerRepository answerToAnswerRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    AddAnswerToAnswerUseCase addAnswerToAnswerUseCase;
    @Autowired
    GetAnswerUseCase getAnswerUseCase;

    @BeforeEach
    public void setup() {
        MapperUtilsAnswer mapperUtilsAnswer = new MapperUtilsAnswer();
        answerRepository = mock(AnswerRepository.class);
        answerToAnswerRepository = mock(AnswerToAnswerRepository.class);
        getAnswerUseCase = new GetAnswerUseCase(mapperUtilsAnswer, answerRepository, answerToAnswerRepository);
        addAnswerToAnswerUseCase = new AddAnswerToAnswerUseCase(mapperUtilsAnswer, answerToAnswerRepository, getAnswerUseCase);
    }

    @Test
    public void setAddAnswerToAnswerTest() {
        MapperUtilsAnswer mapperUtilsAnswer = new MapperUtilsAnswer();
        AnswerToAnswer answerToAnswer = new AnswerToAnswer();
        answerToAnswer.setId("ATA-01");
        answerToAnswer.setUserId("U-1234");
        answerToAnswer.setUserName("Juan");
        answerToAnswer.setAnswer("Is like");
        answerToAnswer.setAnswerId("A-001");
        answerToAnswer.setPosition(1);

        AnswerToAnswerDTO answerToAnswerDTO = mapperUtilsAnswer.mapEntityAnswerToAnswer().apply(answerToAnswer);

        Answer answer = new Answer();
        answer.setId("A-001");
        answer.setUserId("U-1234");
        answer.setUserName("Juan");
        answer.setAnswer("Yes, I like it");
        answer.setPosition(1);
        answer.setQuestionId("Q-01");

        when(answerRepository.findById(any(String.class))).thenReturn(Mono.just(answer));
        when(answerToAnswerRepository.save(any(AnswerToAnswer.class))).thenReturn(Mono.just(answerToAnswer));
        when(answerToAnswerRepository.findAllByAnswerId(any(String.class))).thenReturn(Flux.empty());

        StepVerifier.create(addAnswerToAnswerUseCase.apply(answerToAnswerDTO))
                .expectNextMatches(answerDTO -> {
                    assert answerDTO.getId().equals("A-001");
                    assert answerDTO.getUserId().equals("U-1234");
                    assert answerDTO.getUserName().equals("Juan");
                    assert answerDTO.getAnswer().equals("Yes, I like it");
                    assert answerDTO.getPosition().equals(1);
                    assert answerDTO.getQuestionId().equals("Q-01");
                    return true;
                })
                .verifyComplete();

        verify(answerRepository).findById("A-001");
    }
}