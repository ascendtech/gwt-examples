package us.ascendtech.client.views.sync;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Watch;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsProperty;

@Component
public class SyncComponent implements IsVueComponent {

	@Data
	boolean isLoading;

	@Data
	String search;

	@Data
	String todo;

	@Data
	@JsProperty
	JsArray<String> items = new JsArray<>();

	@Watch("search")
	void watchMessage(String newValue, String oldValue) {
		DomGlobal.console.log("NewValue: " + newValue);
	}

}
