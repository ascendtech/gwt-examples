package us.ascendtech.rest.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ToDo {

	private static final AtomicInteger counter = new AtomicInteger();

	private Integer id;
	private String todo;

	public ToDo() {
		this.id = counter.getAndIncrement();
	}

	public ToDo(String todo) {
		this.id = counter.getAndIncrement();
		this.todo = todo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ToDo toDo = (ToDo) o;
		return Objects.equals(id, toDo.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "ToDo{" + "todo='" + todo + '\'' + '}';
	}

}

