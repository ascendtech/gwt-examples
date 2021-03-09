package us.ascendtech.client;

import com.axellience.vuegwt.core.client.Vue;
import com.axellience.vuegwt.core.client.VueGWT;
import com.google.gwt.core.client.EntryPoint;
import us.ascendtech.client.views.AppComponentFactory;

public class Games implements EntryPoint {
	public void onModuleLoad() {
		VueGWT.initWithoutVueLib();

		Vue.attach("#app", AppComponentFactory.get());

	}
}
