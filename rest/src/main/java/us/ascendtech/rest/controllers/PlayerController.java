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
		//Strings coming in from the front-end tend to have extra quotes around them
		if (name.startsWith("\"")) {
			name = name.substring(1);
		}
		if (name.endsWith("\"")) {
			name = name.substring(0, name.length() - 1);
		}
		return HttpResponse.ok(playersService.addPlayer(name));
	}

	@Put("/remove/{id}")
	public HttpResponse<Boolean> remove(@Parameter Integer id) {
		return HttpResponse.ok(playersService.remove(id));
	}

	@Post("/rename/{id}")
	public HttpResponse<Boolean> rename(@Parameter Integer id, @Body String name) {
		//Strings coming in from the front-end tend to have extra quotes around them
		if (name.startsWith("\"")) {
			name = name.substring(1);
		}
		if (name.endsWith("\"")) {
			name = name.substring(0, name.length() - 1);
		}
		return HttpResponse.ok(playersService.rename(id, name));
	}
}
