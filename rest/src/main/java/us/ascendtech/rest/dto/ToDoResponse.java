package us.ascendtech.rest.dto;

public class ToDoResponse {

	private ToDo todo;

	public ToDoResponse() {
	}

	public ToDoResponse(ToDo todo) {
		this.todo = todo;
	}

	public ToDo getTodo() {
		return todo;
	}

	public void setTodo(ToDo todo) {
		this.todo = todo;
	}

	@Override
	public String toString() {
		return "ToDoResponse{" + "todo=" + todo + '}';
	}
}
