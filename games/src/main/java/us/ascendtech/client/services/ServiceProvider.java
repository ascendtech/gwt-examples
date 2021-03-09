package us.ascendtech.client.services;

import com.intendia.gwt.autorest.client.RequestResourceBuilder;
import com.intendia.gwt.autorest.client.ResourceVisitor;
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
		triviaServiceClient = new TriviaServiceClient_RestServiceModel(ServiceProvider::osm);
		playersServiceClient = new PlayersServiceClient_RestServiceModel(ServiceProvider::osm);
	}

	public static ServiceProvider get() {
		return serviceProvider;
	}

	private static ResourceVisitor osm() {
		String baseUrl = DomGlobal.window.location.protocol + "//" + DomGlobal.window.location.host;
		return new RequestResourceBuilder().path(baseUrl);
	}

	public TriviaServiceClient getTriviaServiceClient() {
		return triviaServiceClient;
	}

	public PlayersServiceClient getPlayersServiceClient() {
		return playersServiceClient;
	}

}
