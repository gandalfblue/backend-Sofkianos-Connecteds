package co.com.sofka.questions.answerusecasetest;

import co.com.sofka.questions.answerusecases.AddAnswerUseCase;
import co.com.sofka.questions.answerusecases.MapperUtilsAnswer;
import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.questionusecases.GetUseCase;
import co.com.sofka.questions.questionusecases.MapperUtils;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddAnswerUseCaseTest {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    AddAnswerUseCase addAnswerUseCase;
    @Autowired
    GetUseCase getUseCase;

    @BeforeEach
    public void setup() {
        MapperUtilsAnswer mapperUtilsAnswer = new MapperUtilsAnswer();
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getUseCase = new GetUseCase(mapperUtils, questionRepository, answerRepository, mapperUtilsAnswer);
        addAnswerUseCase = new AddAnswerUseCase(mapperUtilsAnswer, answerRepository, getUseCase);
    }

    @Test
    public void addAnswerTest() {

        MapperUtilsAnswer mapperUtilsAnswer = new MapperUtilsAnswer();

        Answer answer = new Answer();
        answer.setId("A-001");
        answer.setUserId("U-1234");
        answer.setUserName("Juan");
        answer.setQuestionId("Q-01");
        answer.setAnswer("Is like");
        answer.setPosition(1);

        AnswerDTO answerdto = mapperUtilsAnswer.mapEntityToAnswer().apply(answer);

        Question question = new Question();
        question.setId("Q-01");
        question.setUserId("U-1234");
        question.setUserName("Juan");
        question.setQuestion("¿Do you like Java?");
        question.setDescription("Technology");
        question.setCategory("Information Technology");

        when(questionRepository.findById(any(String.class))).thenReturn(Mono.just(question));
        when(answerRepository.save(any(Answer.class))).thenReturn(Mono.just(answer));
        when(answerRepository.findAllByQuestionId(any(String.class))).thenReturn(Flux.empty());

        StepVerifier.create(addAnswerUseCase.apply(answerdto))
                .expectNextMatches(questiondto -> {
                    assert questiondto.getId().equals("Q-01");
                    assert questiondto.getUserId().equals("U-1234");
                    assert questiondto.getUserName().equals("Juan");
                    assert questiondto.getQuestion().equals("¿Do you like Java?");
                    assert questiondto.getDescription().equals("Technology");
                    assert questiondto.getCategory().equals("Information Technology");
                    assert questiondto.getAnswers().contains(answerdto);
                    return true;
                })
                .verifyComplete();

        verify(questionRepository).findById("Q-01");
    }
}