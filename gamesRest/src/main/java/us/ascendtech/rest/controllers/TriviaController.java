package us.ascendtech.rest.controllers;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.ascendtech.rest.dto.TriviaQuestion;
import us.ascendtech.rest.services.SessionService;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/service/trivia")
public class TriviaController {
    static final Logger LOG = LoggerFactory.getLogger(TriviaController.class);
    private final SessionService sessionService;

    public TriviaController(SessionService sessionService) {

        this.sessionService = sessionService;
    }

    @Get("/next/{gameKey}")
    public HttpResponse<TriviaQuestion> next(@Parameter String gameKey) {
        var session = sessionService.getTriviaGame(gameKey);
        if (session.isPresent()) {
            return HttpResponse.created(session.get().getTrivia().nextQuestion());
        }
        return HttpResponse.serverError();
    }

    @Post("/difficulty/{gameKey}/{difficulty}")
    public HttpResponse setDifficulty(@Parameter String gameKey, @Parameter String difficulty) {
        var session = sessionService.getTriviaGame(gameKey);
        if (session.isPresent()) {
            LOG.debug("Attempting to set difficulty to " + difficulty);
            var parsedDifficulty = TriviaQuestion.Difficulty.fromString(difficulty);
            if (parsedDifficulty.isPresent()) {
                return HttpResponse.ok(session.get().getTrivia().setDifficulty(parsedDifficulty.get()));
            }
            else {
                return HttpResponse.serverError();
            }
        }
        return HttpResponse.serverError();
    }

    @Get("/state/{gameKey}")
    public HttpResponse<State> getState(@Parameter String gameKey) {
        State state = new State();
        var session = sessionService.getTriviaGame(gameKey);
        if (session.isPresent()) {
            state.category = session.get().getTrivia().getCategory();
            state.difficulty = session.get().getTrivia().getDifficulty().toString();
            state.question = session.get().getTrivia().getQuestion();
            return HttpResponse.ok(state);
        }
        return HttpResponse.serverError();
    }

    @Post("/category/{gameKey}/{category}")
    public HttpResponse setCategory(@Parameter String gameKey, @Parameter int category) {
        var session = sessionService.getTriviaGame(gameKey);
        if (session.isPresent()) {
            LOG.debug("Attempting to set category to " + category);
            return HttpResponse.ok(session.get().getTrivia().setCategory(category));
        }
        return HttpResponse.serverError();
    }

    @Get("/categories/{gameKey}")
    public HttpResponse<List<Category>> getCategories(@Parameter String gameKey) {
        var session = sessionService.getTriviaGame(gameKey);
        if (session.isPresent()) {
            return HttpResponse.ok(session.get().getTrivia().getCategories().entrySet().stream().map(entry -> new Category(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList()));
        }
        return HttpResponse.serverError();
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
