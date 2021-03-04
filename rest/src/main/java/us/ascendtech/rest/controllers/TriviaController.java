package us.ascendtech.rest.controllers;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import us.ascendtech.rest.dto.TriviaQuestion;
import us.ascendtech.rest.services.TriviaService;

@Controller("/service/trivia")
public class TriviaController {

    private final TriviaService triviaService;

    public TriviaController(TriviaService triviaService) {
        this.triviaService = triviaService;
    }

    @Get("/next")
    public HttpResponse<TriviaQuestion> next() {
        return HttpResponse.created(triviaService.getQuestion());
    }
}
