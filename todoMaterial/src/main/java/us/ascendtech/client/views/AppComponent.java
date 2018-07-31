package us.ascendtech.client.views;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import us.ascendtech.client.RoutesConfig;

@Component(customizeOptions = RoutesConfig.class)
public class AppComponent implements IsVueComponent {

	@Data
	boolean drawer;

}