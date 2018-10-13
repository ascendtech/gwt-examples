package us.ascendtech.client;

import com.axellience.vuegwt.core.client.component.options.CustomizeOptions;
import com.axellience.vuegwt.core.client.component.options.VueComponentOptions;
import com.axellience.vueroutergwt.client.Route;
import com.axellience.vueroutergwt.client.RouteConfig;
import com.axellience.vueroutergwt.client.RouterOptions;
import com.axellience.vueroutergwt.client.VueRouter;
import jsinterop.annotations.JsFunction;
import jsinterop.base.JsPropertyMap;
import us.ascendtech.client.views.charts.ChartComponentFactory;
import us.ascendtech.client.views.home.HomeComponentFactory;
import us.ascendtech.client.views.todo.ToDoComponentFactory;
import us.ascendtech.client.views.upload.UploadComponentFactory;

public class RoutesConfig implements CustomizeOptions {

	@JsFunction
	public interface Props {
		JsPropertyMap<String> prop(Route route);
	}

	@Override
	public void customizeOptions(VueComponentOptions componentOptions) {
		RouterOptions routerOptions = new RouterOptions();

		routerOptions.addRoute(new RouteConfig().setPath("/").setName("home").setComponent(HomeComponentFactory.get().getJsConstructor()));
		routerOptions.addRoute(new RouteConfig().setPath("/todo").setName("todo").setComponent(ToDoComponentFactory.get().getJsConstructor()));
		routerOptions.addRoute(new RouteConfig().setPath("/chart").setName("chart").setComponent(ChartComponentFactory.get().getJsConstructor()));
		routerOptions.addRoute(new RouteConfig().setPath("/upload").setName("upload").setComponent(UploadComponentFactory.get().getJsConstructor()));


		// We need to create our router
		VueRouter vueRouter = new VueRouter(routerOptions);

		// And set it on our Component options
		componentOptions.set("router", vueRouter);
	}
}
