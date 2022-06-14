package co.com.sofka.questions.questionusecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class DeleteUseCaseTest {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    DeleteUseCase deleteUseCase;

    @BeforeEach
    public void setup() {
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        deleteUseCase = new DeleteUseCase(answerRepository, questionRepository);
    }

    @Test
    void getValidationTest() {
        Question question = new Question();
        question.setId("Q-01");
        question.setUserId("U-1234");
        question.setUserName("Juan");
        question.setQuestion("¿Do you like Java?");
        question.setDescription("Technology");
        question.setCategory("Information Technology");


        Mono.just(question).flatMap(questionRepository::save).subscribe();

        when(questionRepository.deleteById(question.getId())).thenReturn(Mono.empty());

        StepVerifier.create(deleteUseCase.apply(question.getId()))
                .expectNextMatches(pregunta->{
                    assert pregunta.equals(question);
                    return true;
                }).expectComplete();

        verify(questionRepository).deleteById(question.getId());
    }
}