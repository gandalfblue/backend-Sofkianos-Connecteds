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
class UpdateUseCaseTest {

    @Autowired
    MapperUtils mapperUtils;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UpdateUseCase updateUseCase;

    @BeforeEach
    public void setUp(){
        questionRepository = mock(QuestionRepository.class);
        mapperUtils = new MapperUtils();
        updateUseCase = new UpdateUseCase(mapperUtils,questionRepository);

    }

    @Test
    public void setUpdateUseCase() {
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
        questionDTO.setDescription("Sofware");
        questionDTO.setCategory("Specialized");


        when(questionRepository.save(Mockito.any(Question.class))).thenReturn(Mono.just(question));

        StepVerifier.create(updateUseCase.apply(questionDTO))

                .expectNextMatches(q -> {

                    assert questionDTO.getId().equalsIgnoreCase(question.getId());
                    assert questionDTO.getUserId().equalsIgnoreCase("U-1234");
                    assert questionDTO.getUserName().equalsIgnoreCase("Juan");
                    assert questionDTO.getCategory().equalsIgnoreCase("Specialized");
                    assert questionDTO.getQuestion().equalsIgnoreCase("¿Do you like Java?");
                    assert questionDTO.getDescription().equalsIgnoreCase("Sofware");
                    return true;
                }).verifyComplete();

        verify(questionRepository).save(Mockito.any(Question.class));
    }
}