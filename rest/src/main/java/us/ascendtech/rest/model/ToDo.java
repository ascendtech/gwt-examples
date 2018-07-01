package us.ascendtech.rest.model;

public class ToDo {

	private String todo;

	public ToDo() {
	}

	public ToDo(String todo) {
		this.todo = todo;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	@Override
	public String toString() {
		return "ToDo{" + "todo='" + todo + '\'' + '}';
	}
}
