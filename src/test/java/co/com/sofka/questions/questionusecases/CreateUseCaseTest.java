package co.com.sofka.questions.questionusecases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
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
class CreateUseCaseTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CreateUseCase createUseCase;

    @Autowired
    MapperUtils mapperUtils = new MapperUtils();

    @BeforeEach
    public void setUp() {

        questionRepository = mock(QuestionRepository.class);
        createUseCase = new CreateUseCase(mapperUtils, questionRepository);

    }

    @Test
    void getValidationCreateTest() {
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

        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));

        StepVerifier.create(createUseCase.apply(questionDTO))

                .expectNextMatches(q -> {

                    assert questionDTO.getId().equals(question.getId());
                    assert questionDTO.getUserId().equals("U-1234");
                    assert questionDTO.getUserName().equals("Juan");
                    assert questionDTO.getCategory().equals("Information Technology");
                    assert questionDTO.getQuestion().equals("¿Do you like Java?");
                    assert questionDTO.getDescription().equals("Technology");
                    return true;
                }).verifyComplete();

        verify(questionRepository).save(Mockito.any(Question.class));
    }
}