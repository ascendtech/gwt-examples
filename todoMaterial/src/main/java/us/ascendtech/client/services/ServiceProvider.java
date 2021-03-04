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

	public static ServiceProvider get() {
		return serviceProvider;
	}

	private final ToDoServiceClient todoServiceClient;
	private final TriviaServiceClient triviaServiceClient;

	private static ResourceVisitor osm() {
		String baseUrl = DomGlobal.window.location.protocol + "//" + DomGlobal.window.location.host;
		return new RequestResourceBuilder().path(baseUrl);
	}

	private ServiceProvider() {
		todoServiceClient = new ToDoServiceClient_RestServiceModel(ServiceProvider::osm);
		triviaServiceClient = new TriviaServiceClient_RestServiceModel(ServiceProvider::osm);
	}

	public ToDoServiceClient getTodoServiceClient() {
		return todoServiceClient;
	}

	public TriviaServiceClient getTriviaServiceClient() {
		return triviaServiceClient;
	}

}
