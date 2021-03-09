package us.ascendtech.rest.controllers;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.ascendtech.rest.dto.Player;
import us.ascendtech.rest.services.PlayersService;

import java.util.List;
import java.util.Optional;

@Controller("/service/player")
public class PlayerController {
	static final Logger LOG = LoggerFactory.getLogger(TriviaController.class);
	private final PlayersService playersService;

	public PlayerController(PlayersService playersService) {
		this.playersService = playersService;
	}

	@Get("/list")
	public HttpResponse<List<Player>> list() {
		return HttpResponse.ok(playersService.getPlayers());
	}

	@Get("/get/{id}")
	public HttpResponse<Optional<Player>> get(@Parameter Integer id) {
		return HttpResponse.ok(playersService.get(id));
	}

	@Put("/add")
	public HttpResponse<Player> add(@Body String name) {
		return HttpResponse.ok(playersService.addPlayer(name));
	}

	@Put("/remove/{id}")
	public HttpResponse remove(@Parameter Integer id) {
		if (playersService.remove(id)) {
			return HttpResponse.ok();
		}
		return HttpResponse.serverError();
	}

	@Post("/rename/{id}")
	public HttpResponse rename(@Parameter Integer id, @Body String name) {
		if (playersService.rename(id, name)) {
			return HttpResponse.ok();
		}
		return HttpResponse.serverError();
	}

	@Post("addScore/{id}/{amount}")
	public HttpResponse addScore(@Parameter Integer id, @Parameter Integer amount) {
		if (playersService.incrementScore(id, amount)) {
			return HttpResponse.ok();
		}
		return HttpResponse.serverError();
	}
}
