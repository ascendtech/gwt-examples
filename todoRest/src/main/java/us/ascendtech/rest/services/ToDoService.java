package us.ascendtech.rest.services;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.inject.Singleton;
import us.ascendtech.rest.dto.ToDo;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Singleton
public class ToDoService {

	//TODO: This is just a simple example.  A real app would use a database for ToDo items and
	// user login with different todo lists for different users
	// resets every hour so we don't end up with a lot of nonsense people have added
	private final Cache<String, Set<ToDo>> toDoSet = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).concurrencyLevel(8).build();

	public ToDoService() {
	}

	public Set<ToDo> getToDoSet() {
		synchronized (toDoSet) {
			Set<ToDo> todos = toDoSet.getIfPresent("todo");
			if (todos == null) {
				todos = new LinkedHashSet<>();
				todos.add(new ToDo("Need to get milk."));
				todos.add(new ToDo("Wash the car."));
				todos.add(new ToDo("Water the plants."));
				todos.add(new ToDo("Watch the best movie."));

				toDoSet.put("todo", todos);
			}
			return todos;
		}
	}

	public Collection<ToDo> getCurrentTODOs() {
		return getToDoSet();
	}

	public void addTodo(ToDo todo) {
		getToDoSet().add(todo);
	}

	public void removeTodo(Integer id) {
		getToDoSet().removeIf(toDo -> id.equals(toDo.getId()));
	}
}
