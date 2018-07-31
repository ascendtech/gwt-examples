package us.ascendtech.client.views.todo;

import com.axellience.vuegwt.core.annotations.component.Component;
import com.axellience.vuegwt.core.annotations.component.Data;
import com.axellience.vuegwt.core.client.component.IsVueComponent;
import com.axellience.vuegwt.core.client.component.hooks.HasBeforeMount;
import com.google.gwt.core.client.GWT;
import elemental2.core.Function;
import elemental2.core.JsArray;
import elemental2.dom.File;
import io.reactivex.functions.Consumer;
import jsinterop.annotations.JsMethod;
import jsinterop.base.JsPropertyMap;
import us.ascendtech.client.aggrid.AgReadyEvent;
import us.ascendtech.client.aggrid.ColumnDefinition;
import us.ascendtech.client.aggrid.GridApi;
import us.ascendtech.client.dto.DropzoneOptions;
import us.ascendtech.client.dto.DropzoneResponseDTO;
import us.ascendtech.client.dto.ToDoDTO;
import us.ascendtech.client.services.ServiceProvider;

import static jsinterop.base.Js.cast;

@Component
public class ToDoComponent implements IsVueComponent, HasBeforeMount {

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
	String success;

	@Data
	boolean showSuccess;

	@Data
	String inputTodo;

	@Data
	GridApi<ToDoDTO> gridApi;

	@Data
	JsArray<ColumnDefinition> columnDefs = new JsArray<>();

	@Data
	JsArray<ToDoDTO> rowData = new JsArray<>();

	@Data
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
		success = response.response;
		showSuccess = true;

		rowData = new JsArray<>();
		ServiceProvider.get().getTodoServiceClient().getCurrentToDos().subscribe(n -> rowData.push(n), err);

		JsPropertyMap<Function> jsComponent = cast(vue().$ref("myVueDropzone"));
		jsComponent.get("removeAllFiles").apply(jsComponent);
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

		dropzoneOptions.thumbnailWidth = 75;
		dropzoneOptions.thumbnailHeight = 75;
		dropzoneOptions.addRemoveLinks = true;
		dropzoneOptions.maxFilesize = 0.5f;
		dropzoneOptions.maxFiles = 1;

		dropzoneOptions.acceptedFiles = ".txt";
		dropzoneOptions.dictDefaultMessage = "Drop a txt file here or click to upload (one TODO per line)";

		ServiceProvider.get().getTodoServiceClient().getCurrentToDos().subscribe(n -> rowData.push(n), err);

	}

}
