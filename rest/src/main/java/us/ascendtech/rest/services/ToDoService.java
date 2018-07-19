package us.ascendtech.rest.services;

import us.ascendtech.rest.model.ToDo;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Singleton
public class ToDoService {

	//TODO: This is just a simple example.  A real app would use a database for ToDo items and
	// user login with different todo lists for different users
	private Set<ToDo> toDoList = new LinkedHashSet<>();

	public ToDoService() {
		toDoList.add(new ToDo("Need to get milk."));
		toDoList.add(new ToDo("Wash the car."));
		toDoList.add(new ToDo("Water the plants."));
		toDoList.add(new ToDo("Watch the best movie."));
	}

	public Collection<ToDo> getCurrentTODOs() {
		return toDoList;
	}

	public void addTodo(ToDo todo) {
		toDoList.add(todo);
	}

	public void removeTodo(Integer id) {
		toDoList.removeIf(toDo -> id.equals(toDo.getId()));
	}
}
