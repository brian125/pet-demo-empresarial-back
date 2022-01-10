package co.com.sofka.questions.useCases;

import co.com.sofka.questions.collections.Answer;
import co.com.sofka.questions.model.AnswerDTO;
import co.com.sofka.questions.repositories.AnswerRepository;
import co.com.sofka.questions.repositories.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Mono;

@SpringBootTest
class DeleteUseCaseTest {
    @MockBean
    private AnswerRepository answerRepository;
    @MockBean
    private QuestionRepository questionRepository;

    @SpyBean
    DeleteUseCase deleteQuestionUseCase;

    @Test
    void deleteUseCaseTest(){

        var answerDTO = new AnswerDTO("a001","q001", "u001", "Raúl nos dará una nota de 100?");
        var answer = new Answer("a001", "u001", "q001", "Ni por el putas", 1);

        Mockito.when(questionRepository.deleteById("a001")).thenReturn(Mono.empty());
        Mockito.when(answerRepository.deleteByQuestionId("a001")).thenReturn(Mono.empty());

        var result = deleteQuestionUseCase.apply("a001").block();
        Assertions.assertNull(result);
    }
}