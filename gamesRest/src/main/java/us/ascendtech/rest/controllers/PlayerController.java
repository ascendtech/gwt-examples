package us.ascendtech.rest.controllers;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.ascendtech.rest.dto.Player;
import us.ascendtech.rest.services.SessionService;

import java.util.List;
import java.util.Optional;

@Controller("/service/player")
public class PlayerController {
	static final Logger LOG = LoggerFactory.getLogger(TriviaController.class);
	private final SessionService sessionService;

	public PlayerController(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	@Get("/list/{gameKey}")
	public HttpResponse<List<Player>> list(@Parameter String gameKey) {
		var session = sessionService.getTriviaGame(gameKey);
		if (session.isPresent()) {
			return HttpResponse.ok(session.get().getPlayers().getPlayers());
		}
		return HttpResponse.serverError();
	}

	@Get("/get/{gameKey}/{id}")
	public HttpResponse<Optional<Player>> get(@Parameter String gameKey, @Parameter Integer id) {
		var session = sessionService.getTriviaGame(gameKey);
		if (session.isPresent()) {
			return HttpResponse.ok(session.get().getPlayers().get(id));
		}
		return HttpResponse.serverError();
	}

	@Put("/add/{gameKey}/{name}")
	public HttpResponse<Player> add(@Parameter String gameKey, @QueryValue String name) {
		var session = sessionService.getTriviaGame(gameKey);
		if (session.isPresent()) {
			return HttpResponse.ok(session.get().getPlayers().addPlayer(name));
		}
		return HttpResponse.serverError();
	}

	@Put("/remove/{gameKey}/{id}")
	public HttpResponse remove(@Parameter String gameKey, @Parameter Integer id) {
		var session = sessionService.getTriviaGame(gameKey);
		if (session.isPresent()) {
			if (session.get().getPlayers().remove(id)) {
				return HttpResponse.ok();
			}
			return HttpResponse.serverError();
		}
		return HttpResponse.serverError();
	}

	@Post("/rename/{gameKey}/{id}/{name}")
	public HttpResponse rename(@Parameter String gameKey, @Parameter Integer id, @QueryValue String name) {
		var session = sessionService.getTriviaGame(gameKey);
		if (session.isPresent()) {
			if (session.get().getPlayers().rename(id, name)) {
				return HttpResponse.ok();
			}
			return HttpResponse.serverError();
		}
		return HttpResponse.serverError();
	}

	@Post("addScore/{gameKey}/{id}/{amount}")
	public HttpResponse addScore(@Parameter String gameKey, @Parameter Integer id, @Parameter Integer amount) {
		var session = sessionService.getTriviaGame(gameKey);
		if (session.isPresent()) {
			if (session.get().getPlayers().incrementScore(id, amount)) {
				return HttpResponse.ok();
			}
			return HttpResponse.serverError();
		}
		return HttpResponse.serverError();
	}
}
