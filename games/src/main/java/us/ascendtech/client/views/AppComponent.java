package us.ascendtech.client.views;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import us.ascendtech.client.RoutesConfig;
import us.ascendtech.client.vuetify.VuetifyCustomizeOptions;
import us.ascendtech.momentjs.client.MomentJS;

@Component(customizeOptions = { RoutesConfig.class, VuetifyCustomizeOptions.class })
public class AppComponent implements IsVueComponent {

	@Data
	boolean drawer;

	@Data
	double currentYear = new MomentJS().year();

}