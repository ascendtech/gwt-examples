package us.ascendtech.rest.controllers;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.ascendtech.rest.dto.TriviaQuestion;
import us.ascendtech.rest.services.TriviaService;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/service/trivia")
public class TriviaController {
    static final Logger LOG = LoggerFactory.getLogger(TriviaController.class);
    private final TriviaService triviaService;

    public TriviaController(TriviaService triviaService) {
        this.triviaService = triviaService;
    }

    @Get("/next")
    public HttpResponse<TriviaQuestion> next() {
        return HttpResponse.created(triviaService.nextQuestion());
    }

    @Post("/difficulty/{difficulty}")
    public HttpResponse setDifficulty(@Parameter String difficulty) {
        LOG.debug("Attempting to set difficulty to " + difficulty);
        var parsedDifficulty = TriviaQuestion.Difficulty.fromString(difficulty);
        if (parsedDifficulty.isPresent()) {
            return HttpResponse.ok(triviaService.setDifficulty(parsedDifficulty.get()));
        }
        else {
            return HttpResponse.serverError();
        }
    }

    @Get("/state")
    public HttpResponse<State> getState() {
        State state = new State();
        state.category = triviaService.getCategory();
        state.difficulty = triviaService.getDifficulty().toString();
        state.question = triviaService.getQuestion();
        return HttpResponse.ok(state);
    }

    @Post("/category/{category}")
    public HttpResponse setCategory(@Parameter int category) {
        LOG.debug("Attempting to set category to " + category);
        return HttpResponse.ok(triviaService.setCategory(category));
    }

    @Get("/categories")
    public HttpResponse<List<Category>> getCategories() {
        return HttpResponse.ok(triviaService.getCategories().entrySet().stream().map(entry -> new Category(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList()));
    }

    private class Category {
        public int id;
        public String name;

        public Category(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private class State {
        public int category;
        public String difficulty;
        public TriviaQuestion question;
    }
}
