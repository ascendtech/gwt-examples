package us.ascendtech.client.views.todo;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import com.google.gwt.core.client.GWT;
import elemental2.core.JsArray;
import elemental2.dom.DomGlobal;
import elemental2.dom.File;
import io.reactivex.functions.Consumer;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import us.ascendtech.client.aggrid.AgReadyEvent;
import us.ascendtech.client.aggrid.ColumnDefinition;
import us.ascendtech.client.aggrid.GridApi;
import us.ascendtech.client.dto.DropzoneOptions;
import us.ascendtech.client.dto.DropzoneResponseDTO;
import us.ascendtech.client.dto.ToDoDTO;
import us.ascendtech.client.services.ServiceProvider;

@Component
public class ToDoComponent implements IsVueComponent, HasBeforeMount {

	private Consumer<Throwable> err = e -> {
		GWT.log("exception: " + e, e);
		error = e.getMessage();
		showError = true;
	};

	@JsProperty
	String error;

	@JsProperty
	boolean showError;

	@JsProperty
	String inputTodo;

	@JsProperty
	GridApi<ToDoDTO> gridApi;

	@JsProperty
	JsArray<ColumnDefinition> columnDefs = new JsArray<>();

	@JsProperty
	JsArray<ToDoDTO> rowData = new JsArray<>();

	@JsProperty
	DropzoneOptions dropzoneOptions;

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
		gridApi = event.api;
		gridApi.sizeColumnsToFit();
	}

	@JsMethod
	public void onSuccess(File file, DropzoneResponseDTO response) {
		DomGlobal.console.log("Got response: " + response.response);
	}

	@Override
	public void beforeMount() {
		ColumnDefinition<ToDoDTO> todoColumn = new ColumnDefinition<>();
		todoColumn.headerName = "ToDo";
		todoColumn.field = "todo";
		todoColumn.checkboxSelection = true;

		columnDefs.push(todoColumn);

		rowData = new JsArray<>();

		dropzoneOptions = new DropzoneOptions();
		dropzoneOptions.url = "/service/todo/fileUpload";
		dropzoneOptions.thumbnailWidth = 150;
		dropzoneOptions.maxFilesize = 0.5f;

		ServiceProvider.get().getTodoServiceClient().getCurrentToDos().subscribe(n -> rowData.push(n), err);

	}

}
