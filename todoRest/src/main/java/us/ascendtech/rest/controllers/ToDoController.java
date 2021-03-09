package us.ascendtech.rest.controllers;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.QueryValue;
import us.ascendtech.rest.dto.ToDo;
import us.ascendtech.rest.services.ToDoService;

import java.util.ArrayList;
import java.util.Collection;

@Controller("/service/todo")
public class ToDoController {

	private ToDoService todoService;

	public ToDoController(ToDoService todoService) {
		this.todoService = todoService;
	}

	@Get("/list")
	public HttpResponse<Collection<ToDo>> list() {
		return HttpResponse.created(todoService.getCurrentTODOs());
	}

	@Put("/add")
	public HttpResponse<ToDo> add(@Body ToDo todo) {
		System.out.println(todo);
		todoService.addTodo(todo);
		return HttpResponse.created(todo);
	}

	@Delete("/delete/{id}")
	public HttpResponse delete(@Parameter Integer id) {
		todoService.removeTodo(id);
		return HttpResponse.ok();
	}

	@Post("/search/{query}")
	public HttpResponse<Collection<ToDo>> searchToDos(@QueryValue String query) {

		Collection<ToDo> todos = new ArrayList<>();
		for (ToDo todo : todoService.getCurrentTODOs()) {
			if (todo.getTodo().toLowerCase().contains(query.toLowerCase())) {
				todos.add(todo);
			}
		}

		return HttpResponse.created(todos);

	}

}
