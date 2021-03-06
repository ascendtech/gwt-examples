package us.ascendtech.client.views.sync;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.annotations.component.Watch;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.dto.ToDoDTO;
import us.ascendtech.client.services.ServiceProvider;

@Component
public class SyncWorkaroundComponent implements IsVueComponent {

	@Data
	boolean isLoading;

	@Data
	String search;

	@Data
	ToDoDTO todo;

	@Data
	JsArray<ToDoDTO> items = new JsArray<>();

	@Watch("search")
	void watchMessage(String newValue, String oldValue) {
		if (newValue != null && newValue.length() > 1) {
			items = new JsArray<>();
			ServiceProvider.get().getTodoServiceClient().searchToDos(newValue, data -> {
				items.push(data);
				isLoading = false;
			}, (statusCode, status, errorBody) -> {
				DomGlobal.console.log(status + ":" + errorBody);
				isLoading = false;
			});

		}
	}

	@JsMethod
	void emptyItems() {
		items.splice(0, items.length);
	}

}
