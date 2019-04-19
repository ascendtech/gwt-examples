package us.ascendtech.client.views.todo;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import com.axellience.vuegwt.core.client.component.hooks.HasCreated;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import elemental2.core.JsArray;
import io.reactivex.functions.Consumer;
import jsinterop.annotations.JsMethod;
import us.ascendtech.client.aggrid.AgReadyEvent;
import us.ascendtech.client.aggrid.ColumnDefinition;
import us.ascendtech.client.aggrid.GridApi;
import us.ascendtech.client.dto.ToDoDTO;
import us.ascendtech.client.services.ServiceProvider;

@Component
public class ToDoComponent implements IsVueComponent, HasBeforeMount, HasCreated, ResizeHandler {

	private Consumer<Throwable> err = e -> {
		GWT.log("exception: " + e, e);
		error = e.getMessage();
		showError = true;
	};

	@Data
	String error;

	@Data
	boolean showError;

	@Data
	String inputTodo;

	@Data
	GridApi<ToDoDTO> gridApi;

	@Data
	JsArray<ColumnDefinition> columnDefs = new JsArray<>();

	@Data
	JsArray<ToDoDTO> rowData = new JsArray<>();

	@Data
	String tableHeight;

	@JsMethod
	void addToTable() {
		ToDoDTO newToDoDTO = new ToDoDTO();
		newToDoDTO.todo = inputTodo;
		rowData.push(newToDoDTO);
		ServiceProvider.get().getTodoServiceClient().addToDo(newToDoDTO).subscribe(toDoDTO -> newToDoDTO.id = toDoDTO.id, err);
		inputTodo = "";
	}

	@JsMethod
	void removeFromTable() {
		gridApi.getSelectedRows().forEach((currentValue, index, array) -> {
			rowData.splice(rowData.indexOf(currentValue), 1);
			ServiceProvider.get().getTodoServiceClient().deleteToDo(currentValue.id).subscribe();
			return null;
		});

	}

	@JsMethod
	public void onGridReady(AgReadyEvent<ToDoDTO> event) {
		gridApi = event.getApi();
		gridApi.sizeColumnsToFit();
	}

	@Override
	public void beforeMount() {
		ColumnDefinition<ToDoDTO> todoColumn = new ColumnDefinition<>();
		todoColumn.setHeaderName("ToDo");
		todoColumn.setField("todo");
		todoColumn.setCheckboxSelection(true);

		columnDefs.push(todoColumn);

		rowData = new JsArray<>();

		ServiceProvider.get().getTodoServiceClient().getCurrentToDos().subscribe(n -> rowData.push(n), err);

	}

	@Override
	public void onResize(ResizeEvent resizeEvent) {
		calculateTableHeight();
	}

	private void calculateTableHeight() {
		int height = Window.getClientHeight();
		tableHeight = "height: " + (height - 280) + "px;";
	}

	@Override
	public void created() {
		calculateTableHeight();
		Window.addResizeHandler(this);
	}
}
