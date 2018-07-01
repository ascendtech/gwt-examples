package us.ascendtech.rest.controllers;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import us.ascendtech.rest.model.ToDo;
import us.ascendtech.rest.services.ToDoService;

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

}
