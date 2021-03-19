package us.ascendtech.rest.controllers;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import us.ascendtech.rest.dto.GameKey;
import us.ascendtech.rest.services.SessionService;

@Controller("/service/session")
public class SessionController {

	private final SessionService sessionService;

	public SessionController(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Get("/new")
	public HttpResponse<GameKey> newSession() {
		var key = new GameKey(sessionService.newTriviaGame().getSession());
		return HttpResponse.ok(key);
	}

	@Get("/exists/{gameKey}")
	public HttpResponse<Boolean> exists(@Parameter String gameKey) {
		var game = sessionService.getTriviaGame(gameKey);
		return HttpResponse.ok(game.isPresent());
	}
}
