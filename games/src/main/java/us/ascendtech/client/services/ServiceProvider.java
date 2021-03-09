package us.ascendtech.client.services;

import elemental2.dom.DomGlobal;

/**
 * Created by Payam Meyer on 5/13/15.
 * @author pmeyer
 */
public class ServiceProvider {

	private static final ServiceProvider serviceProvider = new ServiceProvider();
	private final TriviaServiceClient triviaServiceClient;
	private final PlayersServiceClient playersServiceClient;

	private ServiceProvider() {
		triviaServiceClient = new TriviaServiceClientSimpleRest(DomGlobal.window.location.protocol + "//" + DomGlobal.window.location.host);
		playersServiceClient = new PlayersServiceClientSimpleRest(DomGlobal.window.location.protocol + "//" + DomGlobal.window.location.host);
	}

	public static ServiceProvider get() {
		return serviceProvider;
	}

	public TriviaServiceClient getTriviaServiceClient() {
		return triviaServiceClient;
	}

	public PlayersServiceClient getPlayersServiceClient() {
		return playersServiceClient;
	}

}
