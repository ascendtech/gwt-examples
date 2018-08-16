package us.ascendtech.client;

import com.axellience.vuegwt.core.client.Vue;
import com.axellience.vuegwt.core.client.VueGWT;
import com.google.gwt.core.client.EntryPoint;
import us.ascendtech.client.highcharts.HighchartsInjector;
import us.ascendtech.client.views.AppComponentFactory;

public class ToDo implements EntryPoint {
	public void onModuleLoad() {
		VueGWT.initWithoutVueLib();

		HighchartsInjector.injectHighcharts();

		Vue.attach("#app", AppComponentFactory.get());

	}
}
