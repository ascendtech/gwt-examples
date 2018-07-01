package us.ascendtech.client.views;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import jsinterop.annotations.JsProperty;
import us.ascendtech.client.RoutesConfig;

@Component(customizeOptions = RoutesConfig.class)
public class AppComponent implements IsVueComponent {

	@JsProperty
	boolean drawer;

}