package co.com.sofka.questions.useCases;

import co.com.sofka.questions.collections.Question;
import co.com.sofka.questions.repositories.QuestionRepository;
import co.com.sofka.questions.utils.Category;
import co.com.sofka.questions.utils.MapperUtils;
import co.com.sofka.questions.utils.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@SpringBootTest
class ListUseCaseTest {

    @MockBean
    private QuestionRepository repository;

    @SpyBean
    private ListUseCase listUseCase;

    @BeforeEach
    public void setup(){

        MapperUtils mapperUtils = new MapperUtils();
        repository = mock(QuestionRepository.class);
        listUseCase = new ListUseCase(mapperUtils,repository);
    }

    @Test
    void listUseCaseTest(){
        var question = new Question("q001", "u001", "Raúl nos dará una nota de 100?", Type.OPEN, Category.SOFTWARE_DEVELOPMENT, "Email enviado");

        when(repository.findAll()).thenReturn(Flux.just(question ));

        StepVerifier.create(listUseCase.get())
                .expectNextMatches(questionDTO -> {
                    assert questionDTO.getUserId().equals("u001");
                    assert questionDTO.getCategory().equals(Category.SOFTWARE_DEVELOPMENT);
                    assert questionDTO.getQuestion().equals("Raúl nos dará una nota de 100?");
                    assert questionDTO.getType().equals(Type.OPEN);
                    return true;
                })
                .verifyComplete();

        verify(repository).findAll();
    }
}