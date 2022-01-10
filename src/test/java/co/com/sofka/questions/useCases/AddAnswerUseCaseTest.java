package co.com.sofka.questions.useCases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.model.QuestionDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
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
class AddAnswerUseCaseTest {
    @SpyBean
    private AddAnswerUseCase addAnswerUseCase;

    @MockBean
    private GetUseCase getUseCase;

    @MockBean
    AnswerRepository answerRepository;

    @Test
    void addAnswerUseCaseTest(){
        var question = new QuestionDTO("q001", "u001", "Raúl nos dará una nota de 100?", Type.OPEN, Category.SCIENCES, "Email enviado");
        var answerDTO = new AnswerDTO("a001","q001", "u001", "Ni por el putas");
        var answer = new Answer("a001", "u001", "q001", "Ni por el putas", 1);

        Mockito.when(answerRepository.save(Mockito.any(Answer.class))).thenReturn(Mono.just(answer));
        Mockito.when(getUseCase.apply(Mockito.anyString())).thenReturn(Mono.just(question));

        var questionDTO = addAnswerUseCase.apply(answerDTO);
        var resultQuestionDTO = questionDTO.block();

        assert resultQuestionDTO != null;
        Assertions.assertEquals(resultQuestionDTO.getId(),question.getId());
        Assertions.assertEquals(resultQuestionDTO.getQuestion(),question.getQuestion());
        Assertions.assertEquals(resultQuestionDTO.getAnswers().get(0).getQuestionId(),answerDTO.getQuestionId());
    }
}