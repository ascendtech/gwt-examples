package us.ascendtech.rest.services;

import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Singleton
public class SessionService {
	static final Logger LOG = LoggerFactory.getLogger(SessionService.class);
	private final HashMap<String, TriviaGame> triviaGames;

	public SessionService() {
		triviaGames = new HashMap<>();
	}

	private final synchronized String generateKey() {
		var charKey = new char[3];
		String key;
		do {
			charKey[0] = (char) ('A' + ThreadLocalRandom.current().nextInt(0, 26));
			charKey[1] = (char) ('A' + ThreadLocalRandom.current().nextInt(0, 26));
			charKey[2] = (char) ('A' + ThreadLocalRandom.current().nextInt(0, 26));
			key = new String(charKey);
		}
		while (triviaGames.containsKey(key));
		LOG.debug("Generated new session key " + key);
		return key;
	}

	public synchronized TriviaGame newTriviaGame() {
		String key = generateKey();
		TriviaGame newGame = new TriviaGame(key, new PlayersService(), new TriviaService());
		triviaGames.put(key, newGame);
		return newGame;
	}

	public Optional<TriviaGame> getTriviaGame(String session) {
		return Optional.ofNullable(triviaGames.get(session));
	}

	public class TriviaGame {
		private String session;
		private PlayersService players;
		private TriviaService trivia;

		public TriviaGame(String session, PlayersService players, TriviaService trivia) {
			this.session = session;
			this.players = players;
			this.trivia = trivia;
		}

		public final String getSession() {
			return session;
		}

		public final void setSession(String session) {
			this.session = session;
		}

		public final PlayersService getPlayers() {
			return players;
		}

		public final void setPlayers(PlayersService players) {
			this.players = players;
		}

		public final TriviaService getTrivia() {
			return trivia;
		}

		public final void setTrivia(TriviaService trivia) {
			this.trivia = trivia;
		}
	}

}
