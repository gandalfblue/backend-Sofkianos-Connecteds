package co.com.sofka.questions.questionusecases;

import co.com.sofka.questions.answerusecases.MapperUtilsAnswer;
import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
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
class GetUseCaseTest {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    GetUseCase getUseCase;
    @Autowired
    MapperUtilsAnswer mapperUtilsAnswer;

    @BeforeEach
    public void setup() {
        MapperUtils mapperUtils = new MapperUtils();
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        getUseCase = new GetUseCase(mapperUtils, questionRepository, answerRepository, mapperUtilsAnswer);
    }

    @Test
    void getValidationTest() {

        Answer answer = new Answer();
        answer.setId("A-789");
        answer.setUserId("U-456");
        answer.setUserName("Julian");
        answer.setQuestionId("idQuestion");
        answer.setPosition(1);
        answer.setAnswer("Is a framework");

        Question question = new Question();
        question.setId("Q-01");
        question.setUserId("U-1234");
        question.setUserName("Juan");
        question.setQuestion("¿Do you like Java?");
        question.setDescription("Technology");
        question.setCategory("Information Technology");

        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setUserId(question.getUserId());
        questionDTO.setUserName(question.getUserName());
        questionDTO.setQuestion(question.getQuestion());
        questionDTO.setDescription(question.getDescription());
        questionDTO.setCategory(question.getCategory());

        AnswerDTO answerdto = mapperUtilsAnswer.mapEntityToAnswer().apply(answer);

        when(questionRepository.findById(Mockito.any(String.class))).thenReturn(Mono.just(question));
        when(answerRepository.findAllByQuestionId(question.getId())).thenReturn(Flux.just(answer));

        StepVerifier.create(getUseCase.apply(question.getId()))
                .expectNextMatches(questionData -> {
                    assert questionData.getId().equals(questionDTO.getId());
                    assert questionData.getUserId().equals(questionDTO.getUserId());
                    assert questionData.getUserName().equals(questionDTO.getUserName());
                    assert questionData.getCategory().equals(questionDTO.getCategory());
                    assert questionData.getQuestion().equals(questionDTO.getQuestion());
                    assert questionData.getDescription().equals(questionDTO.getDescription());
                    assert questionData.getAnswers().contains(answerdto);
                    return true;
                }).verifyComplete();

        verify(questionRepository).findById(question.getId());
        verify(answerRepository).findAllByQuestionId(question.getId());
    }
}