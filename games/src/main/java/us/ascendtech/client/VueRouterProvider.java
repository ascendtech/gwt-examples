package us.ascendtech.client;

import com.axellience.vueroutergwt.client.RouteConfig;
import com.axellience.vueroutergwt.client.RouterOptions;
import com.axellience.vueroutergwt.client.VueRouter;
import us.ascendtech.client.views.home.HomeComponentFactory;
import us.ascendtech.client.views.trivia.TriviaComponentFactory;

import javax.inject.Singleton;

@Singleton
public class VueRouterProvider {
	private static final VueRouterProvider instance = new VueRouterProvider();
	private final VueRouter vueRouter;

	private VueRouterProvider() {
		RouterOptions routerOptions = new RouterOptions();

		routerOptions.addRoute(new RouteConfig().setPath("/").setName("home").setComponent(HomeComponentFactory.get().getJsConstructor()));
		routerOptions.addRoute(new RouteConfig().setPath("/trivia").setName("trivia").setComponent(TriviaComponentFactory.get().getJsConstructor()));

		vueRouter = new VueRouter(routerOptions);
	}

	public static VueRouterProvider getInstance() {
		return instance;
	}

	public VueRouter getRouter() {
		return vueRouter;
	}
}
