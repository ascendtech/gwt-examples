package us.ascendtech.rest.model;

import java.util.Objects;

public class ToDo {

	private String todo;
	private boolean done;

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

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ToDo toDo = (ToDo) o;
		return Objects.equals(todo, toDo.todo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(todo);
	}

	@Override
	public String toString() {
		return "ToDo{" + "todo='" + todo + '\'' + ", done=" + done + '}';
	}
}
