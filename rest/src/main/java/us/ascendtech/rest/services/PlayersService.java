package us.ascendtech.rest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.ascendtech.rest.dto.Player;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
public class PlayersService {
	static final Logger LOG = LoggerFactory.getLogger(PlayersService.class);
	private static final AtomicInteger lastId = new AtomicInteger();
	private final List<Player> players = new ArrayList<>();

	public PlayersService() {
		players.add(new Player(lastId.getAndIncrement(), "Player 1", 0));
		players.add(new Player(lastId.getAndIncrement(), "Player 2", 0));
	}

	public Player addPlayer(String name) {
		var newId = lastId.getAndIncrement();
		var player = new Player(newId, name, 0);
		players.add(player);
		return player;
	}

	public Optional<Player> get(int id) {
		return players.stream().filter(player -> player.getId() == id).findFirst();
	}

	public boolean rename(int id, String name) {
		var maybePlayer = get(id);
		if (maybePlayer.isPresent()) {
			var player = maybePlayer.get();
			player.setName(name);
			return true;
		}
		return false;
	}

	public boolean remove(int id) {
		var maybePlayer = get(id);
		if (maybePlayer.isPresent()) {
			players.remove(maybePlayer.get());
			return true;
		}
		return false;
	}

	public boolean incrementScore(int id) {
		var maybePlayer = get(id);
		if (maybePlayer.isPresent()) {
			var player = maybePlayer.get();
			player.setScore(player.getScore() + 1);
			return true;
		}
		return false;
	}

	public final List<Player> getPlayers() {
		return players;
	}
}
