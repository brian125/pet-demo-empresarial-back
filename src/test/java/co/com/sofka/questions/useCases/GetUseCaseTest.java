package co.com.sofka.questions.useCases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.utils.Category;
import co.com.sofka.questions.utils.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

@SpringBootTest
class GetUseCaseTest {
    @MockBean
    private QuestionRepository questionRepository;
    @SpyBean
    private GetUseCase getQuestion;

    @Test
    public void getUseCaseTest(){

        var questionDTO = new QuestionDTO("q001","u001","Raúl nos dará una nota de 100?", Type.OPEN, Category.SOFTWARE_DEVELOPMENT, "Email enviado");
        var question= new Question();
        question.setId("q001");
        question.setQuestion("Raúl nos dará una nota de 100?");
        question.setUserId("u001");
        question.setType(Type.OPEN);
        question.setCategory(Category.SOFTWARE_DEVELOPMENT);

        Mockito.when(questionRepository.findById(Mockito.any(String.class))).thenReturn(Mono.just(question));

        var respuesta = getQuestion.apply("q001");
        Assertions.assertEquals(respuesta.block().getQuestion(), question.getQuestion());
    }
}